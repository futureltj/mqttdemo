# mqttdemo
基于mqtt/mqtts协议的客户端Demo<br>
展示了基于ssl的mqtt客户端的订阅和发布demo
## java
java依赖jar包在lib\jar中,并且想要支持mqtts协议需要:<br>
  将服务器mqttd\etc\cert\cacert.pem 重命名为cacert.crt<br>
  加入JDK证书信任，jdk1.8.0_121\jre\lib\security目录下运行命令：<br>
  `keytool -import -alias cacert -keystore cacerts -file 盘:目录/cacert.crt`<br>
## node.js
node.js运行需要下载mqtt模块，通过`npm install mqtt`下载，在本项目`lib`中的模块为win_64环境下的，未必适合你的操作系统。
## golang
golang运行依赖的包都放在了lib\go中，你也可以通过`go get -u github.com/yosssi/gmq/...`下载<br>
## webjs
前端的运行，依赖之前node中使用的mqtt模块中封好的一个js文件，已经放在了webjs目录下，可以直接使用，注意前端的写法和服务端略有不同
## C
c语言版本的最为复杂，因为不同的cpu构架下的so库需要自己编译，需要自行下载源码`https://github.com/eclipse/paho.mqtt.c`，然后`make & make install`。  
make之前需要确保系统安装了Openssl，上述步骤成功后会产生四个so文件到`/usr/local/lib`以及三个h文件到`/usr/local/include`，这两个路径是gcc会自动寻找的路径  
将本项目src中的`main.c`拷贝，然后`gcc main.c -lpaho-mqtt3cs -o mm`编译出可执行文件mm，`./mm`执行。  
  
## mqtt协议的注意事项
### 1 自动重连问题
这个问题在js中有了不错的解决方案，自动封装了重新连接，`nodejs`端的重新连接响应迅速。前端的反应要慢一些需要几秒钟时间才能重新连接。<br>
而对于Go的api中没有自动重连的设置，并且没有将断开的`handler`暴露出来。<br>
至于java的api则提供了自动重连设置，但需要注意，想要重连并保持原来的订阅需要重新订阅，因而不要用`Test.java`中的代码，可以参考`ReconnectDemo.java`<br>
另外java的api中规定了重连尝试间隔长达2分钟，且新版本中无法更改这个时长。
### 2 网页端免ssl证书认证
在webjs中请求wss网址的时候，虽然和服务端一样设置了`rejectUnauthorized:false`，但是在前端该参数不会生效。因为不能在前端阻止ssl证书的认证。
  
