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
