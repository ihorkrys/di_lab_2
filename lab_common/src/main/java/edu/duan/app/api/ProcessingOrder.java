package edu.duan.app.api;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProcessingOrder {
    private long id;
    private OrderState orderState;
    private String notes;
    private String fulfillmentNotes;
}
