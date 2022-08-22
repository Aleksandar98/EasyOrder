package com.example.eeasyorder.domain.use_case.user_usecases;

import com.example.eeasyorder.domain.TokenRepo;
import com.example.eeasyorder.domain.UserRepo;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;

public class GetUserNameUseCase {

    UserRepo userRepo;

    @Inject
    public GetUserNameUseCase(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    public Flowable<String> invoke(){

        return userRepo.readUserName();
    }
}
