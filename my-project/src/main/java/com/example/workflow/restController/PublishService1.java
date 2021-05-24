package com.example.workflow.restController;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class PublishService1
{

    @Value("${rabbitmq.eventTopic}")
    private String eventTopic;

    @Value("${rabbitmq.exchangeGetName}")
    private String exchangeGetName;

    @Autowired
    private RabbitTemplate rabitTemplate;

    public String publish(String name)
    {
        try {
            System.out.println("message is getting published message is" + name);

            MessageProperties prop = new MessageProperties();
            Message amqpMessage = new Message(( name).getBytes(), prop);
            rabitTemplate.send(exchangeGetName, eventTopic, amqpMessage);
            return "Message published.";
        }catch(Exception e)
        {
            e.printStackTrace();
            return "Due to some exception, Message is not published.";
        }
    }

 /*  @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String task = (String) delegateExecution.getVariable("messageName");
        publish(task);
    }

  */


}


