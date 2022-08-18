package com.sgwannabig.smallgift.springboot.domain;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID")
    private Long id;

    @NotNull
    private Long shopId;

    @NotNull
    private Long managerId;

    @NotNull
    private String reviewTitle;
    @NotNull
    private String reviewContents;
    @NotNull
    private String reviewDate;

//    private UploadFile imageFile;
}
