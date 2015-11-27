package com.example.android.films;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class FilmDetailFragment extends Fragment {

    private static final String LOG_TAG = FilmDetailFragment.class.getSimpleName();
    private final String IMAGE_BASE_URL= "http://image.tmdb.org/t/p/";
    private Film mFilm;

    public FilmDetailFragment() {
        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_film_detail, container, false);

        // The detail Activity called via intent.  Inspect the intent for forecast data.
        Intent intent = getActivity().getIntent();
        Film film;
        if (intent != null && intent.hasExtra("THE_FILM")) {
            mFilm = (Film) intent.getParcelableExtra("THE_FILM");

            String picassoUrl = IMAGE_BASE_URL + getString(R.string.film_poster_size) + mFilm.poster;

            //populate the layout
            ((TextView) rootView.findViewById(R.id.film_detail_title)).setText(mFilm.title);

            Picasso.with(getActivity()).load(picassoUrl).into(
                                    (ImageView) rootView.findViewById(R.id.film_detail_poster) );
            ((TextView) rootView.findViewById(R.id.film_detail_release)).setText(mFilm.release);
            ((TextView) rootView.findViewById(R.id.film_detail_rating)).setText(mFilm.rating + "/10");
            ((TextView) rootView.findViewById(R.id.film_detail_overview)).setText(mFilm.overview);
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //inflater.inflate(R.menu.detailfragment, menu);

        // Retrieve the share menu item
        //MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.

        // Attach an intent to this ShareActionProvider.  You can update this at any time,
        // like when the user selects a new piece of data they might like to share.

    }


}
