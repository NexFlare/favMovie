package com.nexflare.mtap;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Fav extends AppCompatActivity {
    RecyclerView rv;
    ArrayList<favMovie> movie;
    MovieAdapter ma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        rv= (RecyclerView) findViewById(R.id.rv);

        movie=new ArrayList<>();
        readFromFile();
        ma=new MovieAdapter();
        rv.setLayoutManager(new GridLayoutManager(this,2));
        rv.setAdapter(ma);
    }
    class VH extends RecyclerView.ViewHolder{
        TextView tv;
        CardView cv;
        ImageView imv;
        public VH(View itemView) {
            super(itemView);
            tv= (TextView) itemView.findViewById(R.id.movie);
            imv= (ImageView) itemView.findViewById(R.id.imvPoster);
            cv= (CardView) itemView.findViewById(R.id.cv);
        }
    }
    class MovieAdapter extends RecyclerView.Adapter<VH>{

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater lf=getLayoutInflater();
            View v=lf.inflate(R.layout.listmovies,parent,false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(final VH holder, final int position) {
            final favMovie s=movie.get(position);
            holder.tv.setText(s.getTitle());

            Picasso.with(Fav.this).load(s.getPoster()).into(holder.imv);
            holder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Fav.this,showFavMovie.class);
                    intent.putExtra("Title",s.getTitle());
                    startActivity(intent);
                }
            });
            holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder alert=new AlertDialog.Builder(Fav.this);
                    alert.setMessage("Do u really want to delete "+s.getTitle()+" ?").setPositiveButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setNegativeButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences sp=getSharedPreferences("Movies",MODE_PRIVATE);
                            SharedPreferences.Editor e=sp.edit();
                            e.remove(s.getTitle()+"Title");
                            e.remove(s.getTitle() + "Genre");
                            e.remove(s.getTitle() + "Director");
                            e.remove(s.getTitle() + "Actors");
                            e.remove(s.getTitle() + "Plot");
                            e.remove(s.getTitle() + "imdbRating");
                            e.remove(s.getTitle() + "Runtime");
                            e.remove(s.getTitle() + "Poster");
                            e.apply();
                            deleteFromFile(s.getTitle());
                            movie.remove(position);
                            ma.notifyDataSetChanged();
                        }
                    });
                    AlertDialog alertBox=alert.create();
                    alertBox.show();
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return movie.size();
        }
    }
    private void readFromFile() {
        File file=getFilesDir();
        File in=new File(file,"MovieFile");
        FileInputStream fin=null;
        try {
            fin=new FileInputStream(in);
            InputStreamReader isr=new InputStreamReader(fin);
            BufferedReader bufRdr=new BufferedReader(isr);
            String str="";
            try {
                while((str=bufRdr.readLine())!=null){
                    SharedPreferences sp=getSharedPreferences("Movies",MODE_PRIVATE);
                    if(sp.contains(str+"Title")) {
                        String url = sp.getString(str + "Poster", "");
                        movie.add(new favMovie(str, url));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void deleteFromFile(String name) {
        File file=getFilesDir();
        File in=new File(file,"MovieFile");
        File temp=new File(file,"temp");
        FileInputStream fin=null;
        FileOutputStream ou=null;
        try {
            ou=new FileOutputStream(temp,true);
            fin=new FileInputStream(in);
            InputStreamReader isr=new InputStreamReader(fin);
            BufferedReader bufRdr=new BufferedReader(isr);
            String str="";
            try {
                while((str=bufRdr.readLine())!=null){

                    if(str.equals(name)){
                        continue;
                    }
                    else{
                        ou.write(str.getBytes());
                        ou.write('\n');
                    }
                }
                temp.renameTo(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

