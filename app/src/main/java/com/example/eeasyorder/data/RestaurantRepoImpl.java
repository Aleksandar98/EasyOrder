package com.example.eeasyorder.data;

import com.example.eeasyorder.data.remote.MenuPlaygroundApi;
import com.example.eeasyorder.data.remote.dto.ResponseDto;
import com.example.eeasyorder.data.remote.dto.RestaurantDto;
import com.example.eeasyorder.data.remote.dto.UserDto;
import com.example.eeasyorder.domain.RestaurantRepo;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;

public class RestaurantRepoImpl implements RestaurantRepo {

    MenuPlaygroundApi api;

    public RestaurantRepoImpl(MenuPlaygroundApi api) {
        this.api = api;
    }


    @Override
    public Single<ResponseDto> getRestaurants(HashMap<String,String> body) {
        return api.getAllRestaurants(body);
    }

    @Override
    public Single<ResponseDto> loginUser(HashMap<String,String> body) {
        return api.loginUser(body);
    }
}
