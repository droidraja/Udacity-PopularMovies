package in.rajasudhan.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.rajasudhan.popularmovies.models.Movie;

/**
 * Created by raja sudhan on 3/25/2016.
 */
public class MoviesArrayAdapter extends ArrayAdapter<Movie> {

private Context mContext;

    final String POSTER_BASE_URL="http://image.tmdb.org/t/p/w185";
    public MoviesArrayAdapter(Context context, List<Movie> MoviesList)
    {
        super(context,R.layout.lv_movie_single,MoviesList);
        mContext=context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie=getItem(position);

        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.lv_movie_single,parent,false);
        }

        ImageView movieImage=(ImageView) convertView.findViewById(R.id.movie_poster);
        TextView movieTitle=(TextView) convertView.findViewById(R.id.movie_title);

        Picasso.with(mContext).load(POSTER_BASE_URL+movie.getPosterPath()).into(movieImage);
        movieTitle.setText(movie.getOriginalTitle());
        return convertView;
    }


}
