package com.example.board.post.domain;

import com.example.board.author.domain.Author;
import com.example.board.author.domain.Role;
import com.example.board.author.dtos.AuthorListRes;
import com.example.board.common.domain.BaseTimeEntity;
import com.example.board.post.dtos.PostListRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Builder
public class Post extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length =50,nullable = false)
    private String title;

    @Column(length =3000,nullable = false)
    private String contents;
    private String appointment;
    @CreationTimestamp
    private LocalDateTime appointmentTime;
    @ManyToOne(fetch = FetchType.LAZY) //ManyToOne에서는 default EAGER
    @JoinColumn(name = "author_id")
    private Author author;


    public PostListRes postListFromEntity(){
        return PostListRes.builder().id(this.id).title(this.title).authorEmail(this.author.getEmail()).build();
    }
}
