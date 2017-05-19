package com.example.moree.mytvapp1;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by morees on 10/27/2016.
 */

public class utlShared {
    Context context;
    private SharedPreferences MyFavoriteData ;
private SharedPreferences.Editor editor;
    public utlShared() {}


    public utlShared(Context context) {
        this.context = context;
        MyFavoriteData= context.getSharedPreferences("Favorites",Context.MODE_PRIVATE);
        editor=MyFavoriteData.edit();
    }
    public void AddUser(String Link,String Pic) {
            editor.putString(Link,Pic);
            editor.commit();

    }
    //if dosent exites
    public boolean checkKey(String link) {
        String checkuser = MyFavoriteData.getString(link, "na");
        return !checkuser.equals("na");
    }
    //pic equals link with the same value
    public boolean CheckUserPassword(String pic, String link) {
        String user = MyFavoriteData.getString(link, "na");
        return user.equals(pic);
    }
    public void DeleteKey(String key)
    {
        editor.remove(key);
        editor.commit();
    }
}


