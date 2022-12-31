package api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BaseUrlClient {

    private static final String BASE_URL = "http://192.168.43.5:8080/";

    private static BaseUrlClient mInstance;
    private Retrofit retrofit;

    private BaseUrlClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized BaseUrlClient getInstance(){
        if (mInstance ==null){
            mInstance = new BaseUrlClient();
        }
        return mInstance;
    }
    public ApiClient getApi(){
        return retrofit.create(ApiClient.class);
    }
}
