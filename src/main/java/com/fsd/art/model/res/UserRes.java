package com.fsd.art.model.res;

import java.util.List;

public record UserRes(
        Integer id,
        String name,
        String email,
        String role,
        List<Integer> purchased_Item,
        List<Integer> sold_Item
) {
}
