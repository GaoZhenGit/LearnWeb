# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-optimizationpasses 5          # 指定代码的压缩级别
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontpreverify           # 混淆时是否做预校验
-verbose                # 混淆时是否记录日志

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
#如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment
-keep class * extends java.lang.annotation.Annotation { *; }
-keepclassmembers class ** {
    public void on*Event(...);
}

-keep public class com.google.gson.**{*;}
-keep public class com.android.volley.**{*;}

-keep public class com.androidquery.**{*;}
#-keepnames public class com.androidquery.**{*;}

-keep public class com.daimajia.**{*;}
#-keepnames public class com.daimajia.**{*;}
-keep public class com.nineoldandroids.**{*;}
-keep public class com.nostra13.**{*;}
-keep public class com.ibm.mqtt.**{*;}
-keep public class com.lidroid.**{*;}
-keep public class com.ibm.gz.learn_cloud.entire.**{*;}