package com.dangchph33497.fpoly.lab5_md18306_ph33497.Service;

import com.dangchph33497.fpoly.lab5_md18306_ph33497.Model.Distributor;
import com.dangchph33497.fpoly.lab5_md18306_ph33497.Model.Response;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {

    public static String BASE_URL = "http://192.168.1.239:3000/api/";

    @GET("get-list-distributor")
    Call<Response<ArrayList<Distributor>>> getListDistributor();
    @GET("search-distributor")
    Call<Response<ArrayList<Distributor>>> searchDistributor(@Query("key") String key);

    @POST("add-distributor")
    Call<Response<Distributor>> addDistributor(@Body Distributor distributor);

    @PUT("update-distributor-by-id/{id}")
    Call<Response<Distributor>> updateDistributor(@Path("id") String id, @Body Distributor distributor);

    @DELETE("delete-distributor-by-id/{id}")
    Call<Response<Distributor>> deleteDistributorById(@Path("id") String id);
}
