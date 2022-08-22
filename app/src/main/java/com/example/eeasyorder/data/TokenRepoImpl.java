package com.example.eeasyorder.data;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.rxjava3.RxDataStore;

import com.example.eeasyorder.SplashActivity;
import com.example.eeasyorder.domain.TokenRepo;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class TokenRepoImpl implements TokenRepo {

    RxDataStore<Preferences> dataStore;

    public TokenRepoImpl(RxDataStore<Preferences> dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public Single<Preferences> saveUserToken(String token) {
        Single<Preferences> updateResult =  dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();


            Preferences.Key<String> KEY = PreferencesKeys.stringKey(Constants.DATA_STORE_TOKEN_KEY);

            mutablePreferences.set(KEY, token);


            return Single.just(mutablePreferences);
        });

        return updateResult;
    }

    @Override
    public Flowable<String> readUserToken() {
        Preferences.Key<String> TOKEN_KEY = PreferencesKeys.stringKey(Constants.DATA_STORE_TOKEN_KEY);

        Flowable<String> tokenFlow =
                dataStore.data().map(prefs -> prefs.get(TOKEN_KEY));

        return tokenFlow;
    }

    @Override
    public Single<Preferences> clearUserToken() {
        Single<Preferences> updateResult =  dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();


            Preferences.Key<String> KEY = PreferencesKeys.stringKey(Constants.DATA_STORE_TOKEN_KEY);

            mutablePreferences.set(KEY, "");


            return Single.just(mutablePreferences);
        });

        return updateResult;
    }
}
