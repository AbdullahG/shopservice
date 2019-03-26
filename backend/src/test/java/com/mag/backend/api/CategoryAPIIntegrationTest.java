package com.mag.backend.api;

import com.mag.backend.dto.CategoryViewDTO;
import com.mag.backend.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
public class CategoryAPIIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void createCategory() throws Exception {
        mockMvc.perform(
                post("/api/category")
                        .content("{\"title\":\"a category title\"}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().json("{id: 1, title: 'a category title', itemCount: 0}")
        );

        List<CategoryViewDTO> categories = categoryService.list();

        assertThat(categories.size()).isEqualTo(1);
        assertThat(categories.get(0).getTitle()).isEqualTo("a category title");
    }

    @Test
    public void listAllCategories() throws Exception {

        categoryService.create("category1");
        categoryService.create("category2");

        mockMvc.perform(
                get("/api/category")
        ).andExpect(
                status().isOk()
        ).andExpect(
                content()
                        .json("[{id: 1, title: 'category1', itemCount: 0},{id: 2, title: 'category2', itemCount: 0}]")
        );
    }
}
