package edu.duan.app.api;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private int userId;
    private int itemId;
    private int count;
    private String notes;
    private String fulfillmentNotes;
}
