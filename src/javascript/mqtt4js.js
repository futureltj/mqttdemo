var mqtt = require('mqtt')
var fs=require('fs');
var opts={
    username:"js",
    password:"123456",
    clientId:"JsClient1",
    rejectUnauthorized:false
};
var client  = mqtt.connect('mqtts://127.0.0.1:8883',opts);
client.on('connect', function () {
    client.subscribe('tp1');
    client.publish("tp1","I am JavaScript");
});

client.on('message', function (topic, message) {
    // message is Buffer
    console.log(topic,message.toString());
    //client.end()
});

