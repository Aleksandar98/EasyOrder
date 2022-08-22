package com.example.eeasyorder.domain;

import com.example.eeasyorder.data.remote.dto.ResponseDto;
import com.example.eeasyorder.data.remote.dto.RestaurantDto;
import com.example.eeasyorder.data.remote.dto.UserDto;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface RestaurantRepo {

    Single<ResponseDto> getRestaurants(HashMap<String,String> body);

    Single<ResponseDto> loginUser (HashMap<String,String> body);
}
