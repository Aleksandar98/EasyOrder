package com.example.eeasyorder.domain.use_case.token_usecases;

import androidx.datastore.preferences.core.Preferences;

import com.example.eeasyorder.data.remote.dto.ResponseDto;
import com.example.eeasyorder.domain.TokenRepo;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class SetTokenUseCase {

    TokenRepo tokenRepo;

    @Inject
    public SetTokenUseCase(TokenRepo tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    public Single<Preferences> invokeClearToken(){

        return tokenRepo.clearUserToken();
    }

    public Single<Preferences> invokeSetToken(String token){

        return tokenRepo.saveUserToken(token);
    }
}
