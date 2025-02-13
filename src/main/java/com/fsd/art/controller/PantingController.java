package com.fsd.art.controller;

import com.fsd.art.model.req.PaintingReq;
import com.fsd.art.model.res.PaintingRes;
import com.fsd.art.service.PaintingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/panting/")
public class PantingController {
    private  final PaintingService paintingService;

    @GetMapping("/{id}")
    public PaintingRes getPainting(@PathVariable Integer id){
        return paintingService.getPainting(id);
    }

    @PostMapping("/")
    public PaintingRes save(@RequestBody PaintingReq paintingReq){
        return paintingService.savePainting(paintingReq);
    }

    public PantingController(PaintingService paintingService) {
        this.paintingService = paintingService;
    }
}
