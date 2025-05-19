package com.basic_shop.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Item> result = itemRepository.findAll();
        model.addAttribute("items", result);
        return "list.html";
    }

    @GetMapping("/write")
    public String list() {
        return "write.html";
    }

    @PostMapping("/add")
    public String addPost(@RequestParam String name, @RequestParam Integer price) {
        itemService.saveItem(name, price);
        return "redirect:/list";
    }

    //위에 처럼 안하고 이렇게 해도 됨
   /*
   @PostMapping("/add")
    public String addPost(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "redirect:/list";
    }
    */
}
