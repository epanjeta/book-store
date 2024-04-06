package ba.unsa.etf.nbp.bookstorebackend.controller;

import ba.unsa.etf.nbp.bookstorebackend.projection.AuthenticationRequest;
import ba.unsa.etf.nbp.bookstorebackend.projection.AuthenticationResponse;
import ba.unsa.etf.nbp.bookstorebackend.projection.MessageResponse;
import ba.unsa.etf.nbp.bookstorebackend.projection.UserProjection;
import ba.unsa.etf.nbp.bookstorebackend.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "authentication")
public class AuthenticationController {

    @Autowired
    protected AuthenticationRepository authenticationRepository;

    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
        return new ResponseEntity<>(authenticationRepository.authenticate(authenticationRequest), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<MessageResponse> register(@RequestBody UserProjection userProjection){
        return new ResponseEntity<>(authenticationRepository.register(userProjection), HttpStatus.OK);
    }
}
