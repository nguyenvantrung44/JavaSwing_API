package retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitClientCreator {
    public static final String BASE_URL = "http://localhost:8888/";

    public static Retrofit getClient() {
        return new Retrofit.Builder().baseUrl(BASE_URL) // This is the onlt mandatory call on Builder object
                .addConverterFactory(JacksonConverterFactory.create()) // Convertor library used to convert response
                // into POJO
                // .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
