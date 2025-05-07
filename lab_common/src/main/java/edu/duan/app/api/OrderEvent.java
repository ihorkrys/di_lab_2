package edu.duan.app.api;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private long userId;
    private long itemId;
    private int count;
    private OrderState orderState;
}
