package com.example.eeasyorder.ui.DetailScreen;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.eeasyorder.R;
import com.example.eeasyorder.SplashActivity;
import com.example.eeasyorder.domain.model.Restaurant;
import com.example.eeasyorder.ui.ListScreen.ListViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class DetailFragment extends Fragment {


    private DetailViewModel viewModel;

    public DetailFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
                View view = inflater.inflate(R.layout.fragment_detail, container, false);

        Restaurant restaurant = DetailFragmentArgs.fromBundle(getArguments()).getSelectedRestaurant();

        ImageView restaurantImage = view.findViewById(R.id.restaurantImage);
        Button logoutBtn = view.findViewById(R.id.logoutBtn);
        TextView title = view.findViewById(R.id.title);
        TextView welcomeMessage = view.findViewById(R.id.welcomeMessage);
        TextView description = view.findViewById(R.id.description);
        TextView noRestaurantImageTxt = view.findViewById(R.id.noRestaurantImageTxt);
        CardView isClosedCard = view.findViewById(R.id.closedCard);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        if(!restaurant.isOpen)
        isClosedCard.setVisibility(View.VISIBLE);


        if(restaurant.imageUrl.isEmpty()){
            progressBar.setVisibility(View.GONE);
            noRestaurantImageTxt.setVisibility(View.VISIBLE);
            Glide.with(view).load(R.drawable.blur_restaurant).into(restaurantImage);
        }else
            Glide.with(view).load(restaurant.imageUrl).listener(
                    new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    }
            ).into(restaurantImage);

        title.setText(restaurant.name);
        welcomeMessage.setText(restaurant.welcomeMessage);
        description.setText(restaurant.description);

        viewModel.clearTokenResult.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean didClear) {
                if (didClear){
                    Navigation.findNavController(view).popBackStack(R.id.loginFragment,false);
                }
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewModel.clearUserToken();
                //clearUserToken(view);
            }
        });

        return view;
    }

//    void clearUserToken(View view){
//        Single<Preferences> updateResult =  SplashActivity.dataStore.updateDataAsync(prefsIn -> {
//            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
//
//
//            Preferences.Key<String> KEY = PreferencesKeys.stringKey("access_token");
//
//            mutablePreferences.set(KEY, "");
//
//
//            return Single.just(mutablePreferences);
//        });
//
//        updateResult.toObservable()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(new io.reactivex.rxjava3.core.Observer<Preferences>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull Preferences preferences) {
//
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Navigation.findNavController(view).popBackStack(R.id.loginFragment,false);
//                    }
//                });
//    }
}