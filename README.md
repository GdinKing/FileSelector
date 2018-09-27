# 安卓文件选择器

## 预览

![](preview.gif)

## 引入

```groovy
 implementation 'com.king.ui:fileselector:1.0.0'
```
## 编译环境要求

 compileSdkVersion：27+
 
 jdk 1.8+

## 特点

- 支持单选/多选
- 适配6.0权限申请
- 支持传入初始文件夹路径
- 支持筛选文件类型

## 用法：

### 1.通过Intent打开文件选择器

```java
  Intent intent = new Intent(this, FileSelectorActivity.class);
  intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_MULTI, true);  //是否多选模式
  intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_MAX_COUNT, 5);//限定文件选择数，默认为3
  intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_FILEROOT, ""); //初始路径
  intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_FILE_TYPE, ""); //筛选文件类型
//intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_FILE_TYPE, FileSelectorActivity.FILE_TYPE_IMAGE);//只展示图片
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
