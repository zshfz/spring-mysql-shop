package com.basic_shop.shop.orders;

import com.basic_shop.shop.CustomUser;
import com.basic_shop.shop.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;

    public void saveOrder(String name, Integer price, Integer count, Authentication authentication) {
        Orders orders = new Orders();
        orders.setCount(count);
        orders.setPrice(price);
        orders.setItemName(name);

        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Member member = new Member();
        member.setId(customUser.getId());
        orders.setMember(member);

        ordersRepository.save(orders);
    }
}
