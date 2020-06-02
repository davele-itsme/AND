package dk.via.sharestead.webservices;

import java.util.Map;

import dk.via.sharestead.model.GameDetails;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface GamesApi {
    //asynchronous calls
    @GET("games")
    Call<GamesResponse> getGamesByPreference(@QueryMap Map<String, Object> map);

    @GET("games?tags=vr&ordering=-rating")
    Call<GamesResponse> getVRGames();

    @GET("platforms/lists/parents")
    Call<PlatformResponse> getPlatformId();

    @GET("games/{id}")
    Call<GameDetails> getGameDetails(@Path("id") int id);
}
