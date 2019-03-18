package com.example.zhang.hitsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class stuClassManage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_class_manage);
    }
    public void query(View view){
        Intent intent = new Intent(stuClassManage.this, OperateQuery.class);
        startActivity(intent);
    }
}
