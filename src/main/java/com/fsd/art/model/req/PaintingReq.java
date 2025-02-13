package com.fsd.art.model.req;

import java.math.BigDecimal;

public record PaintingReq(
        Integer buyerId,
        Integer artistId,
        BigDecimal prices,
        String name
) {
}
