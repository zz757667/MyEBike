package com.MyEBike.api;


import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("login/")
    Single<BaseResponseModel> login(
            @Field("username") String username,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("register/")
    Single<BaseResponseModel> register(
            @Field("username") String username,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("idcard") String idcard);


    @FormUrlEncoded
    @POST("add_money/")
    Single<BaseResponseModel> add_money(
            @Field("money") int money);


    @FormUrlEncoded
    @GET("get_money/")
    Single<BaseResponseModel> get_money(

            @Field("money") float money);


    @FormUrlEncoded
    @POST("problemput/")
    Single<BaseResponseModel> problemput(
            @Field("advice") String advice,
            @Field("type") String type);
}
