package com.example.workflow.restController;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
//import com.example.workflow.service.PublishService;

@RestController
@EnableRabbit
public class TestController {

    //@Autowired
    // PublishService service;


    String msg= "";

    @Autowired
    private ProcessEngine camunda;

  /*  @PostMapping("/api/publish")
    public String publishToRabbit(@RequestBody String task)
    {
        System.out.println("hiii "+task+" message is " + msg);
        //return service.publish(task);
            System.out.println("Task created message received");
           // placeOrder(task);
        return "message published";

    }

   */

    @RabbitListener(queues = "testQueue")
    public void processMessage(byte[] message)
    {
        try
        {
            msg=  new String(message, Charset.defaultCharset());
            System.out.println("message received : "+msg);
            Object obj = new JSONParser().parse(msg);
                        // typecasting obj to JSONObject
            JSONObject jo = (JSONObject) obj;
            String messageName = (String)jo.get("messageName");
            String businessKey= (String)jo.get("businessKey");
            System.out.println("messageName : "+messageName);
            System.out.println("businessKey : "+businessKey);
            if("TaskCreated".equals(messageName)) {
                camunda.getRuntimeService().startProcessInstanceByMessage(messageName,businessKey, Variables.putValue("Task", "1345"));
            }
            else
            {
                Map<String,Object> variables = new HashMap<>();
                variables.put("businessKey", businessKey);
                camunda.getRuntimeService().createMessageCorrelation(messageName).processInstanceBusinessKey(businessKey).setVariable("value", variables)
                    .correlateWithResult();
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }



    public void placeOrder(String task) {
         camunda.getRuntimeService().startProcessInstanceByMessage(task,Variables.putValue("Task", "1345"));
        /*return camunda.getRuntimeService().startProcessInstanceByKey(   "my-project-process",  Variables.putValue("messageName", task));
               */
    }
}
