package org.example.controller.advice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {
    private int code;
    private String message;
    private T data;
}
