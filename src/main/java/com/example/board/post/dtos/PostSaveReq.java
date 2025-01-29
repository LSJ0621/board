package com.example.board.post.dtos;

import com.example.board.author.domain.Author;
import com.example.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@NoArgsConstructor //기본생성자
@AllArgsConstructor //모든 매개변수있는 생성자
@Data
@Builder
public class PostSaveReq {
    @NotEmpty
    private String title;
    private String contents;
    private String appointment;
    private String appointmentTime;

    public Post toEntity(Author author, LocalDateTime appointmentTime) {
        return Post.builder().title(this.title).contents(this.contents).author(author).appointment(this.appointment).appointmentTime(appointmentTime).build();
    }
}
