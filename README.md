# EasyDialog
提供自定义dialog style的库，非自定义view，纯净原生!   

### 简介  
原生提供的dialog不符合设计给出的标准，我们一般情况下是去通过自定义view然后塞给dialog。但分析后发现这种方式有点过了，其实我们只需要通过style文件来替换dialog默认的布局为我们自定义的布局就可以了。这样的方式既不用改变dialog的使用方式，也不用写多余代码，只需要更改样式就能满足需求。这其实也是android的设计思想，官方一般都会把属性值暴露出来，我们做SDK的时候也应该遵循这种标准。让显示和逻辑分开，显示的内容又可以交给style定义。

### 添加依赖
1.添加JitPack仓库
  
```  
repositories {
	maven {
		url "https://jitpack.io"
	}
}
```   
```  
dependencies {
		compile 'com.github.tianzhijiexian:还没做好'
}    
```   
### 使用方式   
**在主题中设置默认样式（一次设置就搞定）**  
```XML  
<resources>

    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->

        <item name="dialogTheme">@style/Theme.Dialog</item>
        <item name="alertDialogTheme">@style/Theme.Dialog.Alert</item>
        <item name="alertDialogStyle">@style/AlertDialog</item>
        <item name="progressDialogStyle">@style/ProgressDialog</item>

        <item name="dialogPreferredPadding">@dimen/abc_dialog_padding_material</item>
        <item name="alertDialogCenterButtons">false</item>
        <item name="textColorAlertDialogListItem">@color/abc_primary_text_material_light</item>
        <!-- dialog中listView的divider -->
        <item name="listDividerAlertDialog">@null</item>
        
    </style>

</resources> 
```  
就是这么简单，一切都搞定了！   
我们在代码中直接用对话框就可以了，对话框的使用方式和之前没有任何区别：  
```JAVA  
AlertDialog dialog = new AlertDialog.Builder(this)
                //.setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle("title")
                //.setMessage("message") // 如果是列表就不要设置message了，否则会出问题
                .setPositiveButton("好", null)
                .setNeutralButton("中", null)
                .setNegativeButton("差", null)
                .setItems(new String[]{"android","ios","wp"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();

        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Toast.makeText(MainActivity.this, "dimiss", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


        // ProgressDialog.show(this, "title", "test message");  
```   

### 定制  
我们采用原生的样式肯定不能满足我们的需求，我现在就来看看如何做自己的style吧。  
```XML   
<resources>

    <style name="Theme.Dialog" parent="Theme.AppCompat.Light.Dialog">
        <item name="android:windowFrame">@null</item><!--边框-->
        <item name="android:windowIsFloating">true</item><!--是否浮现在activity之上-->
        <item name="android:windowIsTranslucent">true</item> <!-- 是否透明 -->
        <item name="android:windowNoTitle">true</item><!--除去title-->
        <item name="android:windowContentOverlay">@null</item> <!-- 对话框是否有遮盖 -->
        <item name="android:backgroundDimEnabled">true</item><!-- 对话框出现时背景是否变暗 -->
        
        <item name="android:colorBackground">@color/background_floating_material_light</item><!-- 背景颜色，因为windowBackground中的背景已经写死了，所以这里的设置无效 -->
        <item name="android:colorBackgroundCacheHint">@null</item><!-- 着色缓存（一般不用）-->

        <item name="android:windowTitleStyle">@style/RtlOverlay.DialogWindowTitle.AppCompat</item><!-- 标题的字体样式 -->
        <item name="android:windowTitleBackgroundStyle">@style/Base.DialogWindowTitleBackground.AppCompat</item>
        <item name="android:windowBackground">@drawable/abc_dialog_material_background_light</item><!--对话框背景(重要)-->
        <item name="android:windowAnimationStyle">@style/Animation.AppCompat.Dialog</item><!-- 动画 -->
        <item name="android:windowSoftInputMode">stateUnspecified|adjustPan</item><!-- 输入法弹出时自适应 -->

        <item name="windowActionBar">false</item>
        <item name="windowActionModeOverlay">true</item>

        <item name="listPreferredItemPaddingLeft">24dip</item>
        <item name="listPreferredItemPaddingRight">24dip</item>

        <item name="android:listDivider">@null</item>
    </style>

    <style name="Theme.Dialog.Alert">
        <item name="windowMinWidthMajor">@dimen/abc_dialog_min_width_major</item>
        <item name="windowMinWidthMinor">@dimen/abc_dialog_min_width_minor</item>
    </style>
    
    <style name="AlertDialog" parent="Base.AlertDialog.AppCompat">
        <!-- AlertController.class - line:168 -->
        <!-- dialog的主体布局文件，里面包含了title，message等控件 -->
        <item name="android:layout">@layout/custom_dialog_alert_material</item>
        <!-- dialog中的列表布局文件，其实就是listView -->
        <item name="listLayout">@layout/custom_dialog_list_material</item>
        <!-- dialog中列表的item的布局 -->
        <item name="listItemLayout">@layout/custom_dialog_select_item_material</item>
        <!-- 多选的item的布局 -->
        <item name="multiChoiceItemLayout">@layout/custom_dialog_select_multichoice_material</item>
        <!-- 单选的item的布局 -->
        <item name="singleChoiceItemLayout">@layout/custom_dialog_select_singlechoice_material</item>
    </style>

    <style name="ProgressDialog">
        <!-- 横向的进度条布局 -->
        <item name="horizontalProgressLayout">@layout/custom_dialog_progress_horizontal_material</item>
        <!-- 圆形进度条布局 -->
        <item name="progressLayout">@layout/custom_dialog_progress</item>
    </style>

</resources>


```   
只要把上面的内容复制到自己项目的style中，然后去参考里面layout文件，最后替换自己的layout文件就行。完全是照猫画虎，没有任何技术难度！



### 开发者
![](https://avatars3.githubusercontent.com/u/9552155?v=3&s=460)

Jack Tony: <developer_kale@qq.com>  


### License

    Copyright 2015 Jack Tony

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 