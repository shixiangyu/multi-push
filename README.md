## multi-push

An android push library integrated with huawei, xiaomi, oppo, vivo and umeng, Developers can integrate with one key！

这是一个集成了华为，小米，oppo，vivo等厂商，umeng第三方的消息推送库，开发者可一键集成，稳定，方便，快捷，该库优先厂商通道，对于非此四家的手机，默认走umeng通道

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
