package com.rtg.frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodDTO {
    private String name;
    private int proteins;
    private int carbohydrates;
    private int fats;
}
