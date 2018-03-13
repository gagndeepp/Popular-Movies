package gagan.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private ArrayList<Movie> movieArrayList;
    Context main_context;
    MovieAdapter(Context context) {
        main_context = context;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View returnHolderView;
        returnHolderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_layout, parent, false);
        return new MovieHolder(returnHolderView);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        Movie thisMovie = movieArrayList.get(position);
        if (thisMovie.getThumbURL() == null)
            Picasso.with(holder.itemView.getContext()).load(R.drawable.no_poster).into(holder.thumb_view);
        else
            Picasso.with(holder.itemView.getContext()).load(thisMovie.getThumbURL()).into(holder.thumb_view);
    }

    @Override
    public int getItemCount() {
        if (movieArrayList != null)
            return movieArrayList.size();
        else
            return 0;
    }

    class MovieHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {
        ImageView thumb_view;

        MovieHolder(View itemView) {
            super(itemView);
            thumb_view = (ImageView) itemView.findViewById(R.id.movie_thumb_list_imageview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Movie current_movie = movieArrayList.get(getAdapterPosition());
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra(main_context.getString(R.string.intent_extra_movie), current_movie);
            v.getContext().startActivity(intent);
        }
    }

    void setMovieData(ArrayList<Movie> list) {
        if (movieArrayList != null)
            movieArrayList.clear();
        movieArrayList = list;
        notifyDataSetChanged();
    }
}
