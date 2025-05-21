package com.basic_shop.shop.item;

import com.basic_shop.shop.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final ItemService itemService;
    private final S3Service s3Service;

    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/list/page/1";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/write")
    public String list() {
        return "write.html";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String addPost(@RequestParam String name, @RequestParam Integer price, @RequestParam String imageUrl) {
        itemService.saveItem(name, price, imageUrl);
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
        model.addAttribute("comment", commentRepository.findAllByParentId(id));
        model.addAttribute("data", itemService.findItem(id));
        return "detail.html";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("data", itemService.findItem(id));
        return "edit.html";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit")
    public String editItem(@RequestParam Long id, @RequestParam String name, @RequestParam Integer price) {
        itemService.saveItem(id, name, price);
        return "redirect:/";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteItem(@RequestParam Long id) {
        itemRepository.deleteById(id);
        return ResponseEntity.status(200).body("삭제 완료");
    }

    @GetMapping("/forbidden")
    public String forbidden(Model model) {
        model.addAttribute("message", "관리자만 상품 등록, 수정, 삭제가 가능합니다.");
        return "forbidden.html";
    }

    @GetMapping("/list/page/{id}")
    public String getListPage(@PathVariable Integer id, Model model) {
        Page<Item> result = itemService.getPage(id);
        model.addAttribute("items", result);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("currentPage", id);
        model.addAttribute("isSearch", false);
        return "list.html";
    }

    @PostMapping("/search")
    public String postSearch(@RequestParam String searchText, Model model) {
        List<Item> result = itemRepository.rawQuery1(searchText);
        model.addAttribute("items", result);
        model.addAttribute("isSearch", true); // 검색 여부 전달
        return "list.html";
    }

    @GetMapping("/presigned-url")
    @ResponseBody
    public String getURL(@RequestParam String filename) {
        return s3Service.createPresignedUrl("test/" + filename);
    }
}
