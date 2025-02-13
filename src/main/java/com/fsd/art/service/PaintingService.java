package com.fsd.art.service;

import com.fsd.art.model.req.PaintingReq;
import com.fsd.art.model.res.PaintingRes;
import com.fsd.art.repository.PaintingRepository;
import com.fsd.art.repository.UserRepository;
import org.springframework.stereotype.Service;

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


    public PaintingRes getPainting(Integer id){
        var painting = paintingRepository.findById(id).orElse(null);
        if(painting == null)
            return null;
        return paintingMapper.forPaintingRes(painting);
    }

    public PaintingRes savePainting(PaintingReq paintingReq){
        var painting = paintingMapper.toPainting(paintingReq);

        var buyer =  userRepository.findById(paintingReq.buyerId()).orElse(null);

        if(buyer == null)
            return null;

        var artist = userRepository.findById(paintingReq.artistId()).orElse(null);

        if(artist == null)
            return null;

        painting.setArtist(artist);
        painting.setBuyer(buyer);

        var savePainting = paintingRepository.save(painting);

        return paintingMapper.forPaintingRes(savePainting);
    }

}
