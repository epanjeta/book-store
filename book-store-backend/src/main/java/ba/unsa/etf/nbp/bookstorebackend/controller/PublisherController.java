package ba.unsa.etf.nbp.bookstorebackend.controller;

import ba.unsa.etf.nbp.bookstorebackend.projection.AuthorProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.PublisherProjection;
import ba.unsa.etf.nbp.bookstorebackend.repository.AuthorRepository;
import ba.unsa.etf.nbp.bookstorebackend.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
