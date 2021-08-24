package service;

import dto.Page.ChiTietTreHenPage;
import dto.Post.PostChiTietTreHen;
import dto.Post.PostPhiTreHen;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChiTietPhiTreHen {
    @POST("api/v1/nhom6/chitietphitrehen")
    Call<Long> addPhiTreHen(@Body PostChiTietTreHen postChiTietTreHen);

    @GET("api/v1/nhom6/chitietphitrehen/{id}")
    Call<ChiTietTreHenPage> getChiTietPhiTreHenById(@Path("id") long id);
}
