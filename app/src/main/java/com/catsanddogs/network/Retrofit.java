package com.catsanddogs.network;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Retrofit {
    @GET("xim/api.php")
    Call<Query> getData(@retrofit2.http.Query("query") String categoryName);
}
