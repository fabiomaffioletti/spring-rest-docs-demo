package com.documentation.demo.controller;

import com.documentation.demo.SpringDocumentationTest;
import com.documentation.demo.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
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
    private RestDocumentationResultHandler restDocumentationResultHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testItShouldFindABookWithItsId() throws Exception {
        mockMvc.perform(get("/books/{id}", 100))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(100)))
                .andExpect(jsonPath("title", is("My test book")))
                .andDo(restDocumentationResultHandler.document(
                        pathParameters(
                                parameterWithName("id").description("The book id")
                        ),
                        responseFields(
                                fieldWithPath("id").description("The book id"),
                                fieldWithPath("title").description("The book title")
                        )));
    }

    @Test
    public void testItShouldFindAllBooks() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(restDocumentationResultHandler.document(responseFields(
                        fieldWithPath("[]").description("The books list"),
                        fieldWithPath("[].id").description("The book id"),
                        fieldWithPath("[].title").description("The book title")
                )));
        ;
    }

    @Test
    public void testItShouldSaveABook() throws Exception {
        Book book = Book.builder().title("A new book").build();
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(102)))
                .andExpect(jsonPath("title", is("A new book")))
                .andDo(restDocumentationResultHandler.document(
                        requestFields(
                                fieldWithPath("title").description("The book title")
                        ),
                        responseFields(
                                fieldWithPath("id").description("The book id"),
                                fieldWithPath("title").description("The book title")
                        )));
    }

}
