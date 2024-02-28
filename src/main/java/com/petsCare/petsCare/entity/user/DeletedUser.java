package com.petsCare.petsCare.entity.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "DELETED_USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@Builder
public class DeletedUser {

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

    @CreatedDate
    @Column(name = "user_deleted_date", updatable = false)
    private LocalDateTime deletedDate;

    @Builder
    public DeletedUser(String provider, String loginId, String username, String profileImage, String role) {
        this.provider = provider;
        this.loginId = loginId;
        this.username = username;
        this.profileImage = profileImage;
        this.role = role;
    }
}
