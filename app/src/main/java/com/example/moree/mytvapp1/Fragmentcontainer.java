package com.example.moree.mytvapp1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.baoyz.widget.PullRefreshLayout;

public class Fragmentcontainer extends AppCompatActivity {
    FragmentManager fm;
    FragmentTransaction ft;
    LinearLayout conteiner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main);
    setPointer();
    }

    private void setPointer() {
        conteiner=(LinearLayout)findViewById(R.id.fcontainer);
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.fcontainer, new Panel());
        ft.commit();
    }
    public void nextFragment(int container, Fragment className) {
        fm=getFragmentManager();
        ft=fm.beginTransaction();
        ft.replace(container, className ).addToBackStack(null);
        ft.commit();
    }
}
