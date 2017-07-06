package com.documentation.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by fmaffioletti on 7/6/17.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private Long id;

    private String title;

}
