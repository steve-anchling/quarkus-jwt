package com.techtalksteve.quarkus.jwt.service;

import com.techtalksteve.quarkus.jwt.entity.User;
import com.techtalksteve.quarkus.jwt.exception.AuthenticationUsernameException;
import com.techtalksteve.quarkus.jwt.exception.AuthenticationPasswordException;
import com.techtalksteve.quarkus.jwt.repository.UserRepository;
import com.techtalksteve.quarkus.jwt.rest.AuthenticationRequest;
import io.smallrye.jwt.build.Jwt;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.util.Optional;

@ApplicationScoped
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Inject))
public class UserService {

    private final UserRepository userRepository;
    private final CryptoService cryptoService;

    public Optional<User> findByUsername(final String username) {
        return userRepository.findByEmail(username);
    }

    public String authenticate(final AuthenticationRequest authRequest)
            throws AuthenticationUsernameException, AuthenticationPasswordException {
        final User user = findByUsername(authRequest.getUsername()).orElseThrow(AuthenticationUsernameException::new);
        if (user.getPassword().equals(cryptoService.encrypt(authRequest.getPassword()))){
            return generateToken(user);
        }
        throw new AuthenticationPasswordException();
    }

    public String generateToken(final User user) {
        return Jwt.issuer("https://techtalksteve.com/issuer")
                        .upn(user.getEmail())
                        .expiresIn(Duration.ofDays(365))
                        .groups(user.getRoles())
                        .sign();
    }
}