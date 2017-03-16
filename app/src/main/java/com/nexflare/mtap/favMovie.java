package com.nexflare.mtap;

/**
 * Created by 15103068 on 28-01-2017.
 */

public class favMovie {
    String Title,Poster;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public favMovie(String title, String poster) {
        Title = title;
        Poster = poster;
    }
}
