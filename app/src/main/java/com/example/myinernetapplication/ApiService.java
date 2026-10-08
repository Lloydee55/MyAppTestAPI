package com.example.myinernetapplication;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface ApiService {
    @GET("image/random")
    Single<DogImage> loadDogImage();
}
