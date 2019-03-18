package com.example.zhang.hitsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.DiscussTopicsAdapter;

import org.json.JSONArray;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ACDicussTopics extends AppCompatActivity {

    private List<String> topicList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acdicuss_topics);
        initTopic();
    }

    public void newdis(View view){
        Intent intent = new Intent(ACDicussTopics.this, NewDiscussion.class);
        startActivity(intent);
    }

    private void initTopic(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String thisurl = "http://"+getString(R.string.ipdress)+"/reqalldis";
                    Log.d("ACDicussTopics","开启线程发送请求");
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(thisurl).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("", "run: "+responseData);
                    JSONArray jsonArray = new JSONArray(responseData);
                    String s = jsonArray.getString(0);
                    jsonArray = new JSONArray(s);
                    for(int i=0;i<jsonArray.length();i++){
                        topicList.add(jsonArray.getString(i));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.ReTopics);
                        LinearLayoutManager layoutManager=new LinearLayoutManager(ACDicussTopics.this);
                        //layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerView.setLayoutManager(layoutManager);
                        DiscussTopicsAdapter adapter=new DiscussTopicsAdapter(ACDicussTopics.this, topicList);
                        recyclerView.setAdapter(adapter);

                        adapter.setItemClickListener(new DiscussTopicsAdapter.MyItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                String topic = topicList.get(position);
                                //Toast.makeText(ACDicussTopics.this, "点击了" + topic, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ACDicussTopics.this, ACDiscuss.class);
                                intent.putExtra("discusstopic",topic);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        }).start();
    }
}
