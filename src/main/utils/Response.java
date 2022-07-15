package main.utils;

import java.util.HashMap;

public class Response {
    private boolean success;
    private String message;

    private HashMap<String,Object> data;

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }


    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
