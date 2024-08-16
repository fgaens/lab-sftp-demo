package be.codesolutions.sftp_demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class FileService {

    public void handleFile(final File file) {
        log.info("Start handling file {}", file.getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Done handling file {}", file.getName());
    }
}
