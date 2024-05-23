package ba.unsa.etf.nbp.bookstorebackend.controller;

import ba.unsa.etf.nbp.bookstorebackend.projection.AddressProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.ImageProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.PublisherProjection;
import ba.unsa.etf.nbp.bookstorebackend.repository.ImageRepository;
import ba.unsa.etf.nbp.bookstorebackend.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping("create")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody int uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            return imageRepository.createImage(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
