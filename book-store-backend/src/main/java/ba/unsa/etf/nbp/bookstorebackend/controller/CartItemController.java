package ba.unsa.etf.nbp.bookstorebackend.controller;

import ba.unsa.etf.nbp.bookstorebackend.constants.CartItemForm;
import ba.unsa.etf.nbp.bookstorebackend.projection.CartItem;
import ba.unsa.etf.nbp.bookstorebackend.repository.AuthenticationRepository;
import ba.unsa.etf.nbp.bookstorebackend.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/cartItem")
public class CartItemController {
    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    protected AuthenticationRepository authenticationRepository;

    @GetMapping("cartForUser")
    public @ResponseBody List<CartItem> getCartForUser(@RequestHeader(name = "Authorization", required = false) String authorizationHeader, @RequestParam int userId){
        if(authenticationRepository.isUserRole(authorizationHeader))
            return cartItemRepository.getCartForUser(userId);
        else return null;
    }

    @PostMapping("/addToCart")
    public @ResponseBody int addToCart(@RequestHeader(name = "Authorization", required = false) String authorizationHeader, @RequestBody CartItemForm cartItemForm){
        if(authenticationRepository.isUserRole(authorizationHeader))
            return cartItemRepository.addToCart(cartItemForm.getUserId(), cartItemForm.getBookId(), cartItemForm.getQuantity());
        else return -1;
    }

    @DeleteMapping("/deleteFromCart")
    public @ResponseBody HttpStatus deleteFromCart(@RequestHeader(name = "Authorization", required = false) String authorizationHeader, @RequestBody CartItemForm cartItemForm){
        if(authenticationRepository.isUserRole(authorizationHeader))
            return cartItemRepository.deleteFromCart(cartItemForm);
        return HttpStatus.UNAUTHORIZED;
    }

    @DeleteMapping("/emptyCart")
    public @ResponseBody HttpStatus deleteFromCart(@RequestHeader(name = "Authorization", required = false) String authorizationHeader, @RequestParam int userId){
        if(authenticationRepository.isUserRole(authorizationHeader))
            return cartItemRepository.emptyCart(userId);
        return HttpStatus.UNAUTHORIZED;
    }
}
