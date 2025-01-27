package com.flag.pam.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.flag.pam.exceptions.AppResponseCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author rameshalagumalai
 * @Date 26/01/2025
 * */

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppResponse {
    private int code = AppResponseCodes.SUCCESS;
    private String message;
    private Object data;
    private List<String> errorList;

    public AppResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public AppResponse(String message) {
        this.message = message;
    }

    public AppResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public AppResponse(int code, List<String> errorList) {
        this.code = code;
        this.errorList = errorList;
    }

    public AppResponse(Object data) {
        this.data = data;
    }
}
