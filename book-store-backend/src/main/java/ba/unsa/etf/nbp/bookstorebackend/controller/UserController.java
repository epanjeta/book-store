package ba.unsa.etf.nbp.bookstorebackend.controller;

import ba.unsa.etf.nbp.bookstorebackend.Role;
import ba.unsa.etf.nbp.bookstorebackend.projection.BookProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.UserProjection;
import ba.unsa.etf.nbp.bookstorebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public @ResponseBody List<UserProjection> getAllUsers() {
        return userRepository.findAllWithAddress(Role.BOOK_BUYER );
    }

    @GetMapping("/{id}")
    public @ResponseBody UserProjection getUserWithAddress(@PathVariable("id") int id) {
        return userRepository.findUserWithAddress(Role.BOOK_BUYER, id);
    }
}
