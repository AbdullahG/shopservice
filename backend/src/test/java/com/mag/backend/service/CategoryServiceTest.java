package com.mag.backend.service;

import com.mag.backend.dto.CategoryViewDTO;
import com.mag.backend.entity.Category;
import com.mag.backend.repository.CategoryRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CategoryService.class)
@RunWith(SpringRunner.class)
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;
    @MockBean
    private CategoryRepository categoryRepository;
    @Captor
    private ArgumentCaptor<Category> categoryArgumentCaptor;

    @Test
    public void createCategory() {
        String title = "example title";
        when(categoryRepository.save(any())).thenReturn(new Category(title));

        CategoryViewDTO viewDTO = categoryService.create(title);
        verify(categoryRepository, times(1)).save(categoryArgumentCaptor.capture());
        assertThat(viewDTO.getTitle()).isEqualTo(title);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionIfCategoryTitleIsNotPresent() {
        categoryService.create("");
    }

    @Test
    public void listAllCategories() {
        when(categoryRepository.findAll()).thenReturn(randomCategories().limit(5).collect(Collectors.toList()));

        List<CategoryViewDTO> categories = categoryService.list();
        verify(categoryRepository, times(1)).findAll();
        assertThat(categories.size()).isEqualTo(5);
    }

    private Stream<Category> randomCategories() {
        return IntStream.iterate(0, IntUnaryOperator.identity()).boxed()
                .map(i -> new Category(RandomStringUtils.randomAlphabetic(20)));
    }
}
