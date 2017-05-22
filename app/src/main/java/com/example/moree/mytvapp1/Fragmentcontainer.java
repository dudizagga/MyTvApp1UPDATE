package com.example.moree.mytvapp1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;

public class Fragmentcontainer extends AppCompatActivity {
    FragmentManager fm;
    FragmentTransaction ft;
    LinearLayout conteiner;
    ImageButton wifi;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main);
        setPointer();
    }

    private void setPointer() {
        this.context = this;
        conteiner = (LinearLayout) findViewById(R.id.fcontainer);
        wifi = (ImageButton) findViewById(R.id.btnwifi);
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.fcontainer, new Panel());
        ft.commit();
        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });
    }

    public void nextFragment(int container, Fragment className) {
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(container, className).addToBackStack(null);
        ft.commit();
    }
}
