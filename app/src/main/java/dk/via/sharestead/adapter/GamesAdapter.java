package dk.via.sharestead.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dk.via.sharestead.R;
import dk.via.sharestead.model.Game;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GameHolder> {
    private OnListItemClickListener listener;
    private List<Game> games;
    private Context context;
    private String type;


    public GamesAdapter(String type, Context context, OnListItemClickListener listener) {
        this.type = type;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, parent, false);
        return new GameHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder holder, int position) {
        position++;
        if (games != null) {
            String image = games.get(position).getBackgroundImage();
            holder.textView.setText(games.get(position).getName());
            if (type.equals("horizontal")) {
                Picasso.with(context).load(image).resize(0, 400).into(holder.imageView);
            } else {
                Picasso.with(context).load(image).resize(0, 800).into(holder.imageView);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (games != null) {
            return 14;
        }
        return 0;
    }

    public void setGames(List<Game> games) {
        this.games = games;
        notifyDataSetChanged();
    }

    public class GameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private ImageView imageView;
        private OnListItemClickListener listener;

        public GameHolder(@NonNull View itemView, OnListItemClickListener listener) {
            super(itemView);
            textView = itemView.findViewById(R.id.gameName);
            imageView = itemView.findViewById(R.id.gameImage);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = games.get(getAdapterPosition() + 1).getId();
            listener.onListItemClick(id);
        }
    }

    public interface OnListItemClickListener {
        void onListItemClick(int gameId);
    }
}
