package com.example.brenda.myhome.helpers;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by brenda on 3/15/17.
 */
public class OauthHelper {
    public Context context;

    public OauthHelper(Context context) {
        this.context = context;
    }
    OkHttpClient client = new OkHttpClient();

    public void AuthorizationCode(FutureCallback<JsonObject> callback) {
        OkHttpClient client = new OkHttpClient();

    Request request = new Request.Builder()
            .url("https://api.wit.ai/speech?v=20160526")
            .post(null)
            .addHeader("content-type", "audio/wav")
            .addHeader("authorization", "OODMVYPEJE3HAJQXXOOIK4WWEMQHVRCV")
            .build();

        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

