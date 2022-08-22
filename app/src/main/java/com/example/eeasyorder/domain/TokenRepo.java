package com.example.eeasyorder.domain;

import androidx.datastore.preferences.core.Preferences;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface TokenRepo {

    public Single<Preferences> saveUserToken(String token);

    public Flowable<String> readUserToken();


    public Single<Preferences> clearUserToken();
}
