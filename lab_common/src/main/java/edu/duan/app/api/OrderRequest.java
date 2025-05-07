package edu.duan.app.api;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private long userId;
    private long itemId;
    private int count;
    private double price;
    private String notes;
    private String fulfillmentNotes;
}
