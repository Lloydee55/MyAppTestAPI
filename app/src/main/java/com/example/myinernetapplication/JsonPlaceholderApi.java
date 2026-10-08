package com.example.myinernetapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceholderApi {
    @GET("posts/{id}")
    Call<Post> getPostById(@Path("id") int postId);
}
