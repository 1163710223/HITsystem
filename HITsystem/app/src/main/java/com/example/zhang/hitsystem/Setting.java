package com.example.zhang.hitsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Setting extends AppCompatActivity {

    EditText ipdressEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    public void initView(){
        ipdressEdit = (EditText)findViewById(R.id.ipdressEdit);
    }
    public void settingcommit(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this);

        //builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("警告");
        builder.setMessage("此页面请谨慎修改！如果您确定已经得到专业人士指导，请点击确定，如需重新考虑，请点击取消！");
        // 最右边的按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                // 提示信息
                //这里写实际修改的代码
                String a = getResources().getString(R.string.ipdress);
                a = String.format(ipdressEdit.getText().toString());
                Toast toast = Toast.makeText(getApplicationContext(), "您已成功修改", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        //	倒数第二个按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                //	提示信息
                Toast toast = Toast.makeText(getApplicationContext(), "修改已取消", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        //	Diglog的显示
        builder.create().show();

    }
}
