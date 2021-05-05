package com.MyEBike.api;

public class ApiWrapper extends RetrofitUtils {
    protected final ApiService service = getRetrofit().create(ApiService.class);

    public ApiService getService() {
        return service;
    }


}
