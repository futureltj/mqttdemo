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
  
