package com.chatterbox.api.response;

public class APIResponse {
    private boolean isSuccess = false;
    private String message;

    public APIResponse(){

    }

    public APIResponse(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public APIResponse(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }


    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
