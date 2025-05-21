package com.basic_shop.shop.member;

import com.basic_shop.shop.CustomUser;
import com.basic_shop.shop.orders.Orders;
import com.basic_shop.shop.orders.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final OrdersRepository ordersRepository;

    @GetMapping("/register")
    public String register() {
        return "register.html";
    }

    @PostMapping("/register")
    public String addMember(@RequestParam String displayName, @RequestParam String username, @RequestParam String password) {
        memberService.saveMember(displayName, username, password);
        return "login.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/my-page")
    public String myPage(Authentication authentication, Model model) {
        if (authentication.isAuthenticated()) {
            model.addAttribute("orders", memberService.findPersonalOrders(authentication));
            return "my-page.html";
        } else {
            return "login.html";
        }
    }

    //dto 사용 예시
    /*
    @GetMapping("/user/{id}")
    @ResponseBody
    public MemberDto getUserInfo(@PathVariable Long id) {
        return memberService.getUser(id);
    }
    */

}
