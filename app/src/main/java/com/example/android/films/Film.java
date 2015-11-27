package com.example.android.films;

import android.os.Parcel;
import android.os.Parcelable;

public class Film implements Parcelable{

    private final String DILEM = "~~";

    String title;
    String release;
    String poster;
    String rating;
    String overview;

    public Film(String vTitle, String vRelease, String vRating, String vOverview, String vPoster)
    {
        this.title = vTitle;
        this.release = vRelease;
        this.rating = vRating;
        this.overview = vOverview;
        this.poster = vPoster;
    }

    private Film(Parcel in){
        title = in.readString();
        release = in.readString();
        rating = in.readString();
        overview = in.readString();
        poster = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() { return title + DILEM + release + DILEM + rating + DILEM + overview + DILEM + poster; }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(release);
        parcel.writeString(rating);
        parcel.writeString(overview);
        parcel.writeString(poster);
    }

    public static final Parcelable.Creator<Film> CREATOR = new Parcelable.Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel parcel) {
            return new Film(parcel);
        }

        @Override
        public Film[] newArray(int i) {
            return new Film[i];
        }

    };
}
