package edu.duan.app.api;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private int id;
    private String name;
    private String description;
    private double price;
}
