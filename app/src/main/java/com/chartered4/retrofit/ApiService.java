package com.chartered4.retrofit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    String BASEURL = "https://test.chartered4.com/app/";
    String TermsAndConditions = "https://test.chartered4.com/terms-of-use";

    @POST("token/login")
    Call<JsonElement> login(@Body() JsonObject jsonObject);

    @POST("customer/signup")
    Call<JsonElement> registration(@Body() JsonObject jsonObject);

    @POST("get_data_rfid")
    Call<JsonElement> getResidentDetailsByRFID(@Header("Authorization") String authHeader, @Body() JsonObject jsonObject);

    @GET("get_resident_list/{facilityId}")
    Call<JsonElement> getResidents(@Header("Authorization") String authHeader, @Path("facilityId") String facilityId);

    @POST("get_rfid_code")
    Call<JsonElement> getRFIDByResidentDetails(@Header("Authorization") String authHeader, @Body() JsonObject jsonObject);

    @GET("get_cloth_details")
    Call<JsonElement> getDeliveryCloths(@Header("Authorization") String authHeader, @Query("facility_id") String facilityId,
                                        @Query("room") String roomNo); //2022-12-28

//    @POST("delivered_cloth_details")
//    Call<JsonElement> submitDeliveryCloths(@Header("Authorization") String authHeader, @Body() SubmitDeliveryBean submitDeliveryBean);

}