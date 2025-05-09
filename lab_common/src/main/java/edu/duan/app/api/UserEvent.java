package edu.duan.app.api;

import lombok.*;

import java.io.Serializable;

@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private long userId;
    private long orderId;
    private boolean exist;
}
