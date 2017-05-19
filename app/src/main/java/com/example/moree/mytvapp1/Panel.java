package com.example.moree.mytvapp1;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moree.mytvapp1.Catagory.Categories;
import com.example.moree.mytvapp1.MyCountries.MyCountries;
import com.example.moree.mytvapp1.MyFavorite.Favorite;

import java.util.List;

/**
 * Created by moree on 12/31/2016.
 */

public class Panel extends Fragment {
    Context context;
    LinearLayout container1;
    WifiManager wifiManager;
    public ConnectivityManager cm;
    public NetworkInfo activeNetwork;
    public ProgressDialog resetNetwork;
    Fragmentcontainer fragmentcontainer;

    public Panel() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        fragmentcontainer = (Fragmentcontainer) getActivity();
        NetWorkName();
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


        final View panel = inflater.inflate(R.layout.panel, container, false);

        ImageView btnCategories = (ImageView) panel.findViewById(R.id.btnCategories);
        ImageView btnFavorites = (ImageView) panel.findViewById(R.id.btnFavorites);
        ImageView btnCountries = (ImageView) panel.findViewById(R.id.btnCountries);
        ImageView btnLogout = (ImageView) panel.findViewById(R.id.btnLogout);

        container1 = (LinearLayout) panel.findViewById(R.id.container1);

//button for the next fragment
        btnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //method that replace fragment with the id of container and its new fragments
                fragmentcontainer.nextFragment(R.id.fcontainer, new Categories());
            }
        });
        btnCountries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentcontainer.nextFragment(R.id.fcontainer, new MyCountries());
                Toast.makeText(context, "moving to  My countries", Toast.LENGTH_SHORT).show();
            }
        });
        btnFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentcontainer.nextFragment(R.id.fcontainer, new Favorite());
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Logout", Toast.LENGTH_SHORT).show();
            }
        });

        return panel;
    }

    //one
    public void NetWorkName() {
        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {

                Toast.makeText(context, activeNetwork.getExtraInfo(), Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(context, "Error Getting Data", Toast.LENGTH_SHORT).show();
            IntentWifi();
            return;

        }
        if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
            Toast.makeText(context, "connect2 to" + activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    //turn on the wifi
    public void IntentWifi() {

        resetNetwork = new ProgressDialog(context);
        ;
        final AlertDialog.Builder reload = new AlertDialog.Builder(context);
        reload.setTitle("Connection Error");
        reload.setMessage("Turn on the Wifi");
        reload.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        reload.setPositiveButton("Turn ON", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                //startActivity(new Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS));


            }
        });
        reload.show();
    }




}