package in.rajasudhan.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import in.rajasudhan.popularmovies.models.Movie;

/**
 * Created by raja sudhan on 3/25/2016.
 */
public class MovieDetailActivity extends AppCompatActivity {

    ImageView moviePoster;
    TextView movieTitle;
    TextView movieOverview;
    TextView movieRating;
    TextView movieReleaseDate;
    Movie movie;
    final String POSTER_BASE_URL="http://image.tmdb.org/t/p/w185";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent i=getIntent();
        movie=i.getParcelableExtra("movie");

        moviePoster=(ImageView)findViewById(R.id.movie_poster);
        movieTitle=(TextView)findViewById(R.id.movie_title);
        movieOverview=(TextView)findViewById(R.id.movie_overview);
        movieRating=(TextView)findViewById(R.id.movie_rating);
        movieReleaseDate=(TextView)findViewById(R.id.movie_release_date);
        setData();
    }


    private void setData()
    {
        Picasso.with(this).load(POSTER_BASE_URL+movie.getPosterPath()).into(moviePoster);
        movieTitle.setText(movie.getOriginalTitle());
        movieOverview.setText("Plot:\n"+movie.getOverview());
        movieRating.setText(movie.getVoteAverage()+"/10");
        movieReleaseDate.setText("Released On: "+movie.getReleaseDate());

    }




}
