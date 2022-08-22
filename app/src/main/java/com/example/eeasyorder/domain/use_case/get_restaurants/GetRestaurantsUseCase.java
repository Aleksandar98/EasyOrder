package com.example.eeasyorder.domain.use_case.get_restaurants;

import android.util.Log;

import com.example.eeasyorder.data.remote.dto.ResponseDto;
import com.example.eeasyorder.data.remote.dto.RestaurantDto;
import com.example.eeasyorder.domain.RestaurantRepo;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.HttpException;

public class GetRestaurantsUseCase {

    RestaurantRepo repo;

    @Inject
    public GetRestaurantsUseCase(RestaurantRepo repo) {
        this.repo = repo;
    }

    //TODO need to return something from RXJava and Resource
    public Single<ResponseDto> invoke(HashMap<String,String> body){

        return repo.getRestaurants(body);
    }
}
