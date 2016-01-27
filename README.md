#Xitu
本项目仅仅用户学习目的,在 Android Studio 下使用`单元测试`,以及使用`开源库`进行快速开发
## 截图
![unlogin](https://github.com/lulee007/Xitu/raw/master/screenshot/xt_unlogin_bro.gif) ![register](https://github.com/lulee007/Xitu/raw/master/screenshot/xt_register.gif)
![login](https://github.com/lulee007/Xitu/raw/master/screenshot/xt_login.gif) ![login_bro](https://github.com/lulee007/Xitu/raw/master/screenshot/xt_login_bro.gif)
## 单元测试
* junit4
使用到最多,配合`retrofit`和`rajava`进行restapi请求测试
* mockito
* hamcrest

## 项目依赖

项目名称 | 项目信息
------- | -------
[android.support.*](https://developer.android.com/tools/support-library/index.html) | Android Support Library
[OkHttp](http://square.github.io/okhttp/) | An HTTP+HTTP/2 client for Android and Java applications.
[retrofit](https://github.com/square/retrofit) | 网络请求组件
[RxJava](https://github.com/ReactiveX/RxJava) | RxJava 是由 Netflix 开发的响应式扩展（Reactive Extensions）的Java实现
[RxAndroid](https://github.com/ReactiveX/RxAndroid) | RxAndroid 是 RxJava 的一个针对 Android 平台的扩展
[logger](https://github.com/orhanobut/logger) | 一个简单、漂亮、功能强大的 Android 日志程序
[LeakCanary](https://github.com/square/leakcanary) | Android 内存泄漏检测工具
[prefser](https://github.com/pwittchen/prefser) | Android sp存储,包括基本类型和自定义对象.Shared Preferences Helper
[sweet-alert-dialog](https://github.com/pedant/sweet-alert-dialog) | A beautiful and clever alert dialog
[Localify](https://github.com/polok/localify) | 从文件中加载内容的Android类库
[glide](https://github.com/bumptech/glide)|专注于平滑滚动,为Android设计的图片加载与缓存的类库,An image loading and caching library for Android focused on smooth scrolling
[ultimaterecyclerview](https://github.com/cymcsg/UltimateRecyclerView) | 下拉刷新,上拉加载更多,自定义列表头的 列表类库.A RecyclerView(advanced and flexible version of ListView in Android) with refreshing,loading more,animation and many other features.
[materialdrawer](https://github.com/mikepenz/MaterialDrawer) | 抽屉导航,集成简单,可扩展性强.The flexible, easy to use, all in one drawer library for your Android project. 
[sweetalert](https://github.com/pedant/sweet-alert-dialog) | 漂亮,简介,易用的,还有动画,提供常用的,成功,警告,错误,进度 样式弹出框.SweetAlert for Android, a beautiful and clever alert dialog
[material-dialogs](https://github.com/afollestad/material-dialogs) | 漂亮,易用,可指定,材料化,能够实现输入,多选,列表等功能A beautiful, easy-to-use, and customizable dialogs API, enabling you to use Material designed dialogs down to API 8.
[packer-ng-plugin](https://github.com/mcxiaoke/packer-ng-plugin)|下一代Android打包工具，1000个渠道包只需要5秒

## 配置
在`app/build.gradle`中使用到的:
```
properties.load(project.rootProject.file('local.properties').newDataInputStream())
def AVOSCloud_App_Id = properties.getProperty('AVOSCloud.AppId')
def AVOSCloud_App_Key = properties.getProperty('AVOSCloud.AppKey')
def BUGHD_ProjectId = properties.getProperty('bughd.projectId')
def BUGHD_ApiToken = properties.getProperty('bughd.apiToken')
def BUGHD_GeneralToken = properties.getProperty('bughd.generalToken')
def FIR_ApiToken = properties.getProperty('fir.apiToken')
def UMENG_App_Key = properties.getProperty('umeng.appKey')
def _keyPassword = properties.getProperty('signConfig.keyPassword')
def _storePassword = properties.getProperty('signConfig.storePassword')
```

这些来自`local.properties`:
```
AVOSCloud.AppKey=xxxxxx
bughd.generalToken=xxxxx
fir.apiToken=xxxx
umeng.appKey=xxxx
signConfig.storePassword=xxx
bughd.projectId=xxxx
AVOSCloud.AppId=xxxx
sdk.dir=/Users/xxx/Library/Android/sdk
signConfig.keyPassword=xxx
bughd.apiToken=xxxx
```
xt_keystore.jks 放在项目根目录下