package com.nexflare.mtap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class showFavMovie extends AppCompatActivity {
    private static final String TAG = "ERR";
    TextView mtitle,mre,mcast,mrate,mplot,mdir;
    ImageView mpostermovie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_fav_movie);
        Intent i=getIntent();
        String titl=i.getStringExtra("Title");
        mtitle= (TextView) findViewById(R.id.mti);
        mre= (TextView) findViewById(R.id.mre);
        mcast= (TextView) findViewById(R.id.mcast);
        mrate= (TextView) findViewById(R.id.mrate);
        mplot=(TextView)findViewById(R.id.mplot);
        mdir= (TextView) findViewById(R.id.mdir);
        String ti,re,ca,ra,pl,di,po;

        mpostermovie= (ImageView) findViewById(R.id.mpostermovie);
        SharedPreferences sp=getSharedPreferences("Movies",MODE_PRIVATE);

        po=sp.getString(titl+"Poster","");
        ti=sp.getString(titl+"Title","");
        re=sp.getString(titl+"Runtime","");
        ca=sp.getString(titl+"Actors","");
        ra=sp.getString(titl+"imdbRating","");
        pl=sp.getString(titl+"Plot","");
        sp.getString(titl+"Director","");
        di=sp.getString(titl+"Director","");
        Picasso.with(this).load(po).into(mpostermovie);
        mtitle.setText(ti);
        mre.setText(re);
        mcast.setText(ca);
        mrate.setText(ra);
        mplot.setText(pl);
        mdir.setText(di);
        Log.d(TAG, "onCreate: "+ti+re+ca+pl);
    }
}
