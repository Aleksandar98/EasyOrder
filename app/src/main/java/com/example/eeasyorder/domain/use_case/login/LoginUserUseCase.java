package com.example.eeasyorder.domain.use_case.login;

import android.util.Log;

import com.example.eeasyorder.data.remote.dto.ResponseDto;
import com.example.eeasyorder.data.remote.dto.RestaurantDto;
import com.example.eeasyorder.data.remote.dto.UserDto;
import com.example.eeasyorder.domain.RestaurantRepo;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class LoginUserUseCase {

    RestaurantRepo repo;

    @Inject
    public LoginUserUseCase(RestaurantRepo repo) {
        this.repo = repo;
    }

    //TODO need to return something from RXJava and Resource
    public Single<ResponseDto> invoke(HashMap<String,String> body){

        return repo.loginUser(body);
    }
}
