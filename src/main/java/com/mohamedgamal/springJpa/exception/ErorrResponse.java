package com.mohamedgamal.springJpa.exception;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErorrResponse {

    private int status;
    private String message;
    private final Date timeStamp;
}
