package com.fit.usernluappsbapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reports")
public class Report {
    @Id
//    @GeneratedValue
    private Long id;
    private String message;
    private boolean isRead;
    private boolean isStar;
    private Date createDate;
    private String readerName;

    @ManyToOne()
    @JoinColumn(name = "id_reporter")
    private User user;
}
