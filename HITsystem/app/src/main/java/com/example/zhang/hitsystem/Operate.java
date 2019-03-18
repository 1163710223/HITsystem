package com.example.zhang.hitsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Operate extends AppCompatActivity {

    private String classname;
    private String classid;
    TextView classnameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operate);
        Intent intent=getIntent();
        classname=intent.getStringExtra("classname");
        classid=intent.getStringExtra("classid");
        classnameText = (TextView)findViewById(R.id.maininfo);
        classnameText.setText(classname);

    }

    public void add(View view){
        Intent intent = new Intent(Operate.this, OperateAdd.class);
        intent.putExtra("classname",classname);
        intent.putExtra("classid",classid);
        startActivity(intent);
    }
    public void modify(View view){
        Intent intent = new Intent(Operate.this, OperateModify.class);
        intent.putExtra("classname",classname);
        intent.putExtra("classid",classid);
        startActivity(intent);
    }
    public void query(View view){
        Intent intent = new Intent(Operate.this, OperateQuery.class);
        intent.putExtra("classname",classname);
        intent.putExtra("classid",classid);
        startActivity(intent);
    }
    public void analyze(View view){
        Intent intent = new Intent(Operate.this, ScoreAnalyze.class);
        intent.putExtra("classname",classname);
        intent.putExtra("classid",classid);
        startActivity(intent);
    }
    public void delete(View view){

    }
}
