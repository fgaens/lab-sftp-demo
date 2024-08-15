package be.codesolutions.sftp_demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SomeService {

    /*
     * The sole purpose of these methods is to demonstrate the asynchronous
     * functionality of the incoming file handler.
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    public void scheduledLogger() {
        log.info("Logline from Scheduled Logger");
    }
}
