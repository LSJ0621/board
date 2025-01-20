package com.example.board.author.domain;

import com.example.board.author.dtos.AuthorListRes;
import com.example.board.common.domain.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
@Builder
public class Author extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length =20,nullable = false)
    private String name;

    @Column(length =30,unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
//    enum은 기본적으로 숫자값으로 db에 들어가므로, 별도로 STRING 지정 필요
    @Enumerated(EnumType.STRING)
    private Role role;


    public AuthorListRes listFromEntity(){
        return AuthorListRes.builder().id(this.id).email(this.email).name(this.name).build();
    }
}
