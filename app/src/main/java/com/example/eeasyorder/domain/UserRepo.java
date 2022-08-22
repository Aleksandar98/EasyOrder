package com.example.eeasyorder.domain;

import androidx.datastore.preferences.core.Preferences;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface UserRepo {

    public Single<Preferences> saveUserName(String name);

    public Flowable<String> readUserName();
}
