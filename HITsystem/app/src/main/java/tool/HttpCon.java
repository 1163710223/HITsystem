package tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.zhang.hitsystem.R;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpCon {
    public static String open(Context context, String url, final String... param) {
        SharedPreferences myPreference = context.getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPreference.edit();

        String email = myPreference.getString("email", "0");
        String password = myPreference.getString("password", "0");

        String responseData = null;
        try{
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder builder = new FormBody.Builder();
            for(int i=0;i<param.length;i=i+2){
                builder.add(param[i], param[i+1]);
            }
            RequestBody requestBody = builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            responseData = response.body().string();

        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("HttpConOpenContent", "open: "+responseData);
        return responseData;
    }
    public static String openget(Context context, String url, final String... param) {
        SharedPreferences myPreference = context.getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPreference.edit();

        String email = myPreference.getString("email", "0");
        String password = myPreference.getString("password", "0");

        String responseData = null;
        try{
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder builder = new FormBody.Builder();
            for(int i=0;i<param.length;i=i+2){
                builder.add(param[i], param[i+1]);
            }
            RequestBody requestBody = builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            responseData = response.body().string();

        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("HttpConOpenContent", "open: "+responseData);
        return responseData;
    }
}
