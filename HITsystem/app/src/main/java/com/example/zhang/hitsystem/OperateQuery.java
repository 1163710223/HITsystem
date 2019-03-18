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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.ScoreAdapter;
import bean.Score;
import bean.Subject;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OperateQuery extends AppCompatActivity {

    String classid;
    String classname;
    String usrid;
    TextView textView;
    private List<Score> scoreList;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operate_query);

        Intent intent = getIntent();
        classid = intent.getStringExtra("classid");
        classname = intent.getStringExtra("classname");
        SharedPreferences sharedPreferences = getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        usrid = sharedPreferences.getString("email","");
        scoreList = new ArrayList<>();
        if(usrid.length()==8){
            initStudents();
        }else{
            initSubjects();
            //Log.d("listlimian", "onCreate: "+scoreList.get(0).toString());
            textView = (TextView)findViewById(R.id.query_midle);
            textView.setText("科目名称");
        }
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        //Log.d("listlimian", "onCreate: "+scoreList.get(0).toString());
                        RecyclerView recyclerView = findViewById(R.id.student_score_recyclerview);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(OperateQuery.this);
                        recyclerView.setLayoutManager(layoutManager);
                        ScoreAdapter adapter = new ScoreAdapter(scoreList);
                        recyclerView.setAdapter(adapter);
                        break;
                    default:
                        AlertDialog.Builder dialog = new AlertDialog.Builder(OperateQuery.this);
                        dialog.setTitle("错误");
                        dialog.setMessage("网络开小差啦！再试一下吧～");
                        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialog.show();
                }
            }
        };
    }
    public void initStudents(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = new String("http://"+getString(R.string.ipdress)+"/reqsubscore?subjectId="+classid);
                    Log.d("", "urlurl: "+url);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    String responsedata = response.body().string();
                    Log.d("initstudnets", "run: "+responsedata);

                    JSONObject jsonObjectre = new JSONObject(responsedata);
                    JSONArray jsonArray = new JSONArray(jsonObjectre.get("subjects").toString());
                    Score stu;
                    String name;
                    String id;
                    String score;
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                        id = jsonObject.getString("student_id");
                        score = jsonObject.getString("score");
                        name = reqname("reqstumsg", id);
                        stu = new Score(name, id, score);
                        scoreList.add(stu);
                    }
                    mHandler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void initSubjects(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = new String("http://"+getString(R.string.ipdress)+"/reqSubByStuId?studentid="+usrid);
                    Log.d("", "run: "+url);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    String responsedata = response.body().string();
                    Log.d("chaxunkechengchengji", "run: "+responsedata);

                    JSONObject jsonObjectre = new JSONObject(responsedata);
                    JSONArray jsonArray = new JSONArray(jsonObjectre.get("subjects").toString());
                    Score stu;
                    String name;
                    String id;
                    String score;
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                        id = jsonObject.getString("student_id");
                        score = jsonObject.getString("score");
                        name = jsonObject.getString("subject_name");
                        stu = new Score(name, id, score);
                        Log.d("jiashanglema", "run:"+stu.toString());

                        scoreList.add(stu);
                        Log.d("listlimian", "onCreate: "+scoreList.get(0).toString());
                    }
                    mHandler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public String reqname(String url, String id){
        String name = null;
        try{
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder builder = new FormBody.Builder();
            RequestBody requestBody = builder
                    .add("id", id)
                    .build();
            Request request = new Request.Builder()
                    .url("http://"+getString(R.string.ipdress)+"/"+url)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            Log.d("", "run: "+responseData);
            JSONObject jsonObject = new JSONObject(responseData);
            name = jsonObject.getString("name");
            Log.d("LoginActivity", "已接受服务端数据"+responseData);
        }catch (Exception e){
            e.printStackTrace();
            Log.d("LoginActivity", "arun: "+"cuolea");
        }
        return name;
    }
    public ArrayList<Subject> init(){
        ArrayList<Subject> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            Subject subject = new Subject();
            subject.setTeacher_id("20181234");
            subject.setSubject_id("math001");
            subject.setSubject_name("gaoshu");
            subject.setStudent_id("1163710215");
            subject.setScore(55+i*5);
        }
        return list;
    }
}
