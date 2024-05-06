package ba.unsa.etf.nbp.bookstorebackend.controller;

import ba.unsa.etf.nbp.bookstorebackend.projection.AddressProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.AuthorProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.AutocompleteProjection;
import ba.unsa.etf.nbp.bookstorebackend.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "author")
public class AuthorController {

    @Autowired
    protected AuthorRepository authorRepository;


    @GetMapping
    public @ResponseBody List<AuthorProjection> getAll() {
        return authorRepository.findAllAuthors();
    }

    @GetMapping("nationality")
    public @ResponseBody List<AutocompleteProjection> getAllCities() {
        return authorRepository.findAllNationalities();
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.OK)
    public  @ResponseBody AuthorProjection createAuthor
            (@RequestBody AuthorProjection author) {
        return authorRepository.createAuthor(author);
    }

    @PostMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public  @ResponseBody AuthorProjection updateAuthor
            (@RequestBody AuthorProjection author) {
        return authorRepository.updateAuthor(author);
    }
}
