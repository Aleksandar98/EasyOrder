package com.example.eeasyorder.ui.LoginScreen;

import android.util.Log;

import androidx.datastore.preferences.core.Preferences;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eeasyorder.data.Resource;
import com.example.eeasyorder.data.SingleLiveEvent;
import com.example.eeasyorder.data.remote.dto.ResponseDto;
import com.example.eeasyorder.data.remote.dto.RestaurantDto;
import com.example.eeasyorder.data.remote.dto.UserDto;
import com.example.eeasyorder.domain.model.ErrorLogin;
import com.example.eeasyorder.domain.model.User;
import com.example.eeasyorder.domain.use_case.get_restaurants.GetRestaurantsUseCase;
import com.example.eeasyorder.domain.use_case.login.LoginUserUseCase;
import com.example.eeasyorder.domain.use_case.token_usecases.SetTokenUseCase;
import com.example.eeasyorder.domain.use_case.user_usecases.SetUserNameUseCase;
import com.google.gson.Gson;

import java.util.HashMap;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    private final LoginUserUseCase loginUserUseCase;
    private final SetTokenUseCase setTokenUseCase;
    private final SetUserNameUseCase setUserNameUseCase;

    private SingleLiveEvent<Resource<User>> _loginResult = new SingleLiveEvent<Resource<User>>();
    public SingleLiveEvent<Resource<User>> loginResult = _loginResult;

    private SingleLiveEvent<Boolean> _setTokenResult = new SingleLiveEvent<Boolean>();
    public SingleLiveEvent<Boolean> setTokenResult = _setTokenResult;

    private SingleLiveEvent<Boolean> _setUserNameResult = new SingleLiveEvent<Boolean>();
    public SingleLiveEvent<Boolean> setUserNameResult = _setUserNameResult;

    @Inject
    public LoginViewModel(LoginUserUseCase loginUserUseCase, SetTokenUseCase setTokenUseCase, SetUserNameUseCase setUserNameUseCase) {
        this.loginUserUseCase = loginUserUseCase;
        this.setTokenUseCase = setTokenUseCase;
        this.setUserNameUseCase = setUserNameUseCase;
    }


    public void saveUserName(String name){
        setUserNameUseCase.invoke(name).toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.rxjava3.core.Observer<Preferences>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Preferences preferences) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        _setUserNameResult.postValue(true);

                    }
                });
    }
    public void saveToken(String token){
        setTokenUseCase.invokeSetToken(token).toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.rxjava3.core.Observer<Preferences>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Preferences preferences) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        _setTokenResult.postValue(true);

                    }
                });
    }
    public void loginUser(String email, String password){
        Log.d("LoginViewModel", "loginUser: called");
        HashMap<String,String> body = new HashMap();
        body.put("email",email);
        body.put("password",password);

        _loginResult.postValue(new Resource.Loading());

        Gson gson = new Gson();

        loginUserUseCase.invoke(body).toObservable()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseDto>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull ResponseDto userDtoResponse) {

                        if(userDtoResponse.code == 200){
                            try{
                                UserDto userDto = gson.fromJson( gson.toJson(userDtoResponse.data), UserDto.class);
                                _loginResult.postValue(
                                        new Resource.Success(
                                                new User(userDto.customer_account.first_name,
                                                        userDto.token.value)));
                            }catch (Exception e){
                                _loginResult.postValue(new Resource.Error(e.getMessage()));
                            }


                        }
                        else{
                            ErrorLogin errorLogin = gson.fromJson( gson.toJson(userDtoResponse.data), ErrorLogin.class);
                            _loginResult.postValue(new Resource.Error(
                                    errorLogin.validations.title + " " + errorLogin.validations.body
                            ));
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _loginResult.postValue(new Resource.Error(e.getMessage()));
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

}
