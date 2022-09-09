package com.lionel.student_jpa.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class HttpResponse {

    private int statusCode;
    private HttpStatus httpStatus;
    private String msg;
    private boolean ok;

}
