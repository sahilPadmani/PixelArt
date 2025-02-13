package com.fsd.art.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Email
    private String email;

    @Size(min = 8)
    private String password;

    private Role role;

    @OneToMany(mappedBy = "buyer",fetch = FetchType.LAZY)
    private List<Painting> purchased_Item;

    @OneToMany(mappedBy = "artist",fetch = FetchType.LAZY)
    private List<Painting> sold_Item;
}
