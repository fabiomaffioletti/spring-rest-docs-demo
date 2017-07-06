package com.documentation.demo.controller;

import com.documentation.demo.SpringDocumentationTest;
import com.documentation.demo.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by fmaffioletti on 7/6/17.
 */
@RunWith(SpringRunner.class)
@SpringDocumentationTest
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testItShouldFindABookWithItsId() throws Exception {
        mockMvc.perform(get("/books/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(100)))
                .andExpect(jsonPath("title", is("My test book")));
    }

    @Test
    public void testItShouldFindAllBooks() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testItShouldSaveABook() throws Exception {
        Book book = Book.builder().title("A new book").build();
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(102)))
                .andExpect(jsonPath("title", is("A new book")));
    }

}
