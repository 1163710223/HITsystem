package com.example.zhang.hitsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.DiscussAdapter;
import bean.Discuss;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ACDiscuss extends AppCompatActivity {

    private List<Discuss> discussList = new ArrayList<>();
    TextView topicText;
    EditText sendmsgEdit;
    String topic;
    String peopleid;
    String sendmsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);

        Intent intent = getIntent();
        topic = intent.getStringExtra("discusstopic");
        topicText = (TextView)findViewById(R.id.discussTopic);
        topicText.setText(topic);

        SharedPreferences sharedPreferences = getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        peopleid = sharedPreferences.getString("email", "");
        initAC();

    }

    private void initAC(){
        discussList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String url = new String("http://"+getString(R.string.ipdress)+"/reqdis?disname="+topic);
                    Log.d("SelfInfo","开启线程发送请求");
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("SelfInfo", "接受的服务端数据"+responseData);
                    JSONArray jsonArray = new JSONArray(responseData);
                    jsonArray = new JSONArray(jsonArray.get(0).toString());
                    String discussion_id;
                    String name;
                    String student_id;
                    String student_words;
                    String teacher_id;
                    String teacher_words;
                    JSONObject jsonObject;
                    for(int i=0;i<jsonArray.length();i++){
                        Discuss discuss;
                        jsonObject = new JSONObject(jsonArray.get(i).toString());
                        discussion_id = jsonObject.getString("discussion_id");
                        name = jsonObject.getString("name");
                        student_id = jsonObject.getString("student_id");
                        teacher_id = jsonObject.getString("teacher_id");
                        student_words = jsonObject.getString("student_words");
                        teacher_words = jsonObject.getString("teacher_words");
                        discuss = new Discuss(discussion_id,topic,student_id,student_words,teacher_id,teacher_words);
                        //System.out.println(discuss.toString());
                        discussList.add(discuss);
                    }
                    discussList.add(new Discuss("00000",topic,"社区管理员","请文明发言哦","社区管理员","请文明发言哦"));
                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView recyclerView = findViewById(R.id.discusses);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(ACDiscuss.this);
                        //layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerView.setLayoutManager(layoutManager);
                        DiscussAdapter adapter = new DiscussAdapter(discussList);
                        recyclerView.setAdapter(adapter);
                    }
                });

            }
        }).start();
    }

    public void senddis(View view){
        sendmsgEdit = (EditText)findViewById(R.id.edit_text);
        sendmsg = sendmsgEdit.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    SharedPreferences  myPreference = getSharedPreferences("myPreference", Context.MODE_PRIVATE);
                    String id = myPreference.getString("email","0");

                    Log.d("peopleid", "run: "+peopleid);
                    String url = new String("http://"+getString(R.string.ipdress)+"/addDiscussion?disname="+topic+"&content="+sendmsg+"&id="+peopleid);
                    Log.d("SelfInfo","开启线程发送请求");
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("SelfInfo", "接受的服务端数据"+responseData);
                    JSONObject jsonObject = new JSONObject(responseData);
                    String status = jsonObject.getString("status");
                    if(status.equals("true")){
                        initAC();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //initAC();
                        RecyclerView recyclerView = findViewById(R.id.discusses);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(ACDiscuss.this);
                        //layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerView.setLayoutManager(layoutManager);
                        DiscussAdapter adapter = new DiscussAdapter(discussList);
                        recyclerView.setAdapter(adapter);
                    }
                });

            }
        }).start();

    }

}
