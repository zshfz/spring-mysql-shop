package com.basic_shop.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(String name, Integer price) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("상품 이름은 필수입니다.");
        }
        if (price == null || price < 0) {
            throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
        }

        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        itemRepository.save(item);
    }

    public Item findItem(Long id) {
        Optional<Item> result = itemRepository.findById(id);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("해당 아이템이 없습니다.");
        }
        return result.get();
    }
}
