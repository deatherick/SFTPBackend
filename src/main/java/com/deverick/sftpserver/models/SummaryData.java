package com.deverick.sftpserver.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
public class SummaryData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String file;

    @OneToOne
    @JoinColumn(name = "detaildata_id", nullable = false)
    private DetailData detailData;

    @CreationTimestamp
    //@Column(name="insertion_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant insertionDate;
}
