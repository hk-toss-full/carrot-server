package com.carrot.daily.domain;

import lombok.Getter;

import java.util.Date;

@Getter
public class Daily {
    private Long id;
    private Long user_id;
    private Long location_id;
    private Long category_id;
    private String title;
    private String content;
    private Date createdAt;

}
