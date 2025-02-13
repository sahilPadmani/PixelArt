package com.fsd.art.service;

import com.fsd.art.model.Painting;
import com.fsd.art.model.req.PaintingReq;
import com.fsd.art.model.res.PaintingRes;
import org.springframework.stereotype.Service;

@Service
public class PaintingMapper {
    public Painting toPainting(PaintingReq paintingReq){
        return Painting.builder()
                .name(paintingReq.name())
                .prices(paintingReq.prices())
                .build();
    }
    public PaintingRes forPaintingRes (Painting painting){
        return new PaintingRes(
            painting.getId(),
            painting.getName(),
            painting.getPrices(),
            painting.getBuyer().getId(),
            painting.getArtist().getId()
        );
    }
}
