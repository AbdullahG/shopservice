package com.mag.backend.api;

import com.mag.backend.dto.CategoryViewDTO;
import com.mag.backend.dto.ItemCreateDTO;
import com.mag.backend.service.CategoryService;
import com.mag.backend.service.ItemService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
public class ItemAPIIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;

    @Test
    public void createItemForCategory() throws Exception {
        categoryService.create("category1");

        mockMvc.perform(
                post("/api/item")
                        .content("{\"title\":\"123k1k23\",\"text\":\"k12k3k12k3\",\"price\":\"123123\",\"category\":1}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().json("{\"title\":\"123k1k23\",\"text\":\"k12k3k12k3\",\"price\":123123.0,\"categoryId\":1}")
        );
    }

    @Test
    public void listItems() throws Exception {
        CategoryViewDTO category1 = categoryService.create("category1");
        CategoryViewDTO category2 = categoryService.create("category2");

        itemService.createItemForCategory(new ItemCreateDTO("title1", "text1", 23.2, category1.getId()));
        itemService.createItemForCategory(new ItemCreateDTO("title2", "text2", 1.9, category1.getId()));
        itemService.createItemForCategory(new ItemCreateDTO("title3", "text3", 4.2, category2.getId()));

        mockMvc.perform(
                get("/api/item")
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().json(
                        "[\n" +
                                "  {\n" +
                                "    \"title\": \"title1\",\n" +
                                "    \"text\": \"text1\",\n" +
                                "    \"price\": 23.2,\n" +
                                "    \"categoryId\": 1\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"title\": \"title2\",\n" +
                                "    \"text\": \"text2\",\n" +
                                "    \"price\": 1.9,\n" +
                                "    \"categoryId\": 1\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"title\": \"title3\",\n" +
                                "    \"text\": \"text3\",\n" +
                                "    \"price\": 4.2,\n" +
                                "    \"categoryId\": 2\n" +
                                "  }\n" +
                                "]"
                )
        );
    }

    @Test
    public void listItemsByCategory() throws Exception {
        CategoryViewDTO category1 = categoryService.create("category1");
        CategoryViewDTO category2 = categoryService.create("category2");

        itemService.createItemForCategory(new ItemCreateDTO("title1", "text1", 23.2, category1.getId()));
        itemService.createItemForCategory(new ItemCreateDTO("title2", "text2", 1.9, category1.getId()));
        itemService.createItemForCategory(new ItemCreateDTO("title3", "text3", 4.2, category2.getId()));

        mockMvc.perform(
                get("/api/item?category=2")
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().json(
                        "[\n" +
                                "  {\n" +
                                "    \"title\": \"title3\",\n" +
                                "    \"text\": \"text3\",\n" +
                                "    \"price\": 4.2,\n" +
                                "    \"categoryId\": 2\n" +
                                "  }\n" +
                                "]"
                )
        );
    }
}
