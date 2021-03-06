package dk.via.sharestead.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dk.via.sharestead.R;
import dk.via.sharestead.viewmodel.GameDetailsViewModel;

public class GameDetailsActivity extends AppCompatActivity {
    private GameDetailsViewModel viewModel;
    private ImageView gameImage;
    private TextView gameName, developerName, metacritic, released;
    private WebView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_details_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        int id = 0;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(HomeFragment.EXTRA_GAME)) {
            id = bundle.getInt(HomeFragment.EXTRA_GAME);
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        setLayout();
        viewModel = new ViewModelProvider(this).get(GameDetailsViewModel.class);
        setGameDetails(id);
    }

    private void setLayout() {
        gameImage = findViewById(R.id.gameImage);
        gameName = findViewById(R.id.gameName);
        developerName = findViewById(R.id.developerName);
        metacritic = findViewById(R.id.metacritic);
        released = findViewById(R.id.released);
        description = findViewById(R.id.description);
    }

    private void setGameDetails(int id) {
        viewModel.setGameDetails(id);
        viewModel.getGameDetails().observe(this, gameDetails -> {
            Picasso.with(getBaseContext()).load(gameDetails.getBackgroundImage()).resize(0, 1000).into(gameImage);
            gameName.setText(gameDetails.getName());
            developerName.setText(gameDetails.getDevelopers().get(0).getName());
            metacritic.setText(String.valueOf(gameDetails.getMetacritic()));
            released.setText(gameDetails.getReleased());

            String text;
            text = "<html><body><p align=\"justify\">";
            text+= gameDetails.getDescription();
            text+= "</p></body></html>";
            description.loadData(text, "text/html", "utf-8");
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }
}
