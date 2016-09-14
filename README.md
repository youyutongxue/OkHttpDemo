# OkHttpDemo

##优点

####1.共享一个socket资源来处理同一个服务器的所有请求
####2.通过连接池来减少请求延迟
####3.使用GZIP来减少数据流量
####4.缓存响应数据来减少重复的网络请求

##GET请求方式：同步阻塞、异步回调

####1.client.newCall(request).execute()是同步的请求方法
####2.client.newCall(request).enqueue(Callback callBack)是异步的请求方法，但是Callback里面的代码是执行在子线程的，因此不能更新UI。

