package com.example.eeasyorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.eeasyorder.data.Constants;
import com.example.eeasyorder.ui.SplashActivityViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
@AndroidEntryPoint
public class SplashActivity extends AppCompatActivity {

    Disposable disposable;
    Disposable disposableName;

    SplashActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        viewModel = new ViewModelProvider(this).get(SplashActivityViewModel.class);

        viewModel.getToken();

        viewModel.getTokenResult.observe(this, new androidx.lifecycle.Observer<String>() {
            @Override
            public void onChanged(String token) {
                if(!token.isEmpty())
                    viewModel.getUserName();
                else
                    navigateToMain(false, "");

            }
        });

        viewModel.getUserNameResult.observe(this, new androidx.lifecycle.Observer<String>() {
            @Override
            public void onChanged(String userName) {
                navigateToMain(true, userName);

            }
        });

    }


    void navigateToMain(Boolean skipLogin,String userName){
        Intent i = new Intent(SplashActivity.this,MainActivity.class);
        i.putExtra("skipLogin",skipLogin);
        i.putExtra("loggedUser",userName);
        startActivity(i);
        finish();
    }

}