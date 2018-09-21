package com.android.king.fileselector.util;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/***
 * 文件图标生成工具
 *
 * @since 2017/12/28
 * @author king
 */

public class FileIconGenerator {

    private Context mContext;

    public FileIconGenerator(Context context) {
        this.mContext = context;
    }

    private String getColorJson() {
        String result = "";
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputReader = new InputStreamReader(mContext.getResources().getAssets().open("colors.json"));
            bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)
                result += line;
        } catch (IOException e) {
            result = "";
        } finally {
            try {
                if (bufReader != null) {
                    bufReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (inputReader != null) {
                    inputReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    /**
     * 获取图标颜色
     * @param suffix
     * @return
     */
    public int getColor(String suffix) {
        String color = "#727b87";
        String json = getColorJson();
        if (TextUtils.isEmpty(json)) {
            return Color.parseColor(color);
        }
        try {
            JSONObject object = new JSONObject(json);
            color = object.getString(suffix);
        } catch (JSONException e) {
            color = "#727b87";
        }
        return Color.parseColor(color);
    }

}
