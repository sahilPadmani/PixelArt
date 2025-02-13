package com.fsd.art.model.res;

import java.math.BigDecimal;

public record PaintingRes(
        Integer id,
        String name,
        BigDecimal prices,
        Integer buyerId,
        Integer artistId
) {
}
