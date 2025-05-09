package edu.duan.app.api;

import lombok.*;

import java.io.Serializable;

@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderErrorEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private long userId;
    private String reason_code;
}
