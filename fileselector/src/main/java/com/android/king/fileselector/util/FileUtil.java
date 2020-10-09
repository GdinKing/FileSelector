package com.android.king.fileselector.util;

import android.text.TextUtils;

import java.text.DecimalFormat;

/***
 * 名称：FileUtil
 * 描述：文件工具类
 * 最近修改时间：2018年09月21日 10:32分
 * @since 2018-09-21
 * @author king
 */
public class FileUtil {
    //图片类型
    public static final String[] IMAGE_TYPES = {
            ".png",
            ".jpg",
            ".jpeg",
            ".gif",
            ".bmp",
            ".wbmp",
            ".ico",
            ".webp"
    };
    //视频类型
    public static final String[] VIDEO_TYPES = {
            ".asf",
            ".mp4",
            ".3gp",
            ".avi",
            ".flv",
            ".f4v",
            ".mpeg",
            ".mov",
            ".movie",
            ".mpg",
            ".m4e",
            ".mkv",
            ".rmvb",
            ".webm",
            ".wm",
            ".wmv"
    };

    /**
     * 文档类型
     */
    public static final String[] DOC_TYPES = {
            ".txt",
            ".doc",
            ".docx",
            ".ppt",
            ".pptx",
            ".xls",
            ".xlsx",
            ".pdf"
    };

    /**
     * 音频类型
     */
    public static final String[] AUDIO_TYPES = {
            ".mp3",
            ".wav",
            ".wma",
            ".aac",
            ".ogg",
            ".asf"
    };

    /**
     * 判断是否是图片类型
     *  Is image
     *
     * @param name
     * @return
     */
    public static boolean isImage(String name) {
        if (TextUtils.isEmpty(name)) {
            return false;
        }
        String fileName = name.toLowerCase();
        for (int i = 0; i < IMAGE_TYPES.length; i++) {
            if (fileName.endsWith(IMAGE_TYPES[i])) {
                return true;
            }
        }
        return false;

    }

    /**
     * 判断是否是视频类型
     * Is video
     *
     * @param name
     * @return
     */
    public static boolean isVideo(String name) {
        if (TextUtils.isEmpty(name)) {
            return false;
        }
        String fileName = name.toLowerCase();
        for (int i = 0; i < VIDEO_TYPES.length; i++) {
            if (fileName.endsWith(VIDEO_TYPES[i])) {
                return true;
            }
        }
        return false;

    }

    /**
     * 判断是否文档类型
     * Is document
     * @param name
     * @return
     */
    public static boolean isDoc(String name) {
        if (TextUtils.isEmpty(name)) {
            return false;
        }
        String fileName = name.toLowerCase();
        for (int i = 0; i < DOC_TYPES.length; i++) {
            if (fileName.endsWith(DOC_TYPES[i])) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断是否是音频类型
     * Is audio
     * @param name
     * @return
     */
    public static boolean isAudio(String name) {
        if (TextUtils.isEmpty(name)) {
            return false;
        }
        String fileName = name.toLowerCase();
        for (int i = 0; i < AUDIO_TYPES.length; i++) {
            if (fileName.endsWith(AUDIO_TYPES[i])) {
                return true;
            }
        }
        return false;

    }
    /**
     * 获取文件扩展名
     * Get the suffix of the file
     *
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return "";
        }
        String suffix = "";
        if (!fileName.contains(".")) {
            suffix = "";
        } else {
            suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
        }
        return suffix;

    }

    /**
     * 格式化文件大小
     * @param size
     * @return
     */
    public static String formatFileSize(long size) {
        DecimalFormat df = new DecimalFormat("#.0");
        double baseKB = 1024.00;
        double baseMB = 1024 * 1024.00;
        String strSize = "";
        if (size < baseKB) {
            strSize = size + " B";
        }
        if (size > baseKB && size < baseMB) {
            strSize = df.format(size / baseKB) + "KB";
        } else if (size > baseMB) {
            strSize = df.format(size / baseMB) + "MB";
        }
        return strSize;
    }
}
