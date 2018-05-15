package com.carpiness.job.input.token;

public class ErrorToken implements Token {

    private String message;

    public ErrorToken(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
