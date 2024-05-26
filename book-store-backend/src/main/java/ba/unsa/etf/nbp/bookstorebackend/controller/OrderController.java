package ba.unsa.etf.nbp.bookstorebackend.controller;

import ba.unsa.etf.nbp.bookstorebackend.constants.OrderForm;
import ba.unsa.etf.nbp.bookstorebackend.constants.Status;
import ba.unsa.etf.nbp.bookstorebackend.projection.OrderProjection;
import ba.unsa.etf.nbp.bookstorebackend.repository.OrderRepository;
import ba.unsa.etf.nbp.bookstorebackend.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @GetMapping
    public @ResponseBody List<OrderProjection> findAllOrders(@RequestParam int userId){
        return orderRepository.findAll(userId);
    }

    @PostMapping("/createNewOrder")
    public @ResponseBody int createNewOrder(@RequestBody OrderForm orderFord){
        orderRepository.createOrderForUser(orderFord);
        cartItemRepository.emptyCart(orderFord.getUserId());
        return 0;
    }

    @PutMapping("/update")
    public @ResponseBody int updateOrder(@RequestBody OrderProjection orderProjection){
        return orderRepository.updateOrder(orderProjection.getId(), orderProjection.getStatus());
    }
}
