package com.example.eeasyorder.ui;

import androidx.lifecycle.ViewModel;

import com.example.eeasyorder.data.SingleLiveEvent;
import com.example.eeasyorder.domain.use_case.token_usecases.GetTokenUseCase;
import com.example.eeasyorder.domain.use_case.token_usecases.SetTokenUseCase;
import com.example.eeasyorder.domain.use_case.user_usecases.GetUserNameUseCase;
import com.example.eeasyorder.domain.use_case.user_usecases.SetUserNameUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class SplashActivityViewModel extends ViewModel {

    private final GetTokenUseCase getTokenUseCase;
    private final GetUserNameUseCase getUserNameUseCase;

    private SingleLiveEvent<String> _getTokenResult = new SingleLiveEvent<String>();
    public SingleLiveEvent<String> getTokenResult = _getTokenResult;

    private SingleLiveEvent<String> _getUserNameResult = new SingleLiveEvent<String>();
    public SingleLiveEvent<String> getUserNameResult = _getUserNameResult;

    @Inject
    public SplashActivityViewModel(GetTokenUseCase getTokenUseCase, GetUserNameUseCase getUserNameUseCase) {
        this.getTokenUseCase = getTokenUseCase;
        this.getUserNameUseCase = getUserNameUseCase;
    }

    public void getToken(){
        getTokenUseCase.invoke().toObservable()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String token) {
                        _getTokenResult.postValue(token);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _getTokenResult.postValue("");

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getUserName(){
        getUserNameUseCase.invoke().toObservable()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String userName) {
                        _getUserNameResult.postValue(userName);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _getUserNameResult.postValue("");

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
