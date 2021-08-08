package com.techtalksteve.quarkus.jwt.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "AUTH_USER")
@Getter
@Setter
@ToString(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name=User.USER_SEQ, sequenceName = User.USER_SEQ, allocationSize = 1)
public class User implements Serializable {
    public static final String USER_SEQ = "USER_SEQ";

    public User(@NotNull String email, @NotNull String password, Set<String> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator=USER_SEQ)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @NotNull
    @Column
    protected String password;

    @Email
    @NotNull
    @Column(unique = true, length = 100)
    protected String email;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();

}