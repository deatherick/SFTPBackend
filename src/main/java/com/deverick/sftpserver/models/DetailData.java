package com.deverick.sftpserver.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class DetailData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String file;

    @CreationTimestamp
    //@Column(name="insertion_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant insertionDate;
}
