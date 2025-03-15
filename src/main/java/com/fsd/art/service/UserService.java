package com.fsd.art.service;

import com.fsd.art.model.Painting;
import com.fsd.art.model.Role;
import com.fsd.art.model.req.UserReq;
import com.fsd.art.model.res.PaintingRes;
import com.fsd.art.model.res.UserRes;
import com.fsd.art.repository.PaintingRepository;
import com.fsd.art.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PaintingRepository paintingRepository;
    private final  PaintingMapper paintingMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PaintingRepository paintingRepository, PaintingMapper paintingMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.paintingRepository = paintingRepository;
        this.paintingMapper = paintingMapper;
    }

    public UserRes getUser(Long id){
        var user = userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User Not Found"));
        return userMapper.getUserRes(user);
    }


    public UserRes signIn(UserReq userReq){
        var user = userRepository.findOneByEmail(userReq.email()).
                orElseThrow(()-> new EntityNotFoundException("User Not Found"));

        if(!user.getPassword().equals(userReq.password())){
            throw  new EntityNotFoundException("Password MisMatch");
        }

        return userMapper.getUserRes(user);

    }
    public UserRes signUp(UserReq userReq){
        userRepository.findOneByEmail(userReq.email()).ifPresent(e ->{
            throw  new EntityExistsException("Email Already SignUp");
        });

        var user = userMapper.getUser(userReq);
        var saveUser = userRepository.save(user);
        return userMapper.getUserRes(saveUser);
    }

    public void addPaintingInCart(Long userId, Long paintingId) {
        var user = userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("User Not Found"));

        var painting = paintingRepository.findById(paintingId).orElseThrow(() -> new EntityNotFoundException("Painting InValid"));

        if(userId.equals(painting.getArtist().getId())) throw new EntityExistsException("Artist Not Buy Own Painting");

        user.getAdd_Cart_Item().add(painting);

        userRepository.save(user);
    }

    public Set<PaintingRes> viewCart(Long userId) {

        var user = userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("User Not Found"));

        return user.getAdd_Cart_Item().stream().map(paintingMapper::getPaintingRes).collect(Collectors.toSet());
    }

    @Transactional
    public List<Long> buyCartItem(Long userId){
        var user = userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("User Not Found"));

        var cartItem= user.getAdd_Cart_Item();

        cartItem.parallelStream().forEach(item-> {
            if(item.isBuy()){
                throw new EntityExistsException("Cart Contain Buy Item");
            }
            item.setBuy(true);
            item.setBuyer(user);
        });

        paintingRepository.saveAll(cartItem);

        return cartItem.stream().map(Painting::getId).toList();
    }

    public List<PaintingRes> allBuyItemByUser(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("User Not Found"));
        return user.getPurchased_Item().stream().map(paintingMapper::getPaintingRes).toList();
    }

    public List<PaintingRes> artistItem(Long userId){
        var user = userRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("User Not Found"));
        if(user.getRole() != Role.ARTIST){
            throw new EntityNotFoundException("User Must Be Artist");
        }

        return user.getSold_Item().stream().map(paintingMapper::getPaintingRes).toList();
    }
}
