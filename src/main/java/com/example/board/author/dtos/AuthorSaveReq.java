package com.example.board.author.dtos;

import com.example.board.author.domain.Author;
import com.example.board.author.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor //기본생성자
@AllArgsConstructor //모든 매개변수있는 생성자
@Data
@Builder
public class AuthorSaveReq {
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    @Size(min=8)
    @NotEmpty
    private String password;
//    사용자가 String으로 입력해도 Role클래스로 자동변환
//    ex)ADMMIN, USER등으로 입력시 Enum클래스로 변환
    @Builder.Default
    private Role role =Role.USER;
    public Author toEntity(String encodedPassword) {
        return Author.builder().name(this.name).email(this.email).password(encodedPassword).role(this.role).build();
    }
}