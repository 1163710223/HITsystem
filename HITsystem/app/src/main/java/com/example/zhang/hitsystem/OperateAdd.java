package com.example.zhang.hitsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.AddAdapter;
import adapter.ScoreAdapter;
import bean.Class;
import bean.Score;
import bean.Subject;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OperateAdd extends AppCompatActivity implements AddAdapter.SaveEditListener{

    List<Score> mscore;
    EditText addnum;
    EditText addstunum;
    EditText addstuscore;
    int x;//录入的学生数目
    Map<Integer,Score> map;

    String classname;
    String classid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operate_add);

        Intent intent = getIntent();
        classname = intent.getStringExtra("classname");
        classid = intent.getStringExtra("classid");

        initview();

    }

    //初始化空列表
    public void initAdd(int x) {
        Log.d("whatisx", "initAdd: " + x);
        for (int i = 0; i < x; i++) {
            Score score = new Score();
            mscore.add(score);
        }
        Log.d("whatisx", "initAdd: 初始化完成");
    }

    public void initview() {
        addnum = (EditText) findViewById(R.id.addnum);
        addstunum = (EditText) findViewById(R.id.addstunum1);
        addstunum = (EditText) findViewById(R.id.addstunum1);
        mscore = new ArrayList<>();
        map = new HashMap<Integer, Score>();
    }

    //输入要输入的学生数目然后点确定
    public void addnum(View view) {
        x = Integer.valueOf(addnum.getText().toString());
        setContentView(R.layout.activity_operate_add_2);
        initAdd(x);
        RecyclerView recyclerView = findViewById(R.id.addrecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        AddAdapter adapter = new AddAdapter(mscore, OperateAdd.this){};
        recyclerView.setAdapter(adapter);
    }

    public void addtoserver(View view) {
        OnSave(view);
    }

    //监听保存
    public void OnSave(View view) {
        //处理存储edittext的map
        //如判断客户名称是否填写且不为空格
        //遍历map中的值
        Log.d("OPerateAdd", "OnSave: aaa"+map.size());
        for(Map.Entry<Integer, Score> mapentry:map.entrySet()){
            System.out.println(mapentry.getValue().toString());
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    FormBody.Builder builder = new FormBody.Builder();
                    List<Subject> list = new ArrayList<>();

                    Subject subject = new Subject();
                    SharedPreferences sharedPreferences = getSharedPreferences("myPreference", Context.MODE_PRIVATE);
                    String teacherid = sharedPreferences.getString("email","");
                    subject.setTeacher_id(teacherid);
                    JSONArray jsonArray = new JSONArray();
                    for(Map.Entry<Integer, Score> mapentry:map.entrySet()){
                        subject.setScore(Double.valueOf(mapentry.getValue().getScore()));
                        subject.setStudent_id(mapentry.getValue().getStunum());
                        subject.setSubject_name(classname);
                        subject.setSubject_id(classid);
                        JSONObject jsonObject = subject.getJSONObject();
                        jsonArray.put(jsonObject);
                    }

                    Log.d("jsonArray", "run: "+jsonArray.toString());

                    String jsonString = jsonArray.toString();
                    JSONArray jsonArray1 = new JSONArray(jsonString);
                    for(int i=0;i<jsonArray1.length();i++){
                        JSONObject jsonObject = jsonArray1.getJSONObject(i);
                        Subject subject1 = new Subject();
                        subject.setScore(Double.valueOf(jsonObject.get("score").toString()));
                        subject.setSubject_name(jsonObject.getString("subject_name"));
                        subject.setStudent_id(jsonObject.getString("student_id"));
                        subject.setTeacher_id(jsonObject.getString("teacher_id"));
                        subject.setSubject_id(jsonObject.getString("subject_id"));
                        Log.d("", "run: "+subject.toString());;
                    }
                    Log.d("", "run: "+jsonString);
                    RequestBody requestBody = builder
                            .add("subject", jsonString)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://"+getString(R.string.ipdress)+"/addScoreByAndroid")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("", "run: 添加成功了吗"+responseData);
                    try{
                        JSONObject jsonObject = new JSONObject(responseData.toString());

                        String resflag = jsonObject.getString("status");

                        if(resflag.equals("true")){
                            Looper.prepare();
                            Toast.makeText(OperateAdd.this, "添加成功啦！", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        } else{
                            Looper.prepare();
                            Toast.makeText(OperateAdd.this, "添加失败了(@_@)", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }catch (Exception e){
                        Looper.prepare();
                        Toast.makeText(OperateAdd.this,"添加失败了(@_@)", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                    //parseJSONWithJSONObject(responseData);
                    Log.d("LoginActivity", "已接受服务端数据"+responseData);

                    //耗时操作，完成之后发送消息给Handler，完成UI更新；
                    //mHandler.sendEmptyMessage(0);
                    //需要数据传递，用下面方法；
                    //Message msg =new Message();
                    //msg.obj = flag[0];//可以是基本类型，可以是对象，可以是List、map等；
                    //mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void SaveEdit(int position, String snum, String sscore) {
        Log.d("2", "SaveEdit: 传过来的是"+snum+sscore);
        map.put(position, new Score("",snum,sscore));
    }
}