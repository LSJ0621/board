package com.example.board.author.service;

import com.example.board.author.domain.Author;
import com.example.board.author.dtos.AuthorDetailRes;
import com.example.board.author.dtos.AuthorListRes;
import com.example.board.author.dtos.AuthorSaveReq;
import com.example.board.author.dtos.AuthorUpdateReq;
import com.example.board.author.repository.AuthorRepository;
import com.example.board.post.dtos.PostDetailRes;
import com.example.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PostRepository postRepository;

    public List<AuthorListRes> findAll() {
        return authorRepository.findAll().stream().map(m -> m.listFromEntity()).collect(Collectors.toList());
    }

    public void save(AuthorSaveReq authorSaveReq) {
        if(authorRepository.findByEmail(authorSaveReq.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
//        cascade를 활용하지 않고 별도로 post 데이터 만드는 경우
        Author author = authorRepository.save(authorSaveReq.toEntity());
//        postRepository.save(Post.builder().title("반갑습니다").contents("처음뵙겠습니다").author(author).build());

//        cascade를 활용해서 post데이터를 함께만드는경우
//        Author author = Author.builder().name(authorSaveReq.getName()).email(authorSaveReq.getEmail())
//                .password(authorSaveReq.getPassword()).role(authorSaveReq.getRole()).build();
////        post를 생성하는 시점에 author가 아직 DB에 저장되지 않은것으로 보여지나, jpa가 author와 post를 save하는 시점에 선후관계를 맞춰줌
//        author.getPosts().add(Post.builder().title("안녕하세요1 반갑습니다").author(author).build());
//        author.getPosts().add(Post.builder().title("안녕하세요2 반갑습니다").author(author).build());
        authorRepository.save(author);
    }

    public void delete(Long id){
        Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("없는 id입니다"));
        authorRepository.delete(author);
    }

    public void updateById(Long id, AuthorUpdateReq authorUpdateReq){
        Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("없는 id입니다"));
        author.updateAuthor(authorUpdateReq.getName(), authorUpdateReq.getPassword());
//        기존 객체에 변경이 발생할 경우, 별도의 save 없이도 jpa가 엔티티의 변경을 자동인지하고, 변경사항을 DB반영
//        이를 dirtychecking이라 부르고, 반드시 transactional 어노테이션이 있을 경우 동작.
//        authorRepository.save(author); -> 없어도 됌
    }
    public AuthorDetailRes findById(Long id){
        return authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("없는 ID입니다")).detailFromEntity();
    }
}
