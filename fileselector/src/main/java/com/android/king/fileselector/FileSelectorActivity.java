package com.android.king.fileselector;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.king.fileselector.util.FileUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * File Selector
 *
 * @author king
 * @since 2017/11/3
 */
public class FileSelectorActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ACTIVITY_KEY_MULTI = "multi_select";
    public static final String ACTIVITY_KEY_MAX_COUNT = "max_select_count";
    public static final String ACTIVITY_KEY_FILE_TYPE = "file_type";
    public static final String ACTIVITY_KEY_FILEROOT = "file_root";
    public static final String ACTIVITY_KEY_RESULT_PATHLIST = "file_path_list";

    private ListView listView;
    private LinearLayout layoutGuide;
    private FileListAdapter listAdapter;
    private List<File> fileList = new ArrayList<>();
    private File parentDir;
    private File currentDir;
    private TextView mBackImgBtn;
    private TextView mEmptyView;
    private TextView mRightText;
    private HorizontalScrollView scrollView;

    /**
     * file type
     */
    public static final String FILE_TYPE_IMAGE = "image";
    public static final String FILE_TYPE_VIDEO = "video";
    public static final String FILE_TYPE_DOC = "doc";
    public static final String FILE_TYPE_AUDIO = "audio";

    /**
     * max select
     */
    private int maxSelect;
    /**
     * file type filter list
     */
    private List<String> filterList;

    private boolean isMultiSelect = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_selector);

        mEmptyView = findViewById(R.id.tv_empty);
        mRightText = findViewById(R.id.right_tv);
        mBackImgBtn = findViewById(R.id.back_img_btn);
        mBackImgBtn.setVisibility(View.VISIBLE);
        mBackImgBtn.setOnClickListener(this);

        scrollView = findViewById(R.id.scrollView);
        layoutGuide = findViewById(R.id.layoutGuide);
        listView = findViewById(R.id.listview);

        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager
                .PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            return;
        }
        initData();
    }

    private void initData() {

        isMultiSelect = getIntent().getBooleanExtra(ACTIVITY_KEY_MULTI, false);
        String fileType = getIntent().getStringExtra(ACTIVITY_KEY_FILE_TYPE);
        String filePath = getIntent().getStringExtra(ACTIVITY_KEY_FILEROOT);
        maxSelect = getIntent().getIntExtra(ACTIVITY_KEY_MAX_COUNT, 3);
        filterList = getFilterType(fileType);

        if (filePath == null || filePath.trim().length() == 0) {
            currentDir = Environment.getExternalStorageDirectory();
        } else {
            currentDir = new File(filePath);
            if (!currentDir.exists()) {
                currentDir = Environment.getExternalStorageDirectory();
            }
        }
        parentDir = Environment.getExternalStorageDirectory();//设置主目录
        fileList = getFileList(currentDir);
        listAdapter = new FileListAdapter(this, isMultiSelect);
        listAdapter.setMaxSelects(maxSelect);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(itemClickListener);
        listView.setEmptyView(mEmptyView);

        if (isMultiSelect) {
            mRightText.setVisibility(View.VISIBLE);
            mRightText.setOnClickListener(this);
            mRightText.setText("确定(" + listAdapter.getSelectList().size() + ")");
        } else {
            mRightText.setVisibility(View.GONE);
        }
        initGuideView(currentDir);
        showFiles(currentDir);

    }

    /**
     * show top guide view
     *
     * @param parent
     */
    private void initGuideView(File parent) {
        if (parent.getAbsolutePath().equals(Environment.getExternalStorageDirectory().getParentFile().getAbsolutePath())) {
            return;
        }
        if (parent != null) {
            initGuideView(parent.getParentFile());
            addGuideView(parent);
        }
    }

    /**
     * get file type filter
     *
     * @param typeStr
     * @return
     */
    private List<String> getFilterType(String typeStr) {
        if (TextUtils.isEmpty(typeStr)) {
            return null;
        }
        List<String> typeList = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(typeStr);

            for (int i = 0; i < array.length(); i++) {
                typeList.add(array.getString(i));
            }
        } catch (JSONException e) {
            typeList = null;
        }
        return typeList;
    }


    /**
     * get file list from @file
     *
     * @param file current file
     */
    private List<File> getFileList(File file) {
        List<File> fileList = new ArrayList<>();
        File[] files = file.listFiles(f -> {
            if (!f.canRead() || f.isHidden()) {
                return false;
            }
            if (f.isDirectory()) {
                return true;
            } else if (filterList != null && filterList.size() > 0) {//过滤条件
                String name = f.getName();
                boolean result = false;
                for (String type : filterList) {
                    if (FILE_TYPE_IMAGE.equals(type) && FileUtil.isImage(name)) {
                        result = true;
                        break;
                    } else if (FILE_TYPE_VIDEO.equals(type) && FileUtil.isVideo(name)) {
                        result = true;
                        break;
                    } else if (FILE_TYPE_DOC.equals(type) && FileUtil.isDoc(name)) {
                        result = true;
                        break;
                    } else if (FILE_TYPE_AUDIO.equals(type) && FileUtil.isAudio(name)) {
                        result = true;
                        break;
                    } else if (name.endsWith("." + type)) {
                        result = true;
                        break;
                    }
                }
                return result;
            }
            return f.getName().trim().length() != 0;
        });
        if (files != null) {
            fileList = Arrays.asList(files);
        }
        return fileList;
    }

    /**
     * sort file
     *
     * @param list
     * @return
     */
    private List<File> sortFiles(List<File> list) {
        Collections.sort(list, (file, newFile) -> {
            if (file.isDirectory() && newFile.isFile()) {
                return -1;
            }
            if (file.isFile() && newFile.isDirectory()) {
                return 1;
            }
            return file.getName().toLowerCase().compareTo(newFile.getName().toLowerCase());
        });
        return list;
    }

    private OnItemClickListener itemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            File f = fileList.get(position);
            if (f.isDirectory()) {  //目录
                addGuideView(f);
                showFiles(f);
            } else if (listAdapter.isMultiSelect()) {
                if (listAdapter.getSelectList().contains(f)) {
                    listAdapter.removeSelect(f);
                } else {
                    listAdapter.addSelect(f);
                }
                mRightText.setText("确定(" + listAdapter.getSelectList().size() + ")");
            } else {

                ArrayList<String> array = new ArrayList<>();
                array.add("file://" + f.getAbsolutePath());
                Intent intent = new Intent();
                intent.putExtra(ACTIVITY_KEY_RESULT_PATHLIST, array);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

        }
    };

    /**
     * add guide view
     *
     * @param f
     */
    private void addGuideView(File f) {
        if (f == null) {
            return;
        }

        TextView tvMain = new TextView(this);
        if (f.getAbsolutePath().equals(Environment.getExternalStorageDirectory().getAbsolutePath())) {
            tvMain.setText("存储");
        } else {
            tvMain.setText(f.getName());
        }
        tvMain.setId(f.hashCode());
        tvMain.setTextSize(14);
        tvMain.setTag(f.getAbsolutePath());
        tvMain.setGravity(Gravity.CENTER);
        tvMain.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_right, 0);
        tvMain.setPadding(0, 20, 5, 20);

        tvMain.setOnClickListener((view) -> {
            String filePath = (String) view.getTag();
            File file = new File(filePath);
            for (int i = 0; i < layoutGuide.getChildCount(); i++) {
                View tv = layoutGuide.getChildAt(i);
                if (filePath.equals(tv.getTag())) {
                    if (i + 1 == layoutGuide.getChildCount()) {
                        continue;
                    }
                    layoutGuide.removeViews(i + 1, layoutGuide.getChildCount() - i - 1);
                }
            }
            showFiles(file);
        });

        layoutGuide.addView(tvMain);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 300);
    }

    /**
     * 打开文件夹
     *
     * @param f
     */
    private void showFiles(File f) {
        currentDir = f;
        fileList = getFileList(currentDir);
        sortFiles(fileList);
        listAdapter.refreshData(fileList);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back_img_btn) {
            setResult(Activity.RESULT_CANCELED);
            finish();
        } else if (i == R.id.right_tv) {
            try {
                if (listAdapter.getSelectList().size() == 0) {
                    Toast.makeText(this, "没有选择任何文件", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<String> array = new ArrayList<>();
                for (File file : listAdapter.getSelectList()) {
                    array.add("file://" + file.getAbsolutePath());
                }
                Intent intent = new Intent();
                intent.putExtra(ACTIVITY_KEY_RESULT_PATHLIST, array);
                setResult(Activity.RESULT_OK, intent);
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "获取文件信息出错", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (currentDir.getAbsolutePath().equals(parentDir.getAbsolutePath())) {//到达主目录，结束
            setResult(Activity.RESULT_CANCELED);
            finish();
        } else {
            if (layoutGuide != null && layoutGuide.getChildCount() > 0) {
                layoutGuide.removeViewAt(layoutGuide.getChildCount() - 1);
            }
            showFiles(currentDir.getParentFile());
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//获得了权限
                initData();
            } else {
                finish();
            }
        }
    }
}
