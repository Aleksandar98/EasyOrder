package com.example.eeasyorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.eeasyorder.ui.LoginScreen.LoginFragmentDirections;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {



    SweetAlertDialog connectionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        boolean skipLogin  = intent.getBooleanExtra("skipLogin",false);
        String userName  = intent.getStringExtra("loggedUser");



        if(skipLogin){
         NavDirections a = LoginFragmentDirections.actionLoginFragmentToListFragment(userName);
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            if(navHostFragment!= null){
                try{
                    NavController navController = navHostFragment.getNavController();
                    navController.navigate(a);
                }catch (Exception e){
                    Log.e("MainActivity", "onCreate: ", e);
                }

            }
        }

        ConnectivityManager m = (ConnectivityManager) getSystemService(Service.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            m.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    if(connectionDialog != null)
                        connectionDialog.dismiss();
                }

                @Override
                public void onLost(Network network) {

                    connectionDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Internet connection lost");
                    connectionDialog.show();
                }

            });
        }
    }
}