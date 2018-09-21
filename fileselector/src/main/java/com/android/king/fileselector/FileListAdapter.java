package com.android.king.fileselector;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.king.fileselector.util.FileIconGenerator;
import com.android.king.fileselector.util.FileUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * 文件选择列表Adapter
 *
 * @since 2017/11/3
 * @author king
 */
public class FileListAdapter extends BaseAdapter {

    private Context context;
    /**
     * 数据源
     */
    private List<File> files;

    /**
     * 是否多选
     */
    private boolean isMultiSelect;

    /**
     * 是否选择目录
     */
    private boolean isSelectDir = false;

    /**
     * 最大选择数
     */
    private int maxSelects = 3;

    /**
     * 选中的文件集合
     */
    private ArrayList<File> selectList = new ArrayList<>();

    public FileListAdapter(Context context, boolean isMultiSelect) {
        this.context = context;
        this.isMultiSelect = isMultiSelect;
    }

    @Override
    public int getCount() {
        if (files == null) {
            return 0;
        }
        return files.size();
    }

    @Override
    public Object getItem(int position) {
        if (files == null) {
            return null;
        }
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_file, null);
            viewHolder = new ViewHolder();
            viewHolder.tv1 = convertView.findViewById(R.id.text1);
            viewHolder.tv2 = convertView.findViewById(R.id.text2);
            viewHolder.icon_image = convertView.findViewById(R.id.icon_image);
            viewHolder.icon_text = convertView.findViewById(R.id.icon_text);
            viewHolder.iv_check = convertView.findViewById(R.id.iv_check);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        File file = files.get(position);
        viewHolder.tv1.setText(file.getName());

        if (file.isDirectory()) { //目录
            viewHolder.icon_image.setImageResource(R.drawable.icon_folder);
            viewHolder.tv2.setVisibility(View.GONE);
            viewHolder.icon_image.setVisibility(View.VISIBLE);
            viewHolder.icon_text.setVisibility(View.GONE);
            viewHolder.iv_check.setVisibility(isSelectDir? View.VISIBLE: View.GONE);
        } else {  //文件
            String text = FileUtil.getSuffix(file.getName());
            if (TextUtils.isEmpty(text)) {
                viewHolder.icon_image.setVisibility(View.VISIBLE);
                viewHolder.icon_text.setVisibility(View.GONE);
                viewHolder.icon_image.setImageResource(R.drawable.icon_unknow);
            } else {
                viewHolder.icon_image.setVisibility(View.GONE);
                viewHolder.icon_text.setVisibility(View.VISIBLE);
                int color = new FileIconGenerator(context).getColor(text);
                if (text.length() >= 3) {
                    viewHolder.icon_text.setText(text.substring(0, 3));
                } else {
                    viewHolder.icon_text.setText(text);
                }
                viewHolder.icon_text.setBackgroundColor(color);
            }

            viewHolder.tv2.setVisibility(View.VISIBLE);
            viewHolder.tv2.setText(FileUtil.formatFileSize(file.length()) + " | " + formatDate(file.lastModified()));
            if (isMultiSelect) {
                viewHolder.iv_check.setVisibility(View.VISIBLE);
            } else {
                viewHolder.iv_check.setVisibility(View.GONE);
            }
        }
        if (selectList.contains(file)) {
            viewHolder.iv_check.setImageResource(R.drawable.icon_checked);
        } else {
            viewHolder.iv_check.setImageResource(R.drawable.icon_uncheck);
        }
        return convertView;
    }

    /**
     * 格式化文件创建时间
     *
     * @param time
     * @return
     */
    private String formatDate(long time) {
        Date d = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(d);
    }

    static class ViewHolder {
        public TextView tv1, tv2, icon_text;
        public ImageView icon_image, iv_check;
    }

    public void setMultiSelect(boolean multiSelect) {
        isMultiSelect = multiSelect;
        notifyDataSetChanged();
    }

    public boolean isMultiSelect() {
        return isMultiSelect;
    }

    public ArrayList<File> getSelectList() {
        return selectList;
    }

    /**
     * 添加文件
     *
     * @param selectFile
     */
    public void addSelect(File selectFile) {
        if (selectList.size() >= maxSelects) {
            Toast.makeText(context, "最多选择" + maxSelects + "个文件", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!selectList.contains(selectFile)) {
            selectList.add(selectFile);
        }
        notifyDataSetChanged();
    }

    /**
     * 移除选中文件
     *
     * @param file
     */
    public void removeSelect(File file) {
        if (selectList.contains(file)) {
            selectList.remove(file);
        }
        notifyDataSetChanged();
    }

    public int getMaxSelects() {
        return maxSelects;
    }

    public void setMaxSelects(int maxSelects) {
        this.maxSelects = maxSelects;
    }

    /**
     * 刷新数据
     * @param fileList
     */
    public void refreshData(List<File> fileList) {
        this.files = fileList;
        notifyDataSetChanged();
    }
}
