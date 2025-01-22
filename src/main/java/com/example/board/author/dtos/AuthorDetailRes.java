package com.example.board.author.dtos;

import com.example.board.author.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor //기본생성자
@AllArgsConstructor //모든 매개변수있는 생성자
@Data
@Builder
public class AuthorDetailRes {
//    id, name, email, password, role, postCount, createdTime
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private int postCount;
    private LocalDateTime createdTime;
}
