package service;

import dto.Post.PostLogin;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("api/auth/singin")
    Call<Integer> login(@Body PostLogin postLogin);
}
