var mqtt = require('mqtt')
var opts={
    username:"js",
    password:"123456",
    clientId:"JsClient2",
    rejectUnauthorized:false
};
var client  = mqtt.connect('mqtts://127.0.0.1:8883',opts);
client.on('connect', function () {
    console.log("连接成功");
});
var i=0;
setInterval(function () {
    client.publish("tp1",(i++).toString());
},1000);
