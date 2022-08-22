package com.example.eeasyorder.domain.use_case.token_usecases;

import androidx.datastore.preferences.core.Preferences;

import com.example.eeasyorder.domain.TokenRepo;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class GetTokenUseCase {

    TokenRepo tokenRepo;

    @Inject
    public GetTokenUseCase(TokenRepo tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    public Flowable<String> invoke(){

        return tokenRepo.readUserToken();
    }
}
