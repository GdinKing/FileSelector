# Android File Selector

## Preview

<img src='preview.gif' width='300' height='520'/>

## Add to your project

AppCompat：
```groovy
 implementation 'com.king.ui:fileselector:1.0.1'
```
AndroidX：
```groovy
 implementation 'com.king.ui:fileselector:1.0.3'
```

## Features

- Single/Multiple selection
- 6.0 permission grant
- Init file path
- File type filter
- Compatibility with AndroidX

## Usage

### 1.Open with Intent

version 1.0.1：

```java
  Intent intent = new Intent(this, FileSelectorActivity.class);
  intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_MULTI, true);  //is multiple selection?
  intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_MAX_COUNT, 5);//max selection,default is 3
  intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_FILEROOT, ""); //init path
  intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_FILE_TYPE, ""); //file type filter，Array，eg:"["video","image","doc"]" or "[FileSelectorActivity.FILE_TYPE_IMAGE,FileSelectorActivity.FILE_TYPE_VIDEO]"
//intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_FILE_TYPE, FileSelectorActivity.FILE_TYPE_IMAGE);//only show image
  startActivityForResult(intent, 100);
```

version 1.0.3：

```java
    //File type filter
   ArrayList<String> filters = new ArrayList<String>();
   filters.add(FileSelectorActivity.FILE_TYPE_IMAGE);//Image
   filters.add(FileSelectorActivity.FILE_TYPE_VIDEO);//Video
   filters.add(FileSelectorActivity.FILE_TYPE_DOC); //Document
   filters.add(FileSelectorActivity.FILE_TYPE_AUDIO);//Audio
   FileSelector.Builder builder = new FileSelector.Builder(this);
   Intent intent = builder.setFileRoot("")// init file root
               .setIsMultiple(true)// whether is multiple select
               .setMaxCount(3)// max file count
               .setFilters(filters)//  file filter
               .getIntent();
   startActivityForResult(intent, 100);
```

### 2.Get the result at `onActivityResult`

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
