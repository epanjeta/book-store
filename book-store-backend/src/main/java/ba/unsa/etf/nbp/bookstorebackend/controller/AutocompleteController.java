package ba.unsa.etf.nbp.bookstorebackend.controller;

import ba.unsa.etf.nbp.bookstorebackend.projection.AutocompleteProjection;
import ba.unsa.etf.nbp.bookstorebackend.repository.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "autocomplete")
public class AutocompleteController {

    @Autowired
    protected CommonRepository commonRepository;

    @GetMapping("/genre")
    public @ResponseBody List<AutocompleteProjection> getAllGenres() {
        return commonRepository.getGenres();
    }

    @GetMapping("/language")
    public @ResponseBody List<AutocompleteProjection> getAllLanguages() {
        return commonRepository.getLanguages();
    }

}
