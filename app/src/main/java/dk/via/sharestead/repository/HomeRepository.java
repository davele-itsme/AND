package dk.via.sharestead.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import dk.via.sharestead.model.Game;
import dk.via.sharestead.webservices.GamesAPI;
import dk.via.sharestead.webservices.GamesResponse;
import dk.via.sharestead.webservices.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepository {
    private MutableLiveData<Game> games;
    private static HomeRepository instance;
    private Application application;
    
    public HomeRepository(Application application) {
        games = new MutableLiveData<>();
        this.application = application;
        requestGames();


    }

    public static HomeRepository getInstance(Application application) {
        if (instance == null)
        {
            instance = new HomeRepository(application);
        }
        return instance;
    }

    public LiveData<Game> getGames() {
        return games;
    }

    private void requestGames(){
        GamesAPI gamesAPI = ServiceGenerator.getGamesAPI();
        Call<GamesResponse> call = gamesAPI.getGames();
        call.enqueue(new Callback<GamesResponse>() {
           @Override
           public void onResponse(Call<GamesResponse> call, Response<GamesResponse> response) {
               if(response.code() == 200)
               {
                   games.setValue(response.body().getGame());
//                   Toast.makeText(application, "Good: " + response.code(), Toast.LENGTH_SHORT).show();
                   Toast.makeText(application, "Good: " + games.getValue().getCount(), Toast.LENGTH_SHORT).show();
               }
               else {
                   Toast.makeText(application, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<GamesResponse> call, Throwable t) {
               Toast.makeText(application, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });

    }
}

   
