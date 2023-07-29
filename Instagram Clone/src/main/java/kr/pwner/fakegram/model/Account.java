package kr.pwner.fakegram.model;

import kr.pwner.fakegram.dto.account.UpdateAccountDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idx;

    @Column(nullable = false)
    private Boolean isActivate;

    @Column(unique = true, nullable = false)
    private String id;

    @Column(length = 72)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Enumerated()
    @Column(nullable = false)
    private AccountRole role;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date updatedAt;

    @Column(nullable = false)
    private Date lastSignIn;

    private String refreshToken;

    private String profileImage;
    // * Relation Mapping
    @OneToMany(mappedBy = "account", fetch= FetchType.EAGER)
    private List<Feed> feeds = new ArrayList<>();

    // * Methods
    @Builder
    public Account(String id, String password, String name, String email, String profileImage) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;

        this.isActivate = true;
        this.role = AccountRole.USER;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.lastSignIn = new Date();
        this.refreshToken = null;
    }

    public void Update(UpdateAccountDto.Request account) {
        this.id = Objects.nonNull(account.getId()) ? account.getId() : this.getId();
        this.password = Objects.nonNull(account.getPassword()) ?
                new BCryptPasswordEncoder().encode(account.getPassword()) : this.getPassword();
        this.email = Objects.nonNull(account.getEmail()) ? account.getEmail() : this.getEmail();
        this.name = Objects.nonNull(account.getName()) ? account.getName() : this.getName();
        this.updatedAt = new Date();
    }

    public void Delete() {
        this.isActivate = false;
        this.password = null;
    }

    public void SignIn(String refreshToken) {
        this.refreshToken = refreshToken;
        this.lastSignIn = new Date();
    }

    public void SignOut() {
        this.refreshToken = null;
    }

    public void ProfileImage(String profileImage){
        this.profileImage = profileImage;
    }
}

// * Enums
enum AccountRole {
    USER, ADMIN
}