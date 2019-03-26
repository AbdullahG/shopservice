package com.mag.backend.service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.mag.backend.dto.ItemCreateDTO;
import com.mag.backend.dto.ItemViewDTO;
import com.mag.backend.entity.Category;
import com.mag.backend.entity.Item;
import com.mag.backend.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryService categoryService;

    public ItemService(ItemRepository itemRepository, CategoryService categoryService) {
        this.itemRepository = itemRepository;
        this.categoryService = categoryService;
    }

    @VisibleForTesting
    ItemViewDTO createItemForCategory(String title, String text, double price, long categoryId) {
        Preconditions.checkArgument(isNotEmpty(title), "title is not present");
        Preconditions.checkArgument(isNotEmpty(text), "text is not present");
        Preconditions.checkArgument(price > 0, "price is invalid");
        Category category = categoryService.getCategory(categoryId);
        Item item = itemRepository.save(new Item(title, text, price, category));
        return ItemViewDTO.of(item);
    }

    @Transactional(readOnly = true)
    public List<ItemViewDTO> listItemsByCategory(Long categoryId) {
        return itemRepository.findByCategoryId(categoryId)
                .map(ItemViewDTO::of)
                .collect(Collectors.toList());
    }

    public ItemViewDTO createItemForCategory(ItemCreateDTO createDTO) {
        return this.createItemForCategory(
                createDTO.getTitle(),
                createDTO.getText(),
                createDTO.getPrice(),
                createDTO.getCategory()
        );
    }
}
