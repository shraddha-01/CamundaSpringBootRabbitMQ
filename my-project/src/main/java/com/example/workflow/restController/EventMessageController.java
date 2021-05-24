package com.example.workflow.restController;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class EventMessageController {

    @Autowired
    PublishService1 service;

    @PostMapping("/api/publish/msg")
    public String publishToRabbit(@RequestBody String task)
    {
        System.out.println("hiii "+task);
        return service.publish(task);
    }


   /* @Autowired
    RuntimeService runtimeService;

    @Override
    public void onMessage(Message message) {


        try {
        Map<String,Object> variables = new HashMap<>();
        variables.put("value", new String(message.getBody(), "UTF-8"));

        System.out.println("this is my Receive Messages of my RabbitMQ: "+ new String(message.getBody(), "UTF-8"));
        runtimeService.createMessageCorrelation("TaskCreated")
                .setVariable("value", variables)
                .correlateWithResult();
    } catch (
    UnsupportedEncodingException e) {
        e.printStackTrace();
    }
    }

    */
}
