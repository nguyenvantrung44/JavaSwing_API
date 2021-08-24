package service;
import dto.Page.TieuDePage;
import dto.Post.PostTieuDe;
import retrofit2.Call;
import retrofit2.http.*;

public interface TieuDeService {
    @Headers("Accept: application/json; charset=utf-8")

    @GET("api/v1/nhom6/tieude")
    Call<TieuDePage> getTieuDe();

    @GET("api/v1/nhom6/tieude/name/{name}")
    Call<TieuDePage> getMaTieuDeByTen(@Path("name") String name);

    @POST("api/v1/nhom6/tieude")
    Call<Void> addTieuDe(@Body PostTieuDe tieuDe);

    @DELETE("api/v1/nhom6/tieude/{id}")
    Call<Void> deleteTieuDe(@Path("id") int id);
}
