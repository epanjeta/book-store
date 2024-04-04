package ba.unsa.etf.nbp.bookstorebackend.controller;

import ba.unsa.etf.nbp.bookstorebackend.projection.ImageProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.PublisherProjection;
import ba.unsa.etf.nbp.bookstorebackend.repository.ImageRepository;
import ba.unsa.etf.nbp.bookstorebackend.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "image")
public class ImageController {

    @Autowired
    protected ImageRepository imageRepository;

    @GetMapping
    public @ResponseBody List<ImageProjection> getAll() {
        return imageRepository.findAll();
    }
}
