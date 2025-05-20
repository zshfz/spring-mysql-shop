package com.basic_shop.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(String name, Integer price) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("상품 이름은 필수입니다.");
        }
        if (name.length() > 20) {
            throw new IllegalArgumentException("상품 이름은 20자 이내여야 합니다.");
        }
        if (price == null || price < 0) {
            throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
        }
        if (price > 1_000_000) {
            throw new IllegalArgumentException("가격은 1,000,000원을 초과할 수 없습니다.");
        }

        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        itemRepository.save(item);
    }

    public void saveItem(Long id, String name, Integer price) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("상품 이름은 필수입니다.");
        }
        if (name.length() > 20) {
            throw new IllegalArgumentException("상품 이름은 20자 이내여야 합니다.");
        }
        if (price == null || price < 0) {
            throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
        }
        if (price > 1_000_000) {
            throw new IllegalArgumentException("가격은 1,000,000원을 초과할 수 없습니다.");
        }

        Item item = new Item();
        item.setId(id);
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

    public Page getPage(Integer id) {
        return itemRepository.findPageBy(PageRequest.of(id - 1, 3));
    }
}
