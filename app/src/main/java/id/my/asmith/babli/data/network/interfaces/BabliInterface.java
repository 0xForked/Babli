package id.my.asmith.babli.data.network.interfaces;

import id.my.asmith.babli.data.model.ServerRequest;
import id.my.asmith.babli.data.model.ServerResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by A. A. Sumitro on 10/15/2017.
 * aasumitro@gmail.com
 * https://asmith.my.id/
 */

public interface BabliInterface {
    @POST("index.php/")
    Call<ServerResponse> operation(@Body ServerRequest request);
}
