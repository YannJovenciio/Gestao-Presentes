package com.gestao.gestaopresente.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private T data;
    private String message;
    private int statusCode;

    public Response(T data) {
        this.data = data;
    }

    public Response(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public Response(T data, String message, int statusCode) {
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
    }

    public Response(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
