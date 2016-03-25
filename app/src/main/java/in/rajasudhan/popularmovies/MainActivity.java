package in.rajasudhan.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.rajasudhan.popularmovies.models.Movie;
import in.rajasudhan.popularmovies.models.MoviesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    GridView moviesGrid;
    int mPage=1;
    ArrayList<Movie> moviesList;
    MoviesArrayAdapter movieAdapter;
    Boolean loading=false;
    TextView loadingIndicator;
    String sortOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moviesGrid=(GridView)findViewById(R.id.gridView);
        loadingIndicator=(TextView)findViewById(R.id.textView);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        sortOrder = prefs.getString("sort_by",getString(R.string.pref_def_sort_by));
        if(savedInstanceState==null)
        {
            moviesList=new ArrayList<Movie>();
            movieAdapter=new MoviesArrayAdapter(this,moviesList);
            moviesGrid.setAdapter(movieAdapter);
            loadMovies(sortOrder);
        }else
        {
            moviesList=savedInstanceState.getParcelableArrayList("moviesList");
            mPage=savedInstanceState.getInt("mPage");
            movieAdapter=new MoviesArrayAdapter(this,moviesList);
            moviesGrid.setAdapter(movieAdapter);
        }

        setListeners();
    }


    private void setListeners()
    {
        moviesGrid.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int width=moviesGrid.getWidth();
                        int cols=(int)Math.round((double)width/(double)getResources().getDimensionPixelSize(R.dimen.poster_width));
                        moviesGrid.setNumColumns(cols);
                    }
                }
        );

        moviesGrid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastInScreen=firstVisibleItem+visibleItemCount;
                if(lastInScreen==totalItemCount&&!loading)
                {
                    loadMovies(sortOrder);

                }

            }
        });


        moviesGrid.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent i= new Intent(MainActivity.this,MovieDetailActivity.class);
                        i.putExtra("movie",movieAdapter.getItem(position));
                        startActivity(i);
                    }
                }
        );
    }


    private void loadMovies(String sort_by)
    {
        loading=true;
        loadingIndicator.setVisibility(View.VISIBLE);
        MovieDBService.MovieDBClient client=MovieDBService.createService(MovieDBService.MovieDBClient.class);

        client.getMovies(mPage+"",sort_by).enqueue(
                new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

                        if(response.isSuccessful())
                        {
                            List<Movie> movies= response.body().getResults();

                            for (Movie movie:movies)
                            {
                                movieAdapter.add(movie);
                            }

                            mPage++;
                            loadingIndicator.setVisibility(View.INVISIBLE);
                            loading=false;

                        }

                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {

                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_settings:
                startActivity(new Intent(this,SettingsActivity.class));
                return true;

        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("moviesList",moviesList);
        outState.putInt("mPage",mPage);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!sortOrder.equals( prefs.getString("sort_by",getString(R.string.pref_def_sort_by))))
        {
            mPage=1;
            sortOrder=prefs.getString("sort_by",getString(R.string.pref_def_sort_by));
            movieAdapter.clear();
            loadMovies(sortOrder);
        }

    }
}
