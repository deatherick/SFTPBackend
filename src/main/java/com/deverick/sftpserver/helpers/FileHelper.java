package com.deverick.sftpserver.helpers;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Vector;

@Service
public class FileHelper {

    Logger logger = LoggerFactory.getLogger(FileHelper.class);

    public Session getSession(String user, String password){
        String host = "localhost";
        String port = "2222";
        JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession(user, host, Integer.parseInt(port));
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            logger.info("SFTP Session Created");
        } catch (JSchException e) {
            /* Handle session or channel disconnect exception
            This catch block will handle any exceptions related to session or channel
            disconnects that are thrown by the session.connect() or channelSftp.connect() methods */

            logger.error("Could Not Create SFTP Session, Possible reasons: {}", e.getMessage());
            e.printStackTrace();
        }
        return session;
    }

    public ChannelSftp getChannel(Session session){
        ChannelSftp channelSftp = null;
        if (session != null && session.isConnected()){
            try {
                channelSftp = (ChannelSftp) session.openChannel("sftp");
                channelSftp.connect();
                logger.info("SFTP Channel Created");
            } catch (JSchException e) {
                /* Handle SFTP related exception
                This catch block will handle any SFTP-related exceptions that may occur during SFTP
                operations like listing files (channelSftp.ls()) or changing directories (channelSftp.cd()) */

                logger.error("Could Not Create SFTP Channel, Possible reasons: {}", e.getMessage());
                e.printStackTrace();
            }
        }
        return channelSftp;
    }

    public Vector<ChannelSftp.LsEntry> getFilesNamesFromSFTP(ChannelSftp channelSftp, String remoteDirectory, String filePrefixAndExtension){
        Vector<ChannelSftp.LsEntry> files = new Vector<>();
        if(channelSftp != null && channelSftp.isConnected()){
            try {
                // Change to the remote directory
                channelSftp.cd(remoteDirectory);
                // List files in the remote directory
                files = channelSftp.ls(filePrefixAndExtension);
            } catch (SftpException e) {
                logger.error("Could Not Create SFTP Channel, Possible reasons: {}", e.getMessage());
                e.printStackTrace();
            }
        }
        return files;
    }

    public String downloadFileToLocalDirFromSFTP(ChannelSftp channelSftp, String localDirectory, ChannelSftp.LsEntry file){
        if(channelSftp != null && channelSftp.isConnected()){
            try {
                String localFilePath = localDirectory + File.separator + file.getFilename();
                // Download the file from the remote directory to the local directory
                channelSftp.get(file.getFilename(), localFilePath);
                File downloadedFile = new File(localFilePath);
                Date dateModify = new Date(file.getAttrs().getMTime() * 1000L);
                downloadedFile.setLastModified(dateModify.getTime());
                logger.info("File copied from SFTP, Filename: {}", downloadedFile.getName());
                return downloadedFile.getName();
            } catch (SftpException e) {
                logger.error("Could Not Download File from SFTP Server, File Name: {}, Possible reasons: {}",file.getFilename(), e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }

    public String uploadFileFromLocalDirToSFTP(ChannelSftp channelSftp, String remoteDir, File localFile){
        if(channelSftp != null && channelSftp.isConnected()){
            try {
                // Upload the file to the remote directory from the local directory
                channelSftp.put(localFile.getPath(), remoteDir + localFile.getName());
                logger.info("File copied to SFTP, Filename: {}", localFile.getName());
                return localFile.getName();
            } catch (SftpException e) {
                logger.error("Could Not Upload File to SFTP Server, File Name: {}, Possible reasons: {}",localFile.getName(), e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }

    public void disconnectChannelAndSession(ChannelSftp channelSftp,Session session){
        // Disconnect the SFTP channel and session
        if (channelSftp != null && channelSftp.isConnected()) {
            channelSftp.disconnect();
            logger.info("SFTP Channel Disconnected from SFTP Server");
        }
        if (session != null && session.isConnected()) {
            session.disconnect();
            logger.info("SFTP Session Ended from SFTP Server");
        }
    }
}
