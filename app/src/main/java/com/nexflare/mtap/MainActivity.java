package com.nexflare.mtap;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String TAG="err";
    EditText et;
    Button btn,fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et= (EditText) findViewById(R.id.et);
        btn= (Button) findViewById(R.id.btnSearch);
        fav= (Button) findViewById(R.id.showf);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://www.omdbapi.com/?t="+et.getText().toString()+"&y=&plot=full&r=json";
                if(isConnected()) {
                    Intent intent=new Intent(MainActivity.this,showMovie.class);
                    intent.putExtra("url",url);
                    startActivity(intent);
                }
                else{
                    AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                    alert.setCancelable(false).setMessage("No Network Connection").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog=alert.create();
                    alertDialog.show();
                }
            }
        });
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MainActivity.this,Fav.class);
                startActivity(in);
            }
        });

    }

    private boolean isConnected() {
        //ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }
}
