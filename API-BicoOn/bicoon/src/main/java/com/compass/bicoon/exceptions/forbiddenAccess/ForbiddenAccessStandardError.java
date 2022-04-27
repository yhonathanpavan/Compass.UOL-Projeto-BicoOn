package com.compass.bicoon.exceptions.forbiddenAccess;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ForbiddenAccessStandardError {

    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
