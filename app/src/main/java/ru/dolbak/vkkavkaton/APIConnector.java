package ru.dolbak.vkkavkaton;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.content.ContentValues.TAG;

public class APIConnector {
    private static OkHttpClient client = new OkHttpClient();
    private static Gson gson = new Gson();
    public static String domain = "http://alpha.rfnull.com:8080";

    public static String authorize(String email, String pwd) throws Exception {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"email\": \"" + email + "\", \"password\": \"" + pwd + "\"}");
        Request request = new Request.Builder()
                .url(domain + "/authorize")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("accept", "application/json")
                .build();
        Log.d("Internet", request.toString());
        Response response = client.newCall(request).execute();
        if(response.code() != 200) {
            Log.d("Internet", response.toString());
            throw new Exception("server request failed");
        }
        ResponseBody responseBody  = response.body();
        // вроде бы такого быть не должно
        if(responseBody  == null) {
            return null;
        }
        JSONObject jObject = new JSONObject(responseBody.string());
        return jObject.getString("token");
    }

}
