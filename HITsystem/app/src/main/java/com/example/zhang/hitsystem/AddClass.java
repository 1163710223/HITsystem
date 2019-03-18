package com.example.zhang.hitsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bean.Score;
import bean.Subject;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddClass extends AppCompatActivity {

    EditText addnameEdit;
    EditText addidEdit;
    EditText addteaidEdit;
    String addname;
    String addid;
    String addteaid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        initview();
    }
    public void initview(){
        addidEdit=(EditText)findViewById(R.id.addid);
        addnameEdit=(EditText)findViewById(R.id.addname);
        addteaidEdit=(EditText)findViewById(R.id.addteaid);
    };

    public void addcommit(View view){
        addname = addnameEdit.getText().toString();
        addid = addidEdit.getText().toString();
        addteaid = addteaidEdit.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    FormBody.Builder builder = new FormBody.Builder();
                    SharedPreferences sharedPreferences = getSharedPreferences("myPreference", Context.MODE_PRIVATE);
                    Subject subject = new Subject();
                    subject.setSubject_name(addname);
                    subject.setSubject_id(addid);
                    subject.setTeacher_id(addteaid);
                    JSONObject jsonObject = subject.getJSONObject();

                    RequestBody requestBody = builder
                            .add("id",addid)
                            .add("teacherId",addteaid)
                            .add("studentId","")
                            .add("score","")
                            .add("subjectName", addname)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://"+getString(R.string.ipdress)+"/addSubjectByAndroid")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("", "run: 添加成功了吗"+responseData);
                    try{
                        JSONObject jsonObjectre = new JSONObject(responseData.toString());

                        String resflag = jsonObjectre.getString("status");

                        if(resflag.equals("true")){
                            Looper.prepare();
                            Toast.makeText(AddClass.this, "添加成功啦！", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        } else{
                            Looper.prepare();
                            Toast.makeText(AddClass.this, "添加失败了(@_@)", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }catch (Exception e){
                        Looper.prepare();
                        Toast.makeText(AddClass.this,"添加失败了(@_@)", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                    Log.d("LoginActivity", "已接受服务端数据"+responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
