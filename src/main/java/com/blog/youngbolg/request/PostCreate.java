package com.blog.youngbolg.request;

import lombok.Data;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
public class PostCreate {

    private String title;
    private String content;

}
