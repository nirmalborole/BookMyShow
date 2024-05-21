package com.example.bookmyshow.dtos;

import org.hibernate.query.NativeQuery;

public class Response {
    private ResponseStatus responseStatus;
    private String error;

    public Response(ResponseStatus responseStatus, String error) {
        this.responseStatus = responseStatus;
        this.error = error;
    }

    public static Response getFailuerResponse(String error){
        return new Response(ResponseStatus.FAILURED,error);
    }
    public static Response getSuccessResponse(){
        return new Response(ResponseStatus.SUCCESS,null);
    }
}
