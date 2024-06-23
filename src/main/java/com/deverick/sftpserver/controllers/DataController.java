package com.deverick.sftpserver.controllers;

import com.deverick.sftpserver.dtos.DownloadFileDto;
import com.deverick.sftpserver.dtos.ValidateCodeDto;
import com.deverick.sftpserver.helpers.FileHelper;
import com.deverick.sftpserver.models.SummaryData;
import com.deverick.sftpserver.repositories.SummaryDataRepository;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/data")
public class DataController {

    @Value("${USERNAME}")
    private String pdfSFTPUser;

    @Value("${PASSWORD}")
    private String pdfSFTPPassword;

    @Autowired
    private SummaryDataRepository summaryDataRepository;

    @Autowired
    private FileHelper fileHelper;

    @GetMapping
    public Iterable<SummaryData> data() {
        return summaryDataRepository.findAll();
    }

    @PostMapping("/downloadFile")
    public ResponseEntity<?> downloadFile(@RequestBody DownloadFileDto body) throws SftpException {
        Session session = this.fileHelper.getSession(pdfSFTPUser, pdfSFTPPassword);
        ChannelSftp channel = this.fileHelper.getChannel(session);

        InputStream stream = channel.get(body.getFileCode());

        Resource resource = new InputStreamResource(stream);

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
            .body(resource);
    }

}
