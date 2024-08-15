# SFTP Demo Application

This is a demo application that leverages Spring Integration SFTP to synchronize files from an FTP server. The incoming file handler is configured to operate asynchronously, ensuring efficient file processing.

## Getting Started

### Prerequisites

Before running the application, you'll need to set up an SFTP server and configure the application accordingly.

#### Start the SFTP Server

To start the SFTP server, use the following Docker command. Replace `<host-dir>` with the appropriate directory on your host machine where you want to sync the files.

```bash
docker run \
    -v <host-dir>/upload:/home/foo/upload \
    -p 2222:22 -d atmoz/sftp \
    foo:bar:1001
```

#### Configure the SFTP Server Address

In the application.yml file, update the sftp.server.address property with your local IP address to ensure proper connectivity.

### Transferring Files

To trigger the file synchronization, simply add files to the <host-dir>/upload directory. The application will automatically detect and sync these files from the SFTP server.

## Running the Application

Once you have completed the prerequisite steps, you can start the application using the following command:

```bash
./gradlew :bootRun
