package com.project.crud.account.domain;

import com.project.crud.common.TimeEntity;
import com.project.crud.exception.CustomException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.project.crud.account.domain.AccountRole.ADMIN;
import static com.project.crud.account.domain.AccountRole.USER;
import static com.project.crud.exception.ErrorCode.ACCOUNT_CREATION_ERROR;


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

    private Account(final String username, final String password, final AccountRole role) {
        checkUsernameIsNotBlank(username);
        checkUsernameUnder30(username);

        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static Account makeUser(String username, String password) {
        return new Account(username, password, USER);
    }

    public static Account makeAdmin(String username, String password) {
        return new Account(username, password, ADMIN);
    }

    public void updateUsername(final String username) {
        checkUsernameUnder30(username);

        this.username = username;
    }

    private void checkUsernameUnder30(String username) {
        if (username.length() > 30) {
            throw new CustomException(ACCOUNT_CREATION_ERROR, "username은 30글자 이하여야 합니다");
        }
    }

    private void checkUsernameIsNotBlank(String username) {
        if (username == null) {
            throw new CustomException(ACCOUNT_CREATION_ERROR, "작성자를 입력해주세요");
        }

        if (username.isBlank()) {
            throw new CustomException(ACCOUNT_CREATION_ERROR, "작성자를 입력해주세요");
        }
    }

    public void updatePassword(final String password) {
        this.password = password;
    }
}
