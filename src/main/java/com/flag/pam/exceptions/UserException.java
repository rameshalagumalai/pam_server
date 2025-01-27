package com.flag.pam.exceptions;

import lombok.Getter;

/**
 * @author rameshalagumalai
 * @Date 26/01/2025
 * */

public class UserException extends Exception{

    @Getter
    private int errorCode = AppResponseCodes.USER_ERROR;

    public UserException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public UserException(String message) {
        super(message);
    }

}
