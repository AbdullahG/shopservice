package com.mag.backend.api;

import com.mag.backend.dto.ItemCreateDTO;
import com.mag.backend.dto.ItemViewDTO;
import com.mag.backend.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/item")
public class ItemAPI {

    private final ItemService itemService;

    public ItemAPI(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<ItemViewDTO> items(@RequestParam(value = "category", required = false) Long categoryId) {
        return itemService.listItemsByCategory(categoryId);
    }

    @PostMapping
    public ItemViewDTO create(@RequestBody ItemCreateDTO createDTO) {
        return itemService.createItemForCategory(createDTO);
    }
}
