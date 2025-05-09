package edu.duan.app.api;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppErrorResponse {
    private int code;
    private String message;
}
