package com.www.scheduleer.config.error;

public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode, String message){
        super(errorCode.getMessage() + ": "+message);
        this.errorCode = errorCode ;
    }
    public CustomException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public ErrorCode getCode(){
        return this.errorCode;
    }

}