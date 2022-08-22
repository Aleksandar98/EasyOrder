package com.example.eeasyorder.ui.LoginScreen;

import android.os.Bundle;

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
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.eeasyorder.R;
import com.example.eeasyorder.SplashActivity;
import com.example.eeasyorder.data.Constants;
import com.example.eeasyorder.data.Resource;
import com.example.eeasyorder.data.Utils;
import com.example.eeasyorder.domain.model.User;
import com.example.eeasyorder.ui.LoginScreen.LoginViewModel;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class LoginFragment extends Fragment {

    private LoginViewModel viewModel;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        EditText emailEditText = view.findViewById(R.id.emailEditText);
        EditText passwordEditText = view.findViewById(R.id.passwordEditText);
        Button signInBtn = view.findViewById(R.id.signInBtn);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        viewModel.loginResult.observe(getViewLifecycleOwner(), new Observer<Resource<User>>() {
            @Override
            public void onChanged(Resource<User> s) {

                if(s instanceof Resource.Success){
                    progressBar.setVisibility(View.GONE);
                    User loggedUser = (User) s.data;
                    Navigation.findNavController(view).navigate(LoginFragmentDirections.actionLoginFragmentToListFragment(loggedUser.first_name));

                    viewModel.saveUserName(loggedUser.first_name);
                    viewModel.saveToken(loggedUser.tokenValue);
                }

                if(s instanceof Resource.Loading){
                    progressBar.setVisibility(View.VISIBLE);
                }

                if(s instanceof Resource.Error){
                    progressBar.setVisibility(View.GONE);
                    Resource.Error error = (Resource.Error) s;

                    new SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Something went wrong! Error: " + error.message)
                            .show();

                }
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String pass = passwordEditText.getText().toString();

                if(Utils.validateEmail(email)){
                    viewModel.loginUser(email,pass);
                }else{
                    new SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Wrong email format")
                            .show();
                }
//                viewModel.loginUser("test@testmenu.app","test1234");

            }
        });




        return view;
    }

}