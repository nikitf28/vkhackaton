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

    public static String[] authorize(String email, String pwd) throws Exception {
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
            ResponseBody responseBody  = response.body();
            JSONObject jObject = new JSONObject(responseBody.string());
            if (jObject.getString("Code").equals("INVALID_PASSWORD")){
                return new String[] {"ERROR", "INVALID_PASSWORD"};
            }
            return new String[] {"ERROR", "unknown error"};
        }
        ResponseBody responseBody  = response.body();
        // вроде бы такого быть не должно
        if(responseBody  == null) {
            return null;
        }
        JSONObject jObject = new JSONObject(responseBody.string());
        return new String[] {"token", jObject.getString("token")};
    }


    public static String[] register(String email, String pwd) throws Exception {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"email\": \"" + email + "\", \"password\": \"" + pwd + "\"}");
        Request request = new Request.Builder()
                .url(domain + "/register")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("accept", "application/json")
                .build();
        Log.d("Internet", request.toString());
        Response response = client.newCall(request).execute();
        if(response.code() != 200) {
            ResponseBody responseBody  = response.body();
            JSONObject jObject = new JSONObject(responseBody.string());
            if (jObject.getString("Code").equals("INVALID_EMAIL")){
                return new String[] {"ERROR", "INVALID_EMAIL"};
            }
            if (jObject.getString("Code").equals("EMAIL_TAKEN")){
                return new String[] {"ERROR", "EMAIL_TAKEN"};
            }
            return new String[] {"ERROR", "unknown error"};
        }
        ResponseBody responseBody  = response.body();
        // вроде бы такого быть не должно
        if(responseBody  == null) {
            return null;
        }
        JSONObject jObject = new JSONObject(responseBody.string());
        return new String[] {"token", jObject.getString("token")};
    }

    public static String[] profileMeGet(String token) throws Exception {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url(domain + "/profile/me")
                .get()
                .addHeader("content-type", "application/json")
                .addHeader("accept", "application/json")
                .addHeader("X-Auth-Token", token)
                .build();
        Log.d("Internet", request.toString());
        Response response = client.newCall(request).execute();
        if(response.code() != 200) {
            ResponseBody responseBody  = response.body();
            JSONObject jObject = new JSONObject(responseBody.string());
            if (jObject.getString("Code").equals("PROFILE_NOT_READY")){
                return new String[] {"ERROR", "PROFILE_NOT_READY"};
            }
            if (jObject.getString("Code").equals("INVALID_TOKEN")){
                return new String[] {"ERROR", "INVALID_TOKEN"};
            }
            return new String[] {"ERROR", "unknown error"};
        }
        ResponseBody responseBody  = response.body();
        // вроде бы такого быть не должно
        if(responseBody  == null) {
            return null;
        }
        JSONObject jObject = new JSONObject(responseBody.string());
        return new String[] {"data", jObject.getString("FirstName"), jObject.getString("LastName"), jObject.getString("Age"),
                jObject.getString("Bio"), jObject.getString("Pictures"), jObject.getString("Tags")};
    }

    public static String[] profileMePost(String token, String FirstName, String LastName, String BirthDate) throws Exception {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url(domain + "/profile/me")
                .get()
                .addHeader("content-type", "application/json")
                .addHeader("accept", "application/json")
                .addHeader("X-Auth-Token", token)
                .build();
        Log.d("Internet", request.toString());
        Response response = client.newCall(request).execute();
        if(response.code() != 200) {
            ResponseBody responseBody  = response.body();
            JSONObject jObject = new JSONObject(responseBody.string());
            if (jObject.getString("Code").equals("INVALID_BIRTHDATE")){
                return new String[] {"ERROR", "INVALID_BIRTHDATE"};
            }
            return new String[] {"ERROR", "unknown error"};
        }
        ResponseBody responseBody  = response.body();
        // вроде бы такого быть не должно
        if(responseBody  == null) {
            return null;
        }
        JSONObject jObject = new JSONObject(responseBody.string());
        return new String[] {"OK", ""};
    }




}
