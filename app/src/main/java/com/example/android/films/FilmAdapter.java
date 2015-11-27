package com.example.android.films;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by stan on 11/22/15.
 */
public class FilmAdapter extends ArrayAdapter<Film> {
    //  http://image.tmdb.org/t/p/w342/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
    private Context mContext;
    private LayoutInflater mInflater;
    private final String IMAGE_BASE_URL= "http://image.tmdb.org/t/p/";

    public FilmAdapter(Context context, List<Film> vFilms){
        super( context, 0, vFilms );

        mContext = context;
        mInflater = LayoutInflater.from(context);

    }
/*
    private static String getPosterPath(String vFilm){

        String posterPath;

        String rawString = vFilm;
        String dilems = "[~]+";
        String[] tokens = rawString.split(dilems);
        posterPath = tokens[tokens.length - 1];

        return posterPath;
    }
*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Film film = getItem(position);
        String posterPath;
        View view;
        SquareImageView posterView;
        String size = mContext.getString(R.string.film_poster_size);

        if (convertView == null) {
            view = mInflater.inflate(R.layout.grid_item_film, parent, false);
        }
        else{
            view = convertView;
        }

        posterPath = film.poster;
        posterView = (SquareImageView) view.findViewById(R.id.grid_item_film_imageView);


        String picassoUrl = "" + IMAGE_BASE_URL + size + posterPath;

        // reset the imageView
        Picasso.with(mContext).cancelRequest( posterView );

        // start a new load for the imageView
        Picasso.with(mContext).load(picassoUrl).into( posterView );

        return view;
    }

    // references to our images

}
