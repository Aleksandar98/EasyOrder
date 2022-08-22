package com.example.eeasyorder.ui.DetailScreen;

import androidx.datastore.preferences.core.Preferences;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.example.eeasyorder.R;
import com.example.eeasyorder.data.Resource;
import com.example.eeasyorder.data.SingleLiveEvent;
import com.example.eeasyorder.domain.model.User;
import com.example.eeasyorder.domain.use_case.token_usecases.SetTokenUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;
@HiltViewModel
public class DetailViewModel extends ViewModel {

    private final SetTokenUseCase setTokenUseCase;

    private SingleLiveEvent<Boolean> _clearTokenResult = new SingleLiveEvent<Boolean>();
    public SingleLiveEvent<Boolean> clearTokenResult = _clearTokenResult;

    @Inject
    public DetailViewModel(SetTokenUseCase setTokenUseCase) {
        this.setTokenUseCase = setTokenUseCase;
    }

    public void clearUserToken() {

        setTokenUseCase.invokeClearToken().toObservable()
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
                        _clearTokenResult.postValue(true);

                    }
                });
    }

}
