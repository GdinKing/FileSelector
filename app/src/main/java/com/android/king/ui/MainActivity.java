package com.android.king.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.king.fileselector.FileSelector;
import com.android.king.fileselector.FileSelectorActivity;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "king";

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResult = findViewById(R.id.tv_result);
    }


    /**
     * 选择文件
     *
     * @param view
     */
    public void selectFile(View view) {
        //文件类型筛选  File Type Filter
        ArrayList<String> filters = new ArrayList<String>();
        filters.add(FileSelectorActivity.FILE_TYPE_IMAGE);
        filters.add(FileSelectorActivity.FILE_TYPE_VIDEO);
        filters.add(FileSelectorActivity.FILE_TYPE_DOC);
        filters.add(FileSelectorActivity.FILE_TYPE_AUDIO);

//        Intent intent = new Intent(this, FileSelectorActivity.class);
//        intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_MULTI, true);  //是否多选模式
//        intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_MAX_COUNT, 3);//限定文件选择数
//        intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_FILEROOT, ""); //初始路径
//        intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_FILE_TYPE, filters); //筛选文件类型

        FileSelector.Builder builder = new FileSelector.Builder(this);
        Intent intent = builder.setFileRoot("")//初始路径  init file root
                .setIsMultiple(true)//是否多选模式 whether is multiple select
                .setMaxCount(3)//限定文件选择数 max file count
                .setFilters(filters)//筛选文件类型  file filter
                .getIntent();
        startActivityForResult(intent, 100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100 && data != null) {
            try {
                ArrayList<String> pathList = data.getStringArrayListExtra(FileSelectorActivity.ACTIVITY_KEY_RESULT_PATHLIST);
                for (String path : pathList) {
                    Log.i(TAG, path);
                }
                tvResult.setText(pathList.toString());
            } catch (Exception e) {
            }
        }
    }
}
