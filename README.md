__Step 1.__ Add the JitPack repository to your build file

    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
    

__Step 2.__ Add the dependency    
    
    dependencies {
            implementation 'com.github.kisoojo:ksbutton:2.0'
    }


__How to use__

    <com.zenoation.library.KSButton
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="#00f"
        android:text="Button"
        android:textColor="@color/white"
        app:isRound="false"
        app:cornerRadius="10dp" />
        
    - isRound : true - oval / false - rectangle
    - cornerRadius : corner radius
    
    

    <com.zenoation.library.KSTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:drawableLeft="@drawable/checkbox_gray"
        android:drawablePadding="5dp"
        android:gravity="center"
        android:text="Text view"
        app:drawableHeight="30dp"
        app:drawableWidth="30dp" />

    <com.zenoation.library.KSEditText
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="@color/white"
        android:drawableLeft="@drawable/checkbox_gray"
        android:drawablePadding="5dp"
        android:text="Edit text"
        app:drawableHeight="30dp"
        app:drawableWidth="30dp" />

    - drawableHeight : drawable height
    - drawableWidth : drawable width
    
    
   
    <com.zenoation.library.KSCheckBox
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Check box"
        app:drawableChecked="@drawable/checkbox_gray"
        app:drawableColor="@color/colorPrimary"
        app:drawableUnchecked="@drawable/checkbox_gray" />
    
    - drawableChecked : drawable while checked
    - drawableUnchecked : drawable while unchecked
    - drawableColor : drawable tint color
