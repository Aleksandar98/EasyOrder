package com.example.eeasyorder.domain.use_case.user_usecases;

import androidx.datastore.preferences.core.Preferences;

import com.example.eeasyorder.domain.TokenRepo;
import com.example.eeasyorder.domain.UserRepo;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class SetUserNameUseCase {

    UserRepo userRepo;

    @Inject
    public SetUserNameUseCase(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    public Single<Preferences> invoke(String name){

        return userRepo.saveUserName(name);
    }
}
