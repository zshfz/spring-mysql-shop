package com.basic_shop.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        return "list.html";
    }

    @GetMapping("/write")
    public String list() {
        return "write.html";
    }

    @PostMapping("/add")
    public String addPost(@RequestParam String name, @RequestParam Integer price) {
        itemService.saveItem(name, price);
        return "redirect:/";
    }

    //위에 처럼 안하고 이렇게 해도 됨
   /*
   @PostMapping("/add")
    public String addPost(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "redirect:/";
    }
    */

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("data", itemService.findItem(id));
        return "detail.html";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("data", itemService.findItem(id));
        return "edit.html";
    }

    @PostMapping("/edit")
    public String editItem(@RequestParam Long id, @RequestParam String name, @RequestParam Integer price) {
        itemService.saveItem(id, name, price);
        return "redirect:/";
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteItem(@RequestParam Long id) {
        itemRepository.deleteById(id);
        return ResponseEntity.status(200).body("삭제 완료");
    }
}
