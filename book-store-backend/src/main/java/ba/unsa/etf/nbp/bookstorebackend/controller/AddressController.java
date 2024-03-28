package ba.unsa.etf.nbp.bookstorebackend.controller;

import ba.unsa.etf.nbp.bookstorebackend.projection.AddressProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.AutocompleteProjection;
import ba.unsa.etf.nbp.bookstorebackend.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "address")
public class AddressController {

    @Autowired protected AddressRepository addressRepository;


    @GetMapping
    public @ResponseBody List<AddressProjection> getAll() {
        return addressRepository.findAllAddresses();
    }

    @GetMapping("city")
    public @ResponseBody List<AutocompleteProjection> getAllCities() {
        return addressRepository.findAllCities();
    }

    @GetMapping ("country")
    public @ResponseBody List<AutocompleteProjection> getAllCountries() {
        return addressRepository.findAllCountries();
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.OK)
    public  @ResponseBody AddressProjection createAddress
            (@RequestBody AddressProjection address) {
        return addressRepository.createAddress(address);
    }


}
