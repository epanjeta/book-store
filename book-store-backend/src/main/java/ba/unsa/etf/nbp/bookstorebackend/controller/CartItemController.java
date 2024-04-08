package ba.unsa.etf.nbp.bookstorebackend.controller;

import ba.unsa.etf.nbp.bookstorebackend.projection.CartItem;
import ba.unsa.etf.nbp.bookstorebackend.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/cartItem")
public class CartItemController {
    @Autowired
    CartItemRepository cartItemRepository;

    @GetMapping("cartForUser")
    public @ResponseBody List<CartItem> getCartForUser(@RequestParam int userId){
        return cartItemRepository.getCartForUser(userId);
    }
}
