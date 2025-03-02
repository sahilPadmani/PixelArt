package com.fsd.art.service;

import com.fsd.art.model.Role;
import com.fsd.art.model.req.PaintingReq;
import com.fsd.art.model.res.PaintingRes;
import com.fsd.art.repository.PaintingRepository;
import com.fsd.art.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaintingService {
    private final PaintingRepository paintingRepository;
    private final PaintingMapper paintingMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public PaintingService(PaintingRepository paintingRepository, PaintingMapper paintingMapper, UserMapper userMapper, UserRepository userRepository) {
        this.paintingRepository = paintingRepository;
        this.paintingMapper = paintingMapper;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public PaintingRes getPaintingById(Long id){
        var panting = paintingRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Panting not Found with Id %d".formatted(id)));

        if(panting.isBuy()){
            throw  new EntityExistsException("Painting Already Sold");
        }
        return paintingMapper.getPaintingRes(panting);
    }

    public List<PaintingRes> getAllPainting(){
        return paintingRepository.findAll().stream().map(paintingMapper::getPaintingRes).toList();
    }

    public Long addPainting(PaintingReq paintingReq){
        var painting = paintingMapper.getPainting(paintingReq);

        var artist = userRepository.findById(paintingReq.artistId()).orElseThrow(() -> new EntityNotFoundException("Artist Not Found"));

        if(artist.getRole() != Role.ARTIST){
            throw  new EntityNotFoundException("User Must Be Artist");
        }

        painting.setArtist(artist);

        return paintingRepository.save(painting).getId();
    }

}
