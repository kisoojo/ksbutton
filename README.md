__Step 1.__ Add the JitPack repository to your build file

    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
    

__Step 2.__ Add the dependency    
    
    dependencies {
            implementation 'com.github.kisoojo:ksbutton:1.2'
    }


__How to use__

    <com.zenoation.library.KSButton
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="#00f"
        android:text="Button"
        android:textColor="@color/white"
        app:cornerRadius="10dp" />

