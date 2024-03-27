package com.dangchph33497.fpoly.lab5_md18306_ph33497.Service;

import static com.dangchph33497.fpoly.lab5_md18306_ph33497.Service.ApiServices.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequest {
    //Biến Interface ApiService
    private ApiServices requestInterface;

    public HttpRequest() {
        //Create Retrofit
        requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiServices.class);

    }
    public  ApiServices callAPI(){
        //Get Retrofit
        return requestInterface;
    }
}
