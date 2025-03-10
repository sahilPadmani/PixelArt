package com.fsd.art.controller;

import com.fsd.art.model.req.PaintingReq;
import com.fsd.art.model.res.PaintingRes;
import com.fsd.art.service.PaintingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/painting")
public class PaintingController {
    private  final PaintingService paintingService;

    public PaintingController(PaintingService paintingService) {
        this.paintingService = paintingService;
    }

    @GetMapping("/")
    public ResponseEntity<List<PaintingRes>> getAll(){
        return ResponseEntity.ok(paintingService.getAllPainting());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaintingRes> getPaintingById(@PathVariable Long id){
        return ResponseEntity.ok(paintingService.getPaintingById(id));
    }


    @PostMapping("/")
    public ResponseEntity<Long> addPainting(@RequestBody PaintingReq paintingReq){
        return ResponseEntity.ok(paintingService.addPainting(paintingReq));
    }
}
