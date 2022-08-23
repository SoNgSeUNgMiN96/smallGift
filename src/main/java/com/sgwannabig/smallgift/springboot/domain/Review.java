package com.sgwannabig.smallgift.springboot.domain;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID")
    private Long id;

    @NotNull
    private Long managerId;

    @NotNull
    private String reviewTitle;
    @NotNull
    private String reviewContents;
    @NotNull
    private String reviewDate;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "manager_id")
//    private Manager manager;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_Id")
//    private User user;

//    private UploadFile imageFile;
}
