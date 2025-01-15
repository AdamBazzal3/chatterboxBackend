package com.chatterbox.api.response;

public class TokenAPIResponse extends APIResponse{
    private String token;

    public TokenAPIResponse(){

    }

    public TokenAPIResponse(String message, String token) {
        super(true, message);
        this.token = token;
    }

    public TokenAPIResponse(boolean isSuccess, String message) {
        super(isSuccess, message);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
