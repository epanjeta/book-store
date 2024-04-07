package ba.unsa.etf.nbp.bookstorebackend.controller;

import ba.unsa.etf.nbp.bookstorebackend.projection.AuthorProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.BookProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.CartItem;
import ba.unsa.etf.nbp.bookstorebackend.repository.AuthorRepository;
import ba.unsa.etf.nbp.bookstorebackend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "book")
public class BookController {

    @Autowired
    protected BookRepository bookRepository;


    @GetMapping
    public @ResponseBody List<BookProjection> getAll() {
        return bookRepository.findAllBooks();
    }

    @GetMapping("/booksForOrder")
    public @ResponseBody List<CartItem> getAllBookForOrder(@RequestParam int orderId) {
        return bookRepository.findAllBooksForOrder(orderId);
    }
}
