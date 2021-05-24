package com.example.workflow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogATicket implements JavaDelegate {

    private final Logger LOGGER = LoggerFactory.getLogger(LogATicket.class);
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        //delegateExecution.setVariable("approved", Boolean.TRUE);
        
        LOGGER.info("Task created");
    }
}
