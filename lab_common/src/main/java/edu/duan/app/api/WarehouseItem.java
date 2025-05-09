package edu.duan.app.api;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WarehouseItem {
    private int id;
    private String name;
    private String description;
    private double price;
    private int inStockCount;
}
