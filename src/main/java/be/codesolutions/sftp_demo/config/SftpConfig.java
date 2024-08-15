package be.codesolutions.sftp_demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.sftp.filters.SftpSimplePatternFileListFilter;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizer;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizingMessageSource;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.scheduling.annotation.Async;

import java.io.File;

@Configuration
@Slf4j
public class SftpConfig {

    @Value("${sftp.server.username}")
    private String username;

    @Value("${sftp.server.password}")
    private String password;

    @Value("${sftp.server.address}")
    private String ipAddress;

    @Value("${sftp.server.port}")
    private int port;


    public DefaultSftpSessionFactory sftpSessionFactory() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();
        factory.setHost(ipAddress);
        factory.setPort(port);
        factory.setAllowUnknownKeys(true);
        factory.setUser(username);
        factory.setPassword(password);
        return factory;
    }

    @Bean(name = "mydefaultsync")
    public SftpInboundFileSynchronizer synchronizer() {
        SftpInboundFileSynchronizer sync = new SftpInboundFileSynchronizer(sftpSessionFactory());
        sync.setDeleteRemoteFiles(true);
        sync.setRemoteDirectory("/upload");
        sync.setFilter(new SftpSimplePatternFileListFilter("*"));
        return sync;
    }

    @Bean(name = "sftpMessageSource")
    @InboundChannelAdapter(channel = "fileuploaded", poller = @Poller(fixedDelay = "3000"))
    public MessageSource<File> sftpMessageSource() {
        SftpInboundFileSynchronizingMessageSource source =
                new SftpInboundFileSynchronizingMessageSource(synchronizer());
        source.setLocalDirectory(new File("tmp/incoming"));
        source.setAutoCreateLocalDirectory(true);
        source.setMaxFetchSize(1);
        return source;
    }

    /*
     * Async incoming file handler
     */
    @Async
    @ServiceActivator(inputChannel = "fileuploaded")
    public void handleIncomingFile(File file) {
        log.info("Received file: {}", file.getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Done handling file: {}", file.getName());
    }
}
