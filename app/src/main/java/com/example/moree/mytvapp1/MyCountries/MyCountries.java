package com.example.moree.mytvapp1.MyCountries;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.baoyz.widget.PullRefreshLayout;
import com.example.moree.mytvapp1.Bulgaria.BulgariaChannels;
import com.example.moree.mytvapp1.Denmark.DenmarkChannels;
import com.example.moree.mytvapp1.Europ.EUChannels;
import com.example.moree.mytvapp1.Fragmentcontainer;
import com.example.moree.mytvapp1.Israel.IsraelChannels;
import com.example.moree.mytvapp1.Italy.ItalyChannels;
import com.example.moree.mytvapp1.MyFavorite.FavoriteData;
import com.example.moree.mytvapp1.Panel;
import com.example.moree.mytvapp1.R;
import com.example.moree.mytvapp1.Russia.RusChannels;
import com.example.moree.mytvapp1.Spain.SpainChannels;
import com.example.moree.mytvapp1.Turky.TurkyChannels;
import com.example.moree.mytvapp1.UnitedKindom.UKChannels;
import com.squareup.picasso.Picasso;
import com.example.moree.mytvapp1.utlShared;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.moree.mytvapp1.R.id.myBookmarks;

public class MyCountries extends Fragment {
    Context context;
    utlShared utlShared;
    public GridView myCountryGist;
    List<String> CountryFlags = new ArrayList<>();
    List<String> CountryNames = new ArrayList<>();
    ArrayList<String> Flink = new ArrayList<>();
    Panel panel;
    SharedPreferences sharedPreferences;
    Fragmentcontainer fragmentcontainer;
    MyCountryAdapter country;
    boolean saved = false;

    public MyCountries() {

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        context = container.getContext();
        fragmentcontainer = (Fragmentcontainer) getActivity();
        panel = new Panel();
        Toast.makeText(context, "getting data", Toast.LENGTH_SHORT).show();
        getData();
        utlShared = new utlShared(context);
        sharedPreferences = context.getSharedPreferences("Favorites", MODE_PRIVATE);


        Toast.makeText(context, "got data from GetData()", Toast.LENGTH_SHORT).show();
        final View MyCountryInf = inflater.inflate(R.layout.my_grid_view, container, false);
        //     pullRefreshLayout=(PullRefreshLayout)MyCountryInf.findViewById(R.id.swipeRefreshLayout);
//        pullRefreshLayout.setRefreshDrawable(new SmartisanDrawable(context,pullRefreshLayout));
        country = new MyCountryAdapter(context, CountryFlags, CountryNames);
        ;
        myCountryGist = (GridView) MyCountryInf.findViewById(R.id.MyGridView);
        myCountryGist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {
                    //checked
                    case 0:
                        fragmentcontainer.nextFragment(R.id.fcontainer, new UKChannels());
                        break;
                    case 1:
                        //checked
                        fragmentcontainer.nextFragment(R.id.fcontainer, new EUChannels());

                        break;
                    case 2:
                        //checked

                        fragmentcontainer.nextFragment(R.id.fcontainer, new IsraelChannels());
                        break;
                    case 3:
                        //checked
                        fragmentcontainer.nextFragment(R.id.fcontainer, new SpainChannels());

                        break;
                    case 4:
                        //checked

                        fragmentcontainer.nextFragment(R.id.fcontainer, new RusChannels());
                        break;

                    case 5:
                        //checked
                        fragmentcontainer.nextFragment(R.id.fcontainer, new BulgariaChannels());
                        break;

                    case 6:
                        //checked

                        //activity.nextFragment(R.id.fcontainer, new CroatiaChannels());
                        Toast.makeText(context, "UN Active", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        //checked

                        fragmentcontainer.nextFragment(R.id.fcontainer, new DenmarkChannels());
                        break;
                    case 8:
                        //checked
                        fragmentcontainer.nextFragment(R.id.fcontainer, new ItalyChannels());
                        break;
                    case 9:
                        //checked
                        // panel.netcheck();

                        fragmentcontainer.nextFragment(R.id.fcontainer, new TurkyChannels());
                        break;
                    case 10:
                        //checked
                        //activity.nextFragment(R.id.fcontainer, new NetherlandChannels());
                        //panel.netcheck();

                        Toast.makeText(context, "UN Active", Toast.LENGTH_SHORT).show();
                        break;
                    case 11:
                        //checked
                        //activity.nextFragment(R.id.fcontainer, new SerbiaChannels());
                        Toast.makeText(context, "UN Active", Toast.LENGTH_SHORT).show();
                        break;


                }


            }
        });
        return MyCountryInf;

    }

    private void getData() {
        //progress dailog
        CountryFlags.clear();
        CountryNames.clear();
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Getting Data");
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        /////////////////////////////////
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setPageSize(100);
        queryOptions.setOffset(0);
        dataQuery.setQueryOptions(queryOptions);
        Backendless.Persistence.of(MyCountryData.class).find(dataQuery, new AsyncCallback<BackendlessCollection<MyCountryData>>() {
            @Override
            public void handleResponse(BackendlessCollection<MyCountryData> response) {
                for (MyCountryData data : response.getData()) {
                    //my Arraylist CountryFlags,CountryNames
                    CountryFlags.add(data.CountryPic);
                    CountryNames.add(data.CountryName);
                    Flink.add(data.CountryName);

                }
                myCountryGist.setAdapter(new MyCountryAdapter(context, CountryFlags, CountryNames));
                return;
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
        progressDialog.dismiss();

    }


    //get data and add it to an array

    //my main alert


    public void Find(final Context context) {
        this.context = context;
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();
        Backendless.Persistence.of(FavoriteData.class).find(new AsyncCallback<BackendlessCollection<FavoriteData>>() {
            @Override
            public void handleResponse(BackendlessCollection<FavoriteData> response) {
                for (FavoriteData item : response.getData()) {
                    Flink.add(item.FavoriteLink);

                }
                Toast.makeText(context, "Data", Toast.LENGTH_SHORT).show();
                pd.setCancelable(true);
                pd.dismiss();
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });


    }


    public void MyAlertDialog1(final Context context, final String SaveLink, final String SavePic, final String ChannelPic, final String getString) {
        this.context = context;
        Toast.makeText(context, "My Alert", Toast.LENGTH_SHORT).show();
        //my alert
        AlertDialog.Builder mysingleAlert = new AlertDialog.Builder(context);
        // my inflate for my alert
        final View FavoriteIn = LayoutInflater.from(context).inflate(R.layout.alert_favorite, null, false);
        //my bookmark sign
        final TextView bookmarks = (TextView) FavoriteIn.findViewById(myBookmarks);
        //my image
        final ImageView Image = (ImageView) FavoriteIn.findViewById(R.id.myimg);
        Picasso.with(context)
                .load(ChannelPic)
                .into(Image);
        if (Flink.isEmpty()) {
            bookmarks.setTextColor(Color.WHITE);
        }
        if (Flink.contains(SaveLink)) {
            Toast.makeText(context, "Channel Exists", Toast.LENGTH_SHORT).show();
            bookmarks.setTextColor(Color.YELLOW);
            mysingleAlert.setView(FavoriteIn);
            mysingleAlert.show();
            return;
        } else {
            bookmarks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog pd = new ProgressDialog(context);
                    pd.setMessage("Loading");
                    pd.setCancelable(false);
                    pd.show();
                    Backendless.Persistence.of(FavoriteData.class).find(new AsyncCallback<BackendlessCollection<FavoriteData>>() {
                        @Override
                        public void handleResponse(BackendlessCollection<FavoriteData> response) {
                            for (FavoriteData item : response.getData()) {
                                Flink.add(item.FavoriteLink);
                            }

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                        }
                    });
                    if (Flink.contains(SaveLink)) {
                        bookmarks.setTextColor(Color.YELLOW);
                        Backendless.Persistence.of(FavoriteData.class).find(new AsyncCallback<BackendlessCollection<FavoriteData>>() {
                            @Override
                            public void handleResponse(BackendlessCollection<FavoriteData> response) {
                                for (FavoriteData item : response.getData()) {
                                    Flink.add(item.FavoriteLink);
                                    Toast.makeText(context, "pd was dissmissed", Toast.LENGTH_SHORT).show();
                                    pd.dismiss();
                                }
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {

                            }
                        });
                        pd.dismiss();
                        return;
                    } else {
                        FavoriteData fData = new FavoriteData();
                        fData.FavoriteLink = SaveLink;
                        fData.FavoritePic = SavePic;
                        Backendless.Persistence.of(FavoriteData.class).save(fData, new AsyncCallback<FavoriteData>() {
                            @Override
                            public void handleResponse(FavoriteData response) {
                                Toast.makeText(context, "Channel Saved", Toast.LENGTH_SHORT).show();
                                bookmarks.setTextColor(Color.YELLOW);
                                Find(context);
                                pd.dismiss();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {

                            }
                        });
                        return;
                    }

                }
            });
        }

        mysingleAlert.setView(FavoriteIn);
        mysingleAlert.show();
    }

    public void MyAlertDialog2(final Context context, final String SaveLink, final String SavePic) {
        this.context = context;
        utlShared = new utlShared(context);
        Toast.makeText(context, "My Alert2", Toast.LENGTH_SHORT).show();
        //my alert
        final AlertDialog mysingleAlert = new AlertDialog.Builder(context).create();
        // my inflate for my alert
        final View FavoriteIn = LayoutInflater.from(context).inflate(R.layout.alert_favorite, null, false);
        //my bookmark sign
        final TextView bookmarks = (TextView) FavoriteIn.findViewById(myBookmarks);
        //my image
        final ImageView Image = (ImageView) FavoriteIn.findViewById(R.id.myimg);
        Picasso.with(context)
                .load(SavePic)
                .into(Image);
        /*
        if (Flink.isEmpty()) {
            bookmarks.setTextColor(Color.WHITE);
        }
        */
        if (utlShared.checkKey(SaveLink)) {
            Toast.makeText(context, "Channel Exists", Toast.LENGTH_SHORT).show();
            bookmarks.setTextColor(Color.YELLOW);
            mysingleAlert.setView(FavoriteIn);
            mysingleAlert.show();
            return;
        } else {
            bookmarks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog pd1 = new ProgressDialog(context);
                    pd1.setMessage("Saving Channel");
                    pd1.setCancelable(false);
                    pd1.show();
                    if (utlShared.checkKey(SaveLink)) {
                        bookmarks.setTextColor(Color.YELLOW);
                        pd1.dismiss();
                        return;
                    } else {
                        utlShared.AddUser(SaveLink,SavePic);
                        bookmarks.setTextColor(Color.YELLOW);
                        Toast.makeText(context, "Channel Saved", Toast.LENGTH_SHORT).show();
                        pd1.dismiss();
                        return;
                    }
                }
            });
        }


        mysingleAlert.setView(FavoriteIn);
        mysingleAlert.show();
    }

    //not useable now
    public void SharedAler(final Context context, final String Link, final String Pic) {
        this.context = context;
        utlShared = new utlShared(context);
        Toast.makeText(context, "My Alert", Toast.LENGTH_SHORT).show();
        //my alert
        AlertDialog.Builder mysingleAlert = new AlertDialog.Builder(context);
        // my inflate for my alert
        final View FavoriteIn = LayoutInflater.from(context).inflate(R.layout.alert_favorite, null, false);
        //my bookmark sign
        final TextView bookmarks = (TextView) FavoriteIn.findViewById(myBookmarks);
        //my image
        final ImageView Image = (ImageView) FavoriteIn.findViewById(R.id.myimg);
        Picasso.with(context)
                .load(Pic)
                .into(Image);
        bookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (utlShared.checkKey(Pic)) {
                    Toast.makeText(context, "Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                utlShared.AddUser(Pic, Link);
                Toast.makeText(context, "Pic" + Link + "\t" + "Link\n" + Pic, Toast.LENGTH_SHORT).show();
                bookmarks.setTextColor(Color.YELLOW);
                Toast.makeText(context, "Data was Saved", Toast.LENGTH_SHORT).show();
                return;
            }
        });
        mysingleAlert.setView(FavoriteIn);
        mysingleAlert.show();

    }


    public void DeleteData(String ojectId) {
        FavoriteData fav = new FavoriteData();
        fav.objectId = ojectId;
        Backendless.Persistence.of(FavoriteData.class).remove(fav, new AsyncCallback<Long>() {
            @Override
            public void handleResponse(Long response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }
}








