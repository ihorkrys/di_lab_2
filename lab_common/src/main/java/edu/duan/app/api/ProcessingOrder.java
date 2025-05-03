package edu.duan.app.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessingOrder {
    private int id;
    private OrderState orderState;
    private String notes;
    private String fulfillmentNotes;
}
