package main

import (
	"crypto/tls"
	"fmt"
	"github.com/yosssi/gmq/mqtt"
	"github.com/yosssi/gmq/mqtt/client"
	"os"
	"os/signal"
)

func main() {
	// Set up channel on which to send signal notifications.
	sigc := make(chan os.Signal, 1)
	signal.Notify(sigc, os.Interrupt, os.Kill)

	// Create an MQTT Client.
	cli := client.New(&client.Options{
		// Define the processing of the error handler.
		ErrorHandler: func(err error) {
			fmt.Println(err)
		},
	})

	// Terminate the Client.
	defer cli.Terminate()

	// Connect to the MQTT Server.
	err := cli.Connect(&client.ConnectOptions{
		CleanSession: true,
		Network:      "tcp",
		Address:      "127.0.0.1:8883",
		ClientID:     []byte("GoClient1"),
		UserName:     []byte("go"),
		Password:     []byte("123456"),
		TLSConfig:    &tls.Config{InsecureSkipVerify: true},
	})
	if err != nil {
		panic(err)
	}

	// Subscribe to topics.
	err = cli.Subscribe(&client.SubscribeOptions{
		SubReqs: []*client.SubReq{
			&client.SubReq{
				TopicFilter: []byte("tp1"),
				QoS:         mqtt.QoS2,
				// Define the processing of the message handler.
				Handler: func(topicName, message []byte) {
					fmt.Println(string(topicName), string(message))
				},
			},
		},
	})
	if err != nil {
		panic(err)
	}

	// Publish a message.
	err = cli.Publish(&client.PublishOptions{
		QoS:       mqtt.QoS2,
		TopicName: []byte("tp1"),
		Message:   []byte("I am Go"),
	})
	if err != nil {
		panic(err)
	}

	// Unsubscribe from topics.
	// err = cli.Unsubscribe(&client.UnsubscribeOptions{
	// 	TopicFilters: [][]byte{
	// 		[]byte("foo"),
	// 	},
	// })
	// if err != nil {
	// 	panic(err)
	// }

	// Wait for receiving a signal.
	<-sigc

	// Disconnect the Network Connection.
	if err := cli.Disconnect(); err != nil {
		panic(err)
	}
}
