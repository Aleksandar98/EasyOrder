package com.example.eeasyorder.ui.ListScreen;

import android.os.Bundle;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.eeasyorder.R;
import com.example.eeasyorder.SplashActivity;
import com.example.eeasyorder.data.Resource;
import com.example.eeasyorder.domain.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class ListFragment extends Fragment implements RestaurantListAdapter.ItemClickListener {

    private ListViewModel viewModel;



    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ListViewModel.class);
        //viewModel.getRestaurants();
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        Button logoutBtn = view.findViewById(R.id.logoutBtn);
        TextView welcomeMessage = view.findViewById(R.id.welcomeMessage);

        String loggedUserName = ListFragmentArgs.fromBundle(getArguments()).getLoggedUser();

        welcomeMessage.setText("Welcome " + loggedUserName);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewModel.clearUserToken();

            }
        });

        RestaurantListAdapter adapter = new RestaurantListAdapter(new ArrayList<Restaurant>());
        adapter.setClickListener(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));


        viewModel.clearTokenResult.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean didClear) {
                if(didClear)
                Navigation.findNavController(view).popBackStack();
            }
        });

        viewModel.restaurantList.observe(getViewLifecycleOwner(), new Observer<Resource<List<Restaurant>>>() {
            @Override
            public void onChanged(Resource<List<Restaurant>> response) {
                if(response instanceof Resource.Success){
                    progressBar.setVisibility(View.GONE);
                    adapter.updateList(response.data);
                    adapter.notifyDataSetChanged();

                }

                if(response instanceof Resource.Loading){
                    progressBar.setVisibility(View.VISIBLE);
                }

                if(response instanceof Resource.Error){
                    progressBar.setVisibility(View.GONE);
                    Resource.Error error = (Resource.Error) response;

                    new SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Something went wrong! Error: " + error.message)
                            .show();
                }
            }
        });

        return view;
    }



    @Override
    public void onItemClick(View view, int position, Restaurant restaurant) {
        ListFragmentDirections.ActionListFragmentToDetailFragment action = ListFragmentDirections.actionListFragmentToDetailFragment(restaurant);
        Navigation.findNavController(view).navigate(action);
    }
}