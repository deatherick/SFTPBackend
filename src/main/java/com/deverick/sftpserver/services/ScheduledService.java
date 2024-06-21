package com.deverick.sftpserver.services;

import com.deverick.sftpserver.dtos.RootObject;
import com.deverick.sftpserver.dtos.User;
import com.deverick.sftpserver.dtos.UserForCSV;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.deverick.sftpserver.helpers.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class ScheduledService {

    @Value("${USERNAME}")
    private String pdfSFTPUser;

    @Value("${PASSWORD}")
    private String pdfSFTPPassword;

    @Value("${REMOTE_DIR}")
    private String remoteDirectory;

    @Value("${files.dir}")
    private String localDirectory;

    @Autowired
    private FileHelper fileHelper;

    public void dummyMethod() {
        System.out.println("Scheduled task executed at: " + new Date());

        //Download dummy data from endpoint
        String uri = "https://dummyjson.com/users";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMDD");
        String date = dateFormat.format(new Date());
        String filename = String.format("data_[%s].json", date);

        //Open FTP Session and Channel
        Session session = this.fileHelper.getSession(pdfSFTPUser, pdfSFTPPassword);
        ChannelSftp channel = this.fileHelper.getChannel(session);
        File JsonFile;
        File CsvFile;

        try {
            FileWriter writer = new FileWriter(localDirectory + filename);
            assert result != null;
            writer.write(result);
            writer.flush();
            writer.close();

            //Upload JSON file to FTP
            JsonFile = new File(localDirectory + filename);
            this.fileHelper.uploadFileFromLocalDirToSFTP(channel, remoteDirectory, JsonFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            String csvFileName = String.format("ETL_[%s].csv", date);
            String csvSummaryFileName = String.format("summary_[%s].csv", date);
            CsvMapper csvMapper = new CsvMapper();
            CsvSchema csvSchema = csvMapper
              .schemaFor(UserForCSV.class)
              .withHeader();

            csvMapper.addMixIn(User.class, UserForCSV.class);

            RootObject root = new ObjectMapper()
                .readValue(JsonFile, RootObject.class);

            CsvFile = new File(localDirectory + csvFileName);

            csvMapper.writerFor(User[].class)
                .with(csvSchema)
                .writeValue(CsvFile, root.users);

            // Write Summary
            Writer writer = new FileWriter(localDirectory + csvSummaryFileName);

            CSVWriter csvWriter = new CSVWriter(writer);
            csvWriter.writeNext(new String[]{"registre", String.valueOf(root.users.length)});
            csvWriter.writeNext(new String[]{"gender", "total"});
            csvWriter.writeNext(new String[]{
                    "male",
                    String.valueOf(Arrays.stream(root.users).toList().stream()
                            .filter(t -> t.getGender().equals("male"))
                            .toList().size())
            });
            csvWriter.writeNext(new String[]{
                    "female",
                    String.valueOf(Arrays.stream(root.users).toList().stream()
                            .filter(t -> t.getGender().equals("female"))
                            .toList().size())
            });
            csvWriter.writeNext(new String[]{
                    "other",
                    String.valueOf(Arrays.stream(root.users).toList().stream()
                            .filter(t -> t.getGender().equals("other"))
                            .toList().size())
            });

            csvWriter.writeNext(new String[]{"age", "male", "female", "other"});
            csvWriter.writeNext(new String[]{
                    "00-10",
                     String.valueOf(Arrays.stream(root.users).toList().stream()
                            .filter(t -> t.getGender().equals("male") && t.getAge() > 0 && t.getAge() <= 10)
                            .toList().size()),
                    String.valueOf(Arrays.stream(root.users).toList().stream()
                            .filter(t -> t.getGender().equals("female") && t.getAge() > 0 && t.getAge() <= 10)
                            .toList().size()),
                    String.valueOf(Arrays.stream(root.users).toList().stream()
                            .filter(t -> t.getGender().equals("other") && t.getAge() > 0 && t.getAge() <= 10)
                            .toList().size()),
            });
            csvWriter.writeNext(new String[]{
                    "11-20",
                     String.valueOf(Arrays.stream(root.users).toList().stream()
                            .filter(t -> t.getGender().equals("male") && t.getAge() > 11 && t.getAge() <= 20)
                            .toList().size()),
                    String.valueOf(Arrays.stream(root.users).toList().stream()
                            .filter(t -> t.getGender().equals("female") && t.getAge() > 11 && t.getAge() <= 20)
                            .toList().size()),
                    String.valueOf(Arrays.stream(root.users).toList().stream()
                            .filter(t -> t.getGender().equals("other") && t.getAge() > 11 && t.getAge() <= 20)
                            .toList().size()),
            });
            csvWriter.writeNext(new String[]{
                    "21-30",
                     String.valueOf(Arrays.stream(root.users).toList().stream()
                            .filter(t -> t.getGender().equals("male") && t.getAge() > 21 && t.getAge() <= 30)
                            .toList().size()),
                    String.valueOf(Arrays.stream(root.users).toList().stream()
                            .filter(t -> t.getGender().equals("female") && t.getAge() > 21 && t.getAge() <= 30)
                            .toList().size()),
                    String.valueOf(Arrays.stream(root.users).toList().stream()
                            .filter(t -> t.getGender().equals("other") && t.getAge() > 21 && t.getAge() <= 30)
                            .toList().size()),
            });

            List<User> uniqueCities = Arrays.stream(root.users).toList().stream()
             .filter(distinctByKey(p -> p.getAddress().getCity()))
             .toList();

            csvWriter.writeNext(new String[]{"City", "male", "female", "other"});
            for (User user : uniqueCities) {
                String city = user.getAddress().getCity();
                csvWriter.writeNext(new String[]{
                        city,
                        String.valueOf(Arrays.stream(root.users).toList().stream()
                            .filter(t -> t.getGender().equals("male") && t.getAddress().getCity().equals(city))
                            .toList().size()),
                        String.valueOf(Arrays.stream(root.users).toList().stream()
                            .filter(t -> t.getGender().equals("female") && t.getAddress().getCity().equals(city))
                            .toList().size()),
                        String.valueOf(Arrays.stream(root.users).toList().stream()
                            .filter(t -> t.getGender().equals("other") && t.getAddress().getCity().equals(city))
                            .toList().size()),
                });
            }

            csvWriter.flush();
            csvWriter.close();

            //Upload CSV file to FTP
            this.fileHelper.uploadFileFromLocalDirToSFTP(channel, remoteDirectory, CsvFile);
            this.fileHelper.uploadFileFromLocalDirToSFTP(channel, remoteDirectory, new File(localDirectory + csvSummaryFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.fileHelper.disconnectChannelAndSession(channel, session);
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
