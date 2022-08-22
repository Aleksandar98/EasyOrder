package com.example.eeasyorder.ui.ListScreen;

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
import com.example.eeasyorder.domain.model.ErrorGetRestaurants;
import com.example.eeasyorder.domain.model.ErrorLogin;
import com.example.eeasyorder.domain.model.Restaurant;
import com.example.eeasyorder.domain.model.User;
import com.example.eeasyorder.domain.use_case.get_restaurants.GetRestaurantsUseCase;
import com.example.eeasyorder.domain.use_case.token_usecases.SetTokenUseCase;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ListViewModel extends ViewModel {

    private final GetRestaurantsUseCase getRestaurantsUseCase;
    private final SetTokenUseCase setTokenUseCase;

    private MutableLiveData<Resource<List<Restaurant>>> _restaurantList = new MutableLiveData<Resource<List<Restaurant>>>();
    public LiveData<Resource<List<Restaurant>>> restaurantList = _restaurantList;

    private SingleLiveEvent<Boolean> _clearTokenResult = new SingleLiveEvent<Boolean>();
    public SingleLiveEvent<Boolean> clearTokenResult = _clearTokenResult;

    @Inject
    public ListViewModel(
            GetRestaurantsUseCase getRestaurantsUseCase,
            SetTokenUseCase setTokenUseCase
    ){
        this.getRestaurantsUseCase = getRestaurantsUseCase;
        this.setTokenUseCase = setTokenUseCase;

        getRestaurants();
    }


    public void getRestaurants(){
        HashMap<String,String> body = new HashMap();
        body.put("latitude","44.001783");
        body.put("longitude","21.26907");

        _restaurantList.postValue( new Resource.Loading<>());

        getRestaurantsUseCase.invoke(body)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseDto>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseDto restaurantDtos) {
                            Gson gson = new Gson();
                        if(restaurantDtos.code == 200){

                            RestaurantDto responseDto = gson.fromJson( gson.toJson(restaurantDtos.data), RestaurantDto.class);

                            _restaurantList.postValue( new Resource.Success<>(responseDto.toRestaurants()));

                        }else {
                            ErrorGetRestaurants errorGetRestaurants = gson.fromJson( gson.toJson(restaurantDtos.data), ErrorGetRestaurants.class);
                            _restaurantList.postValue( new Resource.Error<>(errorGetRestaurants.message));
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("TAG", "onError: "+e.getMessage());
                        _restaurantList.postValue( new Resource.Error<>(e.getMessage()));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
