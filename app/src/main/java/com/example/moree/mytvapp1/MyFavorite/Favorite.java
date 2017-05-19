package com.example.moree.mytvapp1.MyFavorite;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.moree.mytvapp1.Fragmentcontainer;
import com.example.moree.mytvapp1.MainActivity;
import com.example.moree.mytvapp1.MyCountries.MyCountries;
import com.example.moree.mytvapp1.MyCountries.MyCountryAdapter;
import com.example.moree.mytvapp1.R;
import com.example.moree.mytvapp1.Video;
import com.example.moree.mytvapp1.utlShared;

import java.util.ArrayList;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.backendless.servercode.services.codegen.ServiceCodeFormat.android;

/**
 * Created by moree on 2/2/2017.
 */

public class Favorite extends Fragment {
    Context context;
    utlShared utlShared;
    public ArrayList<String> Favorite_Links = new ArrayList<>();
    public ArrayList<String> Favorite_Pics = new ArrayList<>();
    public ArrayList<String> Favorite_OBid = new ArrayList<>();
    public ArrayList<String> Favorite_pic = new ArrayList<>();
    ProgressDialog pd;
    MyCountries myCountries;
    GridView FavoriteGrid;
    Fragmentcontainer fragmentcontainer;
    SharedPreferences sharedPreferences;

    public Favorite() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        fragmentcontainer = (Fragmentcontainer) getActivity();
        myCountries = new MyCountries();
        Favorite_Links.clear();
        Favorite_Pics.clear();
        sharedPreferences = context.getSharedPreferences("Favorites", MODE_PRIVATE);
        utlShared = new utlShared(context);
        GetFavoriteShared();
        View FavoriteInf = inflater.inflate(R.layout.my_grid_view, container, false);
        FavoriteGrid = (GridView) FavoriteInf.findViewById(R.id.MyGridView);
        FavoriteGrid.setAdapter(new MyCountryAdapter(context, Favorite_pic, Favorite_Links));
        //  SaveLinks();
        FavoriteGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(context, Video.class);
                intent.putExtra("link", (Favorite_Links.get(position)));
                context.startActivity(intent);

            }
        });

        FavoriteGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                AlertDialog.Builder mydi = new AlertDialog.Builder(context);
                //  mydi.setMessage("Are you use you want to delete\n"+Favorite_OBid.get(position));
                mydi.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mydi.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        utlShared.DeleteKey(Favorite_Links.get(position));

                        /*
                        pd=new ProgressDialog(context);
                        pd.setMessage("Loading");
                        pd.show();
                        /*
                        final FavoriteData fav = new FavoriteData();
                        fav.objectId = Favorite_OBid.get(position);
                        fav.FavoriteLink=Favorite_Links.get(position);
                        /*
                        Backendless.Persistence.of(FavoriteData.class).remove(fav, new AsyncCallback<Long>() {
                            @Override
                            public void handleResponse(Long response) {
                                Toast.makeText(context, "item deleted", Toast.LENGTH_SHORT).show();
                                Favorite_Links.clear();
                                Favorite_Pics.clear();
                                Favorite_OBid.clear();
                                GetLinks();
                                pd.dismiss();

                            }


                            @Override
                            public void handleFault(BackendlessFault fault) {

                            }
                        });
                        */
                    }
                });


                mydi.show();
                return false;
            }
        });

        return FavoriteInf;

    }

    public void Ids(String id) {
        FavoriteData favoriteData = new FavoriteData();
        favoriteData.objectId = id;
        Backendless.Persistence.of(FavoriteData.class).remove(favoriteData, new AsyncCallback<Long>() {
            @Override
            public void handleResponse(Long response) {
                Toast.makeText(context, "item deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    private void SaveLinks() {
        FavoriteData favoriteData = new FavoriteData();
        favoriteData.FavoriteLink = "dgsdf";
        Backendless.Persistence.of(FavoriteData.class).save(favoriteData, new AsyncCallback<FavoriteData>() {
            @Override
            public void handleResponse(FavoriteData response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    public void GetLinks() {
        Backendless.Persistence.of(FavoriteData.class).find(new AsyncCallback<BackendlessCollection<FavoriteData>>() {
            @Override
            public void handleResponse(BackendlessCollection<FavoriteData> response) {
                for (FavoriteData item : response.getData()) {
                    Favorite_Pics.add(item.FavoritePic);
                    Favorite_Links.add(item.FavoriteLink);
                    Favorite_OBid.add(item.objectId);
                }
                FavoriteGrid.setAdapter(new MyCountryAdapter(context, Favorite_Pics, Favorite_Links));
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

    }

    private void GetFavoriteShared() {
        /*
        SharedPreferences.Editor ed=sharedPreferences.edit();
        ed.clear();
        ed.commit();
*/
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Map<String, String> map = (Map<String, String>) sharedPreferences.getAll();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            editor.putString(entry.getKey(), entry.getValue());
            editor.commit();
            String Pic = sharedPreferences.getString(entry.getKey(), "");
            Favorite_Links.add(Pic);
            Favorite_pic.add(entry.getKey());
            Toast.makeText(context, "" + Pic, Toast.LENGTH_SHORT).show();
        }
        // Toast.makeText(context, "myLink"+Link+"\n"+"myPic"+"\n"+Pic, Toast.LENGTH_LONG).show();


    }

}
