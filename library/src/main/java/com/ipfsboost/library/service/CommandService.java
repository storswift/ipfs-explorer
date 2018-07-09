package com.ipfsboost.library.service;

import com.ipfsboost.library.entity.Stats_bw;
import com.ipfsboost.library.entity.Swarm;
import com.ipfsboost.library.entity.Add;
import com.ipfsboost.library.entity.Commands;
import com.ipfsboost.library.entity.Config;
import com.ipfsboost.library.entity.Dag;
import com.ipfsboost.library.entity.Id;
import com.ipfsboost.library.entity.Version;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CommandService {
    @POST("add")
    @Multipart
    Call<Add> add(@Part MultipartBody.Part part);

    @GET("version")
    Call<Version> version();

    @GET("id")
    Call<Id> id();

    @GET("commands")
    Call<Commands> commands();

    interface pin {
        @GET("Pin/ls?type={type}")
        Call<String> ls(@Path("type") String type);
    }

    interface stats {
        @GET("stats/bw")
        Call<Stats_bw> bw();
    }

    interface swarm {
        @GET("stats/bw")
        Call<Swarm.connect> connect(@Query("arg") String arg);
    }

    interface config {
        @GET("config/show")
        Call<Config.show> show();
    }

    interface dag {
        @GET("dag/get")
        Call<Dag> get(@Query("arg") String arg);
    }
}
