package com.example.eeasyorder.di;

import android.content.Context;

import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import com.example.eeasyorder.data.Constants;
import com.example.eeasyorder.data.RestaurantRepoImpl;
import com.example.eeasyorder.data.TokenRepoImpl;
import com.example.eeasyorder.data.UserRepoImpl;
import com.example.eeasyorder.data.remote.MenuPlaygroundApi;
import com.example.eeasyorder.domain.RestaurantRepo;
import com.example.eeasyorder.domain.TokenRepo;
import com.example.eeasyorder.domain.UserRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    MenuPlaygroundApi provideMenuApi(){
        return new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(MenuPlaygroundApi.class);

    }

    @Provides
    @Singleton
    RxDataStore<Preferences> provideTokenDataStore(@ApplicationContext Context appContext){
        return new RxPreferenceDataStoreBuilder(appContext, /*name=*/ "token").build();
    }


    @Provides
    @Singleton
    RestaurantRepo provideRestaurantRepository(MenuPlaygroundApi api){
        return new RestaurantRepoImpl(api);
    }

    @Provides
    @Singleton
    TokenRepo provideTokenRepository(RxDataStore<Preferences> dataStore){
        return new TokenRepoImpl(dataStore);
    }

    @Provides
    @Singleton
    UserRepo provideUserRepository(RxDataStore<Preferences> dataStore){
        return new UserRepoImpl(dataStore);
    }
}
