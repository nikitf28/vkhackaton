package ru.dolbak.vkkavkaton;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
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

    public static UserInfo profileMeGet(String token) throws Exception {
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
                UserInfo userInfo = new UserInfo("ERROR", "PROFILE_NOT_READY");
                return userInfo;
            }
            if (jObject.getString("Code").equals("INVALID_TOKEN")){
                UserInfo userInfo = new UserInfo("ERROR", "INVALID_TOKEN");
                return userInfo;
            }
            UserInfo userInfo = new UserInfo("ERROR", "error");
            return userInfo;
        }
        ResponseBody responseBody  = response.body();
        // вроде бы такого быть не должно
        if(responseBody  == null) {
            return null;
        }
        JSONObject jObject = new JSONObject(responseBody.string());
        JSONArray base64Images = jObject.getJSONArray("Pictures");
        Bitmap[] bitmaps = new Bitmap[base64Images.length()];
        for (int i = 0; i < base64Images.length(); i++){
            String base64 = base64Images.getString(i);
            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            bitmaps[i] = bitmap;
        }
        JSONArray jsonTags = jObject.getJSONArray("Tags");
        String[] tags = new String[jsonTags.length()];
        for (int i = 0; i < jsonTags.length(); i++){
            tags[i] = jsonTags.getString(i);
        }

        UserInfo userInfo = new UserInfo("OK", jObject.getString("FirstName"), jObject.getString("LastName"),
                jObject.getString("Age"), jObject.getString("Bio"), bitmaps, tags);
        return userInfo;
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
