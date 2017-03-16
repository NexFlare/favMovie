package com.nexflare.mtap;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class showMovie extends AppCompatActivity {
    private static final String TAG = "Err";
    RequestQueue rq;
    TextView title,re,cast,rate,plot,dir;
    Button addto;
    ImageView postermovie;
    ProgressDialog progress=null;
    Gson mGson=new Gson();
    NotificationCompat.Builder notify;
    Movie m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie);
        notify=new NotificationCompat.Builder(this);
        notify.setAutoCancel(true);
        title= (TextView) findViewById(R.id.ti);
        re= (TextView) findViewById(R.id.re);
        postermovie= (ImageView) findViewById(R.id.postermovie);
        addto= (Button) findViewById(R.id.btnfav);
        cast= (TextView) findViewById(R.id.cast);
        plot= (TextView) findViewById(R.id.plot);
        rate= (TextView) findViewById(R.id.rate);
        dir= (TextView) findViewById(R.id.dir);
        rq= Volley.newRequestQueue(this);
        Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        progress=new ProgressDialog(this,ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Loading your Movie");
        progress.setCancelable(false);
        progress.show();
        JsonObjectRequest jor=new JsonObjectRequest(url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: "+response);
                        progress.dismiss();

                        m=mGson.fromJson(response.toString(),Movie.class);
                        Log.d(TAG, "onResponse: "+m.getResponse());
                        if(m.getResponse().equals("False")){
                            AlertDialog.Builder alert=new AlertDialog.Builder(showMovie.this);
                            alert.setMessage("The Movie is not Available").setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            AlertDialog alertBox=alert.create();
                            alertBox.show();
                        }
                        addto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                writeinFile(m.getTitle());
                                Intent intent =new Intent(showMovie.this,Fav.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        update();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        Toast.makeText(showMovie.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        rq.add(jor);
    }
    private void writeinFile(String name) {
        SharedPreferences sp = getSharedPreferences("Movies", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        if (!sp.contains(m.getTitle() + "Title")){
            notifyme();
            e.putString(m.getTitle() + "Released", m.getReleased());
        e.putString(m.getTitle() + "Title", m.getTitle());
            e.putString(m.getTitle()+"Poster",m.getPoster());
        e.putString(m.getTitle() + "Genre", m.getGenre());
        e.putString(m.getTitle() + "Director", m.getDirector());
        e.putString(m.getTitle() + "Actors", m.getActors());
        e.putString(m.getTitle() + "Plot", m.getPlot());
        e.putString(m.getTitle() + "imdbRating", m.getImdbRating());
        e.putString(m.getTitle() + "Runtime", m.getRuntime());
        e.putString(m.getTitle() + "Poster", m.getPoster());
        e.apply();
        File file = getFilesDir();
        File in = new File(file, "MovieFile");
        FileOutputStream ou = null;
        try {
            ou = new FileOutputStream(in, true);
            ou.write(m.getTitle().getBytes());
            ou.write('\n');
        } catch (IOException e1) {
            e1.printStackTrace();
        }
     }
        else{
            Toast.makeText(this, "Already Added to Favorite", Toast.LENGTH_SHORT).show();
        }
    }

    private void notifyme() {
        notify.setContentTitle("mTap").setSmallIcon(R.mipmap.ic_launcher).setTicker("Movie Added").
                setContentText(m.getTitle()+" Added to your Favorites").setVibrate(new long[]{1000,1000,1000,1000})
                .setWhen(System.currentTimeMillis()).setStyle(new android.support.v4.app.NotificationCompat.BigTextStyle());
        Intent intent =new Intent(showMovie.this,Fav.class);
        PendingIntent PI=PendingIntent.getActivities(showMovie.this,1200, new Intent[]{intent},PendingIntent.FLAG_UPDATE_CURRENT);
        notify.setContentIntent(PI);
        NotificationManager nm= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(125,notify.build());
    }

    private void update() {
        Picasso.with(this).load(m.getPoster()).into(postermovie);
        title.setText(m.getTitle());
        re.setText(m.getRuntime());
        cast.setText(m.getActors());
        rate.setText(m.getImdbRating());
        plot.setText(m.getPlot());
        dir.setText(m.getDirector());

    }
}
