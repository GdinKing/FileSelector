中文 | [English](https://github.com/GdinKing/FileSelector/blob/master/README_EN.md)
# 安卓文件选择器

## 预览

<img src='preview.gif' width='300' height='520'/>

## 引入

AppCompat版本：(建议升级AndroidX，以后AppCompat不再维护)
```groovy
 implementation 'com.king.ui:fileselector:1.0.1'
```
AndroidX版本：
```groovy
 implementation 'com.king.ui:fileselector:1.0.3'
```

## 特点

- 支持单选/多选
- 适配6.0权限申请
- 支持传入初始文件夹路径
- 支持筛选文件类型
- 兼容AndroidX

## 用法：

### 1.通过Intent打开文件选择器

version 1.0.1：

```java
  Intent intent = new Intent(this, FileSelectorActivity.class);
  intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_MULTI, true);  //是否多选模式
  intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_MAX_COUNT, 5);//限定文件选择数，默认为3
  intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_FILEROOT, ""); //初始路径
  intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_FILE_TYPE, ""); //筛选文件类型，数组字符串形式，例如["video","image","doc"]或[FileSelectorActivity.FILE_TYPE_IMAGE,FileSelectorActivity.FILE_TYPE_VIDEO]
//intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_FILE_TYPE, FileSelectorActivity.FILE_TYPE_IMAGE);//只展示图片
  startActivityForResult(intent, 100);
```
version 1.0.3：

```java
    //文件类型筛选
   ArrayList<String> filters = new ArrayList<String>();
   filters.add(FileSelectorActivity.FILE_TYPE_IMAGE);//图片
   filters.add(FileSelectorActivity.FILE_TYPE_VIDEO);//视频
   filters.add(FileSelectorActivity.FILE_TYPE_DOC);//文档
   filters.add(FileSelectorActivity.FILE_TYPE_AUDIO);//音频

   FileSelector.Builder builder = new FileSelector.Builder(this);
   Intent intent = builder.setFileRoot("")//初始路径  init file root
               .setIsMultiple(true)//是否多选模式 whether is multiple select
               .setMaxCount(3)//限定文件选择数 max file count
               .setFilters(filters)//筛选文件类型  file filter
               .getIntent();
   startActivityForResult(intent, 100);
```

### 2.在onActivityResult获取结果

```java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == 100 && data != null) {
             ArrayList<String> pathList = data.getStringArrayListExtra(FileSelectorActivity.ACTIVITY_KEY_RESULT_PATHLIST);
             for (String path : pathList) {
                 Log.i(TAG, path);
             }
        }
    }
```

## 说明
项目使用的编译版本是28.0.3，对Android 10的文件系统兼容，但后续开发如果使用29及以上编译版本，应该会出错，这个以后再进行兼容调整
