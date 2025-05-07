package edu.duan.app.api;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class OrderErrorEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private long userId;
    private String reason_code;
}
