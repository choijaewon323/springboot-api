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
    public Account(String username, String password, AccountRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public AccountResponseDto toResponseDto() {
        return AccountResponseDto.builder()
                .id(id)
                .username(username)
                .password(password)
                .role(role)
                .build();
    }
}
