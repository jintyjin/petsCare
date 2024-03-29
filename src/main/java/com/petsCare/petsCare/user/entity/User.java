package com.petsCare.petsCare.user.entity;

import com.petsCare.petsCare.pet.entity.Pet;
import com.petsCare.petsCare.BaseUserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseUserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_provider")
    private String provider;

    @Column(name = "user_login_id", unique = true)
    private String loginId;

    @Column(name = "user_username")
    private String username;

    @Column(name = "user_profile_image")
    private String profileImage;

    @Column(name = "user_role")
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Pet> pets = new ArrayList<>();

    @Builder
    public User(String provider, String loginId, String username, String profileImage, String role) {
        this.provider = provider;
        this.loginId = loginId;
        this.username = username;
        this.profileImage = profileImage;
        this.role = role;
    }
}
