package com.jida.common.task;

import com.jida.common.util.SnowflakeInitiator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MySchedule {

    @Autowired
    private SnowflakeInitiator snowflakeInitiator;

    @Scheduled(initialDelay = 30000, fixedRate = 20000)
    private void snowflakeInitiator_ResetExpire() {
        snowflakeInitiator.resetExpire();
    }

}