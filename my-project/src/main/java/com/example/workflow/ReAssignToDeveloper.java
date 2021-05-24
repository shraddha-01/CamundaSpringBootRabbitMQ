package com.example.workflow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ReAssignToDeveloper implements JavaDelegate {
    private final Logger LOGGER = LoggerFactory.getLogger(ReAssignToDeveloper.class);

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LOGGER.info("Changes are rejected, Thus ticket is getting reassigned to developer");

    }
}
