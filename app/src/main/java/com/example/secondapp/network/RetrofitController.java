package com.example.secondapp.network;

import android.util.Log;

import com.example.secondapp.model.TodoItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitController implements Callback<List<TodoItem>> {
    private static final String TAG = "RetrofitController";
    private static final String BASE_URL = "http://ed4.localhost.run/";

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

        Call<ResponseBody> call2 = todoAPI.loginUser("ed", "bob123");
        //call2.enqueue(this);
    }

    /*
    @Override
    public void onResponse(Call<List<TodoItem>> call, Response<List<TodoItem>> response) {
        if (response.isSuccessful()) {
            List<TodoItem> todoList = response.body();
            todoList.forEach(todo -> Log.v(TAG, todo.getTitle()));
        } else {
            Log.e(TAG, response.errorBody().toString());
        }
    }
    */

    public void onResponse(Call<List<TodoItem>> call, Response<List<TodoItem>> response) {}

    @Override
    public void onFailure(Call<List<TodoItem>> call, Throwable t) {
        Log.e(TAG, "Oh shit");
    }
}
