package edu.duan.app.api;

import lombok.*;

@Getter
@Setter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class OrderErrorEvent {
    private int id;
    private int userId;
    private String reason_code;
}
