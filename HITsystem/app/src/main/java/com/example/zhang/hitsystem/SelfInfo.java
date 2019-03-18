package com.example.zhang.hitsystem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import bean.Student;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SelfInfo extends AppCompatActivity {

    private TextView idcontent;
    private TextView phonenumcontent;
    private TextView cclasscontent;
    private TextView namecontent;

    private String id;
    private String phonenum;
    private String cclass;
    private String name;
    private SharedPreferences myPreference;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_info);

        myPreference = getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        email = myPreference.getString("email", "0");

        initview();

        //modelmatchview(requstmsg(email));
        requstmsg(email);
    }

    private void initview(){
        idcontent = (TextView)findViewById(R.id.idcontent);
        phonenumcontent = (TextView)findViewById(R.id.phonenumcontent);
        cclasscontent = (TextView)findViewById(R.id.cclasscontent);
        namecontent = (TextView)findViewById(R.id.namecontent);
    }

    private void modelmatchview(Student student){
        idcontent.setText(student.getId());
        phonenumcontent.setText(student.getPhonenum());
        cclasscontent.setText(student.getYear());
        namecontent.setText(student.getName());

    }

    private Student requstmsg(final String id){
        final Student student = new Student();
        final String thisurl;
        if(email.length()==10){
            thisurl = "http://"+getString(R.string.ipdress)+"/reqstumsg?id="+id;
        }else{
            thisurl = "http://"+getString(R.string.ipdress)+"/reqteamsg?id="+id;
        }
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Log.d("SelfInfo","开启线程发送请求");
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(thisurl).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("SelfInfo", "接受的服务端数据"+responseData);
                    JSONObject jsonObject = new JSONObject(responseData);
                    student.setId(jsonObject.getString("id"));
                    student.setName(jsonObject.getString("name"));
                    student.setPhonenum(jsonObject.getString("phonenum"));
                    if(id.length()==8){
                        String year = id.substring(0, 4);
                        student.setYear(year);
                    }else{
                        String year = id.substring(1, 3);
                        student.setYear("20"+year);
                    }
                    Log.d("SelfInfo", "已接受服务端数据"+jsonObject);

                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        modelmatchview(student);
                    }
                });

            }
        }).start();
        return student;
    }
}
