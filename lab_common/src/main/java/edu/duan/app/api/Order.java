package edu.duan.app.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int id;
    private int userId;
    private OrderItem orderItem;
    private int count;
    private double total;
    private OrderState orderState;
    private String stateReasonCode;
    private String notes;
    private String fulfillmentNotes;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
}
