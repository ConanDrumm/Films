package com.example.android.films;

//import android.support.v4.app.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class FilmsFragment extends Fragment {
    // API Key: 967c9109162248035267dc766abca7fd

    private FilmAdapter mFilmAdapter;
    private ArrayList<Film> filmList;
    private String mSortType = "popular";

    final String FILM_BASE_URL =
            "http://api.themoviedb.org/3/movie/";

    public FilmsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        /*
        if(savedInstanceState == null || !savedInstanceState.containsKey("films")) {
            updateFilms();
            System.out.println("NO SAVED INSTANCE!");
        }
        else {
            filmList = savedInstanceState.getParcelableArrayList("films");
            System.out.println("INSTANCE WAS SAVED!");
        }
        */
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.filmsfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_sort_popular) {
            //updateFilms(getString(R.string.pref_sort_popular));
            mSortType = getString(R.string.pref_sort_popular);
            updateFilms();
            return true;
        }else if (id == R.id.action_sort_top_rated){
            mSortType = getString(R.string.pref_sort_top_rated);
            updateFilms( /*getString(R.string.pref_sort_top_rated)*/ );
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /*
         http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=967c9109162248035267dc766abca7fd
         http://api.themoviedb.org/3/movie/popular?api_key=967c9109162248035267dc766abca7fd
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mFilmAdapter = new FilmAdapter(getActivity(), new ArrayList<Film>());

        GridView gridview = (GridView) rootView.findViewById(R.id.gridview_films);
        gridview.setAdapter(mFilmAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Film film = mFilmAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), FilmDetailActivity.class)
                        .putExtra("THE_FILM", film);
                startActivity(intent);
            }
        });

        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        //updateFilms(getString(R.string.pref_sort_popular));
        updateFilms();
    }
/*
    @Override
    public void onSaveInstanceState(Bundle outState) {
        System.out.println("FILM ZERO" + filmList.get(0).toString());
        outState.putParcelableArrayList("films", filmList);
        super.onSaveInstanceState(outState);
    }
*/
    private void updateFilms(/*String vSortType*/) {
        /*String sortType;
        if( vSortType == null){

            sortType = getString(R.string.pref_sort_popular);
        }
        else{
            sortType = vSortType;
        }
        */
        FetchFilmsTask filmsTask = new FetchFilmsTask();
        filmsTask.execute(mSortType);
    }
///*
    public class FetchFilmsTask extends AsyncTask<String, Void, List<Film> >{

        private final String LOG_TAG = FetchFilmsTask.class.getSimpleName();
        private static final String DEBUG_TAG = "TMDBQueryManager";

//          Take the String representing the complete forecast in JSON Format and
//          pull out the data we need to construct the Strings needed for the wireframes.
//
//          Fortunately parsing is easy:  constructor takes the JSON string and converts it
//          into an Object hierarchy for us.

        private List<Film> getFilmDataFromJson(String filmJsonString)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String MDB_RESULTS = "results";
            final String MDB_TITLE = "title";
            final String MDB_RELEASE = "release_date";
            final String MDB_RATING = "vote_average";
            final String MDB_POSTER = "poster_path";
            final String MDB_OVERVIEW = "overview";

            JSONObject filmJson = new JSONObject(filmJsonString);
            JSONArray filmArray = filmJson.getJSONArray(MDB_RESULTS);


            filmList = new ArrayList<>();
            //ArrayList<String> posterPaths = new ArrayList<String>();

//            SharedPreferences sharedPrefs =
//                    PreferenceManager.getDefaultSharedPreferences(getActivity());

            for(int i = 0; i < filmArray.length(); i++) {

                Film film;

                // Get the JSON object representing the day
                JSONObject filmObj = filmArray.getJSONObject(i);

                film = new Film( filmObj.getString(MDB_TITLE),
                                filmObj.getString(MDB_RELEASE),
                                filmObj.getString(MDB_RATING),
                                filmObj.getString(MDB_OVERVIEW),
                                filmObj.getString(MDB_POSTER)
                        );

                filmList.add(i, film);

            }

            return filmList;
        }



        @Override
        protected List<Film> doInBackground(String... params) {

            if (params.length == 0l){
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String sortType = params[0];

            // Will contain the raw JSON response as a string.
            String filmJsonStr = null;

            // TODO: place this in settings.

            try {
                // http://api.themoviedb.org/3/movie/popular

                final String KEY_PARAM = "api_key";
                Uri builtUri = Uri.parse(FILM_BASE_URL+ sortType).buildUpon()
                        .appendQueryParameter(KEY_PARAM, getString(R.string.apiKey))
                        .build();

                URL url = new URL(builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setRequestMethod("GET");
                urlConnection.addRequestProperty("Accept", getString(R.string.connection_get_format) );
                urlConnection.setDoInput(true);
                urlConnection.connect();

                int responseCode = urlConnection.getResponseCode();
                Log.d(DEBUG_TAG, "The response code is: " + responseCode + " " + urlConnection.getResponseMessage());

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                filmJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);

                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {


                return getFilmDataFromJson(filmJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }

        @Override
        protected void onPostExecute(List<Film> vFilms) {
            if (vFilms != null) {
                mFilmAdapter.clear();
                for(Film film : vFilms) {
                    //String path;
                    //path = getPosterPath(film);
                    //System.out.println("POST_UPDATE:" + film);
                    mFilmAdapter.add(film);
                }
                // New data is back from the server.  Hooray!
            }
        }
    }

    //*/



}
