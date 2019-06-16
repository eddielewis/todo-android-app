package com.example.secondapp.network;

import android.util.Log;

import com.example.secondapp.model.TodoItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitController implements Callback<List<TodoItem>> {
    private static final String TAG = "RetrofitController";
    private static final String BASE_URL = "http://ed8.localhost.run/";

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TodoAPI todoAPI = retrofit.create(TodoAPI.class);

        Call<List<TodoItem>> call = todoAPI.loadTodos();
        call.enqueue(this);

        Call<LoginResponse> call2 = todoAPI.loginUser("ed", "bob123");
        call2.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                Log.e(TAG, loginResponse.getKey());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void onResponse(Call<List<TodoItem>> call, Response<List<TodoItem>> response) {
        if (response.isSuccessful()) {
            List<TodoItem> todoList = response.body();
            todoList.forEach(todo -> Log.v(TAG, todo.getTitle()));
        } else {
            try {
                Log.e(TAG, response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<List<TodoItem>> call, Throwable t) {
        Log.e(TAG, "Oh shit");
        Log.e(TAG, t.toString());
    }
}
