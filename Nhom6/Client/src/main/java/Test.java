import dto.Page.TieuDePage;

import dto.TieuDeDto;
import retrofit.RetrofitClientCreator;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import service.TieuDeService;

import java.io.IOException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
       Retrofit retrofit = RetrofitClientCreator.getClient();
      TieuDeService  tieuDeService = retrofit.create(TieuDeService.class);
        Call<TieuDePage> call = tieuDeService.getTieuDe();
        Response<List<Object>> response;
        List<dto.TieuDeDto> body = call.execute().body().getTieuDeDtos();
       // TieuDeDto tieuDeDto = new TieuDeDto();
//        for (Object o : body){
//            if (o.equals(new  TieuDeDto())){
//                System.out.println(o);
//            }
//        }
//        System.out.println(body);
        body.forEach(dto->{
            System.out.println(dto.getMaTieuDe());
            System.out.println(dto.getTenTieuDe());
        });
    }
}
