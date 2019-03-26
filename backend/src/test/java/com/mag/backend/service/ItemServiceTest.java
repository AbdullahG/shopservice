package com.mag.backend.service;

import com.mag.backend.dto.ItemViewDTO;
import com.mag.backend.entity.Category;
import com.mag.backend.entity.Item;
import com.mag.backend.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
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
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ItemService.class)
@RunWith(SpringRunner.class)
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;
    @MockBean
    private ItemRepository itemRepository;
    @MockBean
    private CategoryService categoryService;

    @Captor
    private ArgumentCaptor<Item> itemArgumentCaptor;

    private Category category;

    @Before
    public void setUp() throws Exception {
        category = new Category("category1");
        category.setId(123L);
        when(categoryService.getCategory(123L)).thenReturn(category);
    }

    @Test
    public void createItemForCategory() throws Exception {
        String itemTitle = "item title";
        String itemText = "sample text for item";
        double price = 44.99;
        when(itemRepository.save(any(Item.class))).thenReturn(new Item(itemTitle, itemText, price, category));

        ItemViewDTO viewDTO = itemService.createItemForCategory(itemTitle, itemText, price, 123L);
        verify(itemRepository, times(1)).save(itemArgumentCaptor.capture());

        Item item = itemArgumentCaptor.getValue();
        assertThat(viewDTO.getTitle()).isEqualTo(itemTitle).isEqualTo(item.getTitle());
        assertThat(viewDTO.getText()).isEqualTo(itemText);
        assertThat(viewDTO.getPrice()).isEqualTo(price);
        assertThat(viewDTO.getCategoryId()).isEqualTo(123L).isEqualByComparingTo(item.getCategory().getId());
    }

    @Test
    public void listAllItemsByCategory() throws Exception {
        when(itemRepository.findByCategoryId(category.getId())).thenReturn(randomItems().limit(12));
        when(categoryService.getCategory(123L)).thenReturn(category);

        List<ItemViewDTO> itemViewDTOS = itemService.listItemsByCategory(123L);
        Assertions.assertThat(itemViewDTOS.size()).isEqualTo(12);
        itemViewDTOS.forEach(item -> assertThat(item.getCategoryId()).isEqualTo(category.getId()));
    }

    private Stream<Item> randomItems() {
        return IntStream.iterate(0, IntUnaryOperator.identity()).boxed()
                .map(i -> new Item(randomAlphabetic(20), randomAlphabetic(20), 33.33, category));
    }
}
