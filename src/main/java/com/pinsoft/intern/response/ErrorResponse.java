package com.pinsoft.intern.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private int code;
    private String message;
    private long timeStamp;

    public ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.timeStamp = System.currentTimeMillis();
    }

    public ErrorResponse( String message) {
        this.message = message;
        this.timeStamp = System.currentTimeMillis();
    }
}
