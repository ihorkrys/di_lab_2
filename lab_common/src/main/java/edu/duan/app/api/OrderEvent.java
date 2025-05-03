package edu.duan.app.api;

import lombok.*;

@Getter
@Setter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private int id;
    private int userId;
    private int itemId;
    private int count;
    private OrderState orderState;
}
