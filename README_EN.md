# Android File Selector

## Preview

<img src='preview.gif' width='300' height='520'/>

## Add to your project

```groovy
 implementation 'com.king.ui:fileselector:1.0.1'
```
## Compile Environment

 compileSdkVersion：27

## Feature

- Single/Multiple selection
- 6.0 permission grant
- Init file path
- File type filter

## Usage

### 1.Open with Intent

```java
  Intent intent = new Intent(this, FileSelectorActivity.class);
  intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_MULTI, true);  //is multiple selection?
  intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_MAX_COUNT, 5);//max selection,default is 3
  intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_FILEROOT, ""); //init path
  intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_FILE_TYPE, ""); //file type filter
//intent.putExtra(FileSelectorActivity.ACTIVITY_KEY_FILE_TYPE, FileSelectorActivity.FILE_TYPE_IMAGE);//only show image
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
