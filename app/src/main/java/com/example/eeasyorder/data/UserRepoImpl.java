package com.example.eeasyorder.data;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.rxjava3.RxDataStore;

import com.example.eeasyorder.domain.UserRepo;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class UserRepoImpl implements UserRepo {

    RxDataStore<Preferences> dataStore;

    public UserRepoImpl(RxDataStore<Preferences> dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public Single<Preferences> saveUserName(String name) {
        Single<Preferences> updateResult =  dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();


            Preferences.Key<String> KEY = PreferencesKeys.stringKey(Constants.DATA_STORE_USERNAME_KEY);

            mutablePreferences.set(KEY, name);


            return Single.just(mutablePreferences);
        });

        return updateResult;
    }

    @Override
    public Flowable<String> readUserName() {
        Preferences.Key<String> NAME_KEY = PreferencesKeys.stringKey(Constants.DATA_STORE_USERNAME_KEY);

        Flowable<String> tokenFlow =
                dataStore.data().map(prefs -> prefs.get(NAME_KEY));

        return tokenFlow;
    }
}
