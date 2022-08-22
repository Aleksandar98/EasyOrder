package com.example.eeasyorder.data.remote;

import com.example.eeasyorder.data.remote.dto.ResponseDto;
import com.example.eeasyorder.data.remote.dto.RestaurantDto;
import com.example.eeasyorder.data.remote.dto.UserDto;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MenuPlaygroundApi {

    @POST("api/directory/search")
    @Headers({
            "application: mobile-application",
            "Content-Type: application/json",
            "Device-UUID: 123456",
            "Api-Version: 3.7.0"
    })
    Single<ResponseDto> getAllRestaurants(@Body HashMap<String,String> body);

    @Headers({
            "application: mobile-application",
            "Content-Type: application/json",
            "Device-UUID: 123456",
            "Api-Version: 3.7.0"
    })
    @POST("api/customers/login")
    Single<ResponseDto> loginUser(@Body HashMap<String,String> body);
}
