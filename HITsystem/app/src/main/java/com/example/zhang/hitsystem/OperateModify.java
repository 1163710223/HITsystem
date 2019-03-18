package com.example.zhang.hitsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OperateModify extends AppCompatActivity {

    EditText modifyEdit;
    EditText scoreEdit;
    EditText nameEdit;
    EditText classEdit;
    EditText teachernameEdit;
    String classname;
    String classid;
    String studentid;
    String studentname;
    String studentscore;
    String teachername;
    String teacherid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operate_modify);

        initView();

        Intent intent = getIntent();
        classid = intent.getStringExtra("classid");
        classname = intent.getStringExtra("classname");
    }

    public void initView(){
        modifyEdit = (EditText)findViewById(R.id.modifyEdit);
        scoreEdit = (EditText)findViewById(R.id.scoreEdit);
        nameEdit = (EditText)findViewById(R.id.nameEdit);
        classEdit = (EditText)findViewById(R.id.classnameEdit);
        teachernameEdit = (EditText)findViewById(R.id.teachernameEdit);
    }

    public void modify_query(View view){
        studentid = modifyEdit.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    FormBody.Builder builder = new FormBody.Builder();
                    Log.d("", "run: "+classname+studentid+"nameheid");
                    RequestBody requestBody = builder
                            .add("subjectname", classname)
                            .add("student_id", studentid)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://"+getString(R.string.ipdress)+"/reqSubjectByAndroid")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("qingqiuaaa", "run: "+responseData);
                    JSONObject jsonO = new JSONObject(responseData);
                    final boolean flag = jsonO.getBoolean("status");
                    if (flag){
                        JSONArray jsonArray = jsonO.getJSONArray("data");
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        studentscore = jsonObject.getString("score");
                        studentid = jsonObject.getString("student_id");
                        teacherid = jsonObject.getString("teacher_id");
                        studentname = reqname("reqstumsg", studentid);
                        teachername = reqname("reqteamsg", teacherid);
                        Log.d("", "老师和学生姓名: "+studentname+teachername);
                        Log.d("LoginActivity", "已接受服务端数据"+responseData);
                    }else{

                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(flag){
                                nameEdit.setText(studentname);
                                classEdit.setText(classname);
                                teachernameEdit.setText(teachername);
                                scoreEdit.setText(studentscore);
                            }else {
                                AlertDialog.Builder dia = new AlertDialog.Builder(OperateModify.this);
                                dia.setTitle("错误");
                                dia.setMessage("这个学生的成绩尚未录入");
                                dia.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        nameEdit.setText("");
                                        classEdit.setText("");
                                        teachernameEdit.setText("");
                                        scoreEdit.setText("");
                                    }
                                });
                                dia.show();
                            }

                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("LoginActivity", "arun: "+"cuolea");
                }
            }
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
    public void modify_commit(View view){
        studentscore = scoreEdit.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    studentscore = scoreEdit.getText().toString();
                    OkHttpClient client = new OkHttpClient();
                    FormBody.Builder builder = new FormBody.Builder();
                    Log.d("", "run: "+classname+studentid+"nameheid");
                    RequestBody requestBody = builder
                            .add("subjectId", classid)
                            .add("studentId", studentid)
                            .add("score",studentscore)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://"+getString(R.string.ipdress)+"/modifyStudentScore")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("qingqiuaaa", "run: "+responseData);
                    JSONObject jsonO = new JSONObject(responseData);
                    final boolean flag = jsonO.getBoolean("status");
                    Log.d("flag", "run: "+flag);
                    if (flag){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                    AlertDialog.Builder dia = new AlertDialog.Builder(OperateModify.this);
                                    dia.setTitle("完成");
                                    dia.setMessage("您已成功修改此学生的成绩！");
                                    dia.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            nameEdit.setText("");
                                            classEdit.setText("");
                                            teachernameEdit.setText("");
                                            scoreEdit.setText("");
                                        }
                                    });
                                    dia.show();
                            }
                        });
                    }else{

                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("LoginActivity", "arun: "+"cuolea");
                }
            }
        }).start();
    }
}
