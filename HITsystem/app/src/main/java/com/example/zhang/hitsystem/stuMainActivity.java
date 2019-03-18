package com.example.zhang.hitsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.ClassAdapter;
import bean.Class;
import bean.Student;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class stuMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences myPreference;
    TextView stuname;
    TextView stuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /*test*/

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        requstmsg(id);

    }

    public void puton(Student student){
        Log.d("", "puton: "+student.getName()+student.getId());
        stuname = (TextView)findViewById(R.id.stuname);
        stuid = (TextView)findViewById(R.id.stuid);
        stuname.setText(student.getName());
        stuid.setText(student.getId());
        Log.d("MainActivity", "puton: 应该改成功了啊");

    }

    @Override
    public void onBackPressed() {
        myPreference = getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        String email = myPreference.getString("email", "0");
        Log.d("MainActivity", "onCreate: email"+email);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(stuMainActivity.this, Setting.class);
                            startActivity(intent);
                        }
                    });
                }
            }).start();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_selfmsg) {
            Intent intent = new Intent(stuMainActivity.this, SelfInfo.class);
            startActivity(intent);
        } else if (id == R.id.nav_classmanage) {
            //跳转至其他活动
            Intent intent = new Intent(stuMainActivity.this, stuClassManage.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(stuMainActivity.this, ACDicussTopics.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(stuMainActivity.this, ACDicussTopics.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Student requstmsg(String id){
        final Student student = new Student();

        final String thisurl = "http://"+getString(R.string.ipdress)+"/reqstumsg?id="+id;
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
                    JSONObject jsonObject = new JSONObject(responseData.toString());
                    student.setId(jsonObject.getString("id"));
                    student.setName(jsonObject.getString("name"));
                    student.setPhonenum(jsonObject.getString("phonenum"));
                    student.setYear(jsonObject.getString("year"));
                    Log.d("SelfInfo", "已接受服务端数据"+jsonObject);
                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        puton(student);
                    }
                });
            }
        }).start();
        return student;
    }

}
