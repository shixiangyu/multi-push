## multi-push

An android push library integrated with huawei, xiaomi, oppo, vivo and umeng, Developers can integrate with one key！


## Setup

1.Download


Edit your build.gradle file, add dependencies in your gradle:

 
```

implementation 'com.xiangyu:multi-push:1.0.0'

```


2.Initialize

placing the initialization in your Application

kotlin

```
WDPushManager.initialize(this,object :IPushHandler{
            override fun onNotificationMessageClicked(context: Context, action: String) {
               //To change body of created functions.
            }

            override fun onReceiveToken(context: Context, deviceType: String, token: String) {
               //To change body of created functions.
            }
        } )
        
```


  
java

```
 WDPushManager.INSTANCE.initialize(getApplication(), new IPushHandler() {
            @Override
            public void onNotificationMessageClicked(@NotNull Context context, @NotNull String action) {
                 //To change body of created functions.
            }

            @Override
            public void onReceiveToken(@NotNull Context context, @NotNull String deviceType, @NotNull String token) {
                 //To change body of created functions.
            }
        });
        
```
