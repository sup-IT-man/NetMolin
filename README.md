# NetMolin
项目中需要对网络状态判断以及相关处理操作

在项目中的依赖：

  根目录的build.gradle中：
  
  allprojects {
  
    repositories {
	
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}

在module的build.gradle中：

dependencies {  

    implementation 'com.github.sup-IT-man:NetMolin:1.0'
	
}
用法步骤:
  如果使用默认的设置，那么只需要在Activity中这样设置:
  NetMolin.getInstance().register(this);
  如果需要用到自己的配置信息，那么可以这么使用：
  
  NetworkConfiguration configuration = new NetworkConfiguration
                .Builder()
		
                .setIsToast(true)
		
                .build();
		
  NetMolin.getInstance().setConfiguration(configuration).register(this);
  
  如果是默认的设置，那么在没有网路的情况下会在当前的界面的头部添加一个提示无网络的Tip。
	
