package edu.duan.app.api;

import lombok.*;

@Getter
@Setter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class UserEvent {
    private int userId;
    private int orderId;
    private boolean exist;
}
