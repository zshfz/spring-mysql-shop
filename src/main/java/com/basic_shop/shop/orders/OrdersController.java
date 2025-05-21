package com.basic_shop.shop.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrdersController {
    private final OrdersService ordersService;
    private final OrdersRepository ordersRepository;

    @PostMapping("/order")
    public String postOrder(@RequestParam String name, @RequestParam Integer price, @RequestParam Integer count, Authentication authentication) {
        ordersService.saveOrder(name, price, count, authentication);
        return "pay.html";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/order/all")
    public String getOrderAll(Model model) {
        List<Orders> result = ordersRepository.findAll();
        model.addAttribute("orders", result);
        return "order-list.html";
    }
}
