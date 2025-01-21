package com.example.board.author.domain;

import com.example.board.author.dtos.AuthorDetailRes;
import com.example.board.author.dtos.AuthorListRes;
import com.example.board.common.domain.BaseTimeEntity;
import com.example.board.post.domain.Post;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
//   OneToMany의 기본값이 fetch lazy라 별도의 설정은 없음.
//    mappedBy에 ManyToOne 쪽에 변수명을 문자열로 지정
    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
//    빌더패턴에서 변수 초기화(디폴트값)시 Builder.Default 어노테이션 사용
    @Builder.Default
    private List<Post> posts = new ArrayList<>();

    public AuthorListRes listFromEntity(){
        return AuthorListRes.builder().id(this.id).email(this.email).name(this.name).build();
    }
    public AuthorDetailRes detailFromEntity(){
        int postcount = this.posts.size();
        LocalDateTime createdtime = this.getCreatedTime();
        return AuthorDetailRes.builder().id(this.id).email(this.email).password(this.password).role(this.role).postCount(postcount).createdTime(createdtime).build();
    }

    public void updateAuthor(String updateName,String updatePw){
        this.name = updateName;
        this.password = updatePw;
    }
}
