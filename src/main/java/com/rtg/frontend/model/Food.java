package com.rtg.frontend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Food {
    private String uuid;
    private String name;
    private int proteins;
    private int carbohydrates;
    private int fats;
}