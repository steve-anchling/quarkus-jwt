package com.techtalksteve.quarkus.jwt.repository;

import com.techtalksteve.quarkus.jwt.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    public Optional<User> findByEmail(final String email){
        return find("email", email).singleResultOptional();
    }
}
