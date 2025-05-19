package com.basic_shop.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(String name, Integer price) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        itemRepository.save(item);
    }
}
