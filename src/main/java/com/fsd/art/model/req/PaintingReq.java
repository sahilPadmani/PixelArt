package com.fsd.art.model.req;

import java.math.BigDecimal;

public record PaintingReq(
        Long buyerId,
        Long artistId,
        BigDecimal prices,
        String name
) {
}
