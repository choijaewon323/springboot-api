package com.project.crud.account.domain;

import com.project.crud.account.dto.AccountResponseDto;
import com.project.crud.common.TimeEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ACCOUNT")
public class Account extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID", nullable = false)
    private Long id;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountRole role;

    @Builder
    public Account(final String username, final String password, final AccountRole role) {
        checkUsernameUnder30(username);

        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void updateUsername(final String username) {
        checkUsernameUnder30(username);

        this.username = username;
    }

    private void checkUsernameUnder30(String username) {
        if (username.length() > 30) {
            throw new IllegalStateException("username은 30글자 이하여야 합니다");
        }
    }

    public void updatePassword(final String password) {
        this.password = password;
    }
}
