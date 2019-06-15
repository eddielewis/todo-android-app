package com.example.secondapp.network;

import com.example.secondapp.model.TodoItem;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TodoAPI {
    @GET("api/v1")
    Call<List<TodoItem>> loadTodos();

    @POST("api/v1/rest-auth/login")
    Call<ResponseBody> loginUser(
            @Field("username") String username,
            @Field("password") String password
    );
}
