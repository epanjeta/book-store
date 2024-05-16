package ba.unsa.etf.nbp.bookstorebackend.controller;

import ba.unsa.etf.nbp.bookstorebackend.constants.BookForm;
import ba.unsa.etf.nbp.bookstorebackend.projection.AddressProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.AuthorProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.BookProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.CartItem;
import ba.unsa.etf.nbp.bookstorebackend.repository.AuthenticationRepository;
import ba.unsa.etf.nbp.bookstorebackend.repository.AuthorRepository;
import ba.unsa.etf.nbp.bookstorebackend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "book")
public class BookController {

    @Autowired
    protected BookRepository bookRepository;

    @Autowired
    protected AuthenticationRepository authenticationRepository;


    @GetMapping
    public @ResponseBody List<BookProjection> getAll() {
        return bookRepository.findAllBooks();
    }

    @GetMapping("/{id}")
    public @ResponseBody BookProjection getBook(@PathVariable("id") int id) {
        return bookRepository.findBook(id);
    }

    @GetMapping("/booksForOrder")
    public @ResponseBody List<CartItem> getAllBookForOrder(@RequestParam int orderId) {
        return bookRepository.findAllBooksForOrder(orderId);
    }

    @PostMapping("/createNewBook")
    public @ResponseBody HttpStatus createNewBook(@RequestHeader(name = "Authorization", required = false) String authorizationHeader, @RequestBody BookProjection bookForm){

        if(authenticationRepository.isAdminRole(authorizationHeader))
            return bookRepository.createNewBook(bookForm);
        else return HttpStatus.UNAUTHORIZED;
    }
}
