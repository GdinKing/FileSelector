package com.android.king.fileselector;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * @author king
 * @date 2020-05-12 09:47
 */
public class FileSelector {

    public static final class Builder {
        private Context mContext;
        private boolean isMultiple = false;
        private int maxCount = 1;
        private String fileRoot;
        private ArrayList<String> filterList;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setIsMultiple(boolean flag) {
            this.isMultiple = flag;
            return this;
        }

        public Builder setMaxCount(int count) {
            this.maxCount = count;
            return this;
        }

        public Builder setFileRoot(String fileRoot) {
            this.fileRoot = fileRoot;
            return this;
        }

        public Builder setFilters(ArrayList<String> filters) {
            this.filterList = filters;
            return this;
        }

        public Intent getIntent() {
            Intent intent = new Intent(this.mContext, FileSelectorActivity.class);
            intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_MULTI, this.isMultiple);  //是否多选模式
            intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_MAX_COUNT, this.maxCount);//限定文件选择数
            intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_FILEROOT, this.fileRoot); //初始路径
            intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_FILE_TYPE, this.filterList); //筛选文件类型
            return intent;
        }
    }
}
