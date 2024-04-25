package ba.unsa.etf.nbp.bookstorebackend.controller;

import ba.unsa.etf.nbp.bookstorebackend.projection.AuthorProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.PublisherProjection;
import ba.unsa.etf.nbp.bookstorebackend.repository.AuthorRepository;
import ba.unsa.etf.nbp.bookstorebackend.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "publisher")
public class PublisherController {

    @Autowired
    protected PublisherRepository publisherRepository;

    @GetMapping
    public @ResponseBody List<PublisherProjection> getAll() {
        return publisherRepository.findAllWithAddress();
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.OK)
    public  @ResponseBody PublisherProjection createPublisher
            (@RequestBody PublisherProjection publisher) {
        return publisherRepository.createPublisherWithAddress(publisher);
    }

    @PostMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public  @ResponseBody PublisherProjection updatePublisher
            (@RequestBody PublisherProjection publisher) {
        return publisherRepository.updatePublisher(publisher);
    }
}
