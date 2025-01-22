package com.example.board.post.service;

import com.example.board.author.domain.Author;
import com.example.board.author.dtos.AuthorDetailRes;
import com.example.board.author.dtos.AuthorUpdateReq;
import com.example.board.author.repository.AuthorRepository;
import com.example.board.post.domain.Post;
import com.example.board.post.dtos.PostDetailRes;
import com.example.board.post.dtos.PostListRes;
import com.example.board.post.dtos.PostSaveReq;
import com.example.board.post.dtos.PostUpdateReq;
import com.example.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public List<PostListRes> findAll() {
        return postRepository.findAll().stream().map(m -> m.postListFromEntity()).collect(Collectors.toList());
    }

//    Page 객체안에 Post가 리스트형태로 담겨있음
    public Page<PostListRes> findAllPaging(Pageable pageable) {
        Page<Post> pagePosts = postRepository.findAll(pageable);
        return pagePosts.map(p->p.postListFromEntity());
    }

    public Post save(PostSaveReq postSaveReq) {
        Author author = authorRepository.findByEmail(postSaveReq.getEmail()).orElseThrow(()->new EntityNotFoundException());
        Post post = postRepository.save(postSaveReq.toEntity(author));
        return post;
    }

    public void delete(Long id){
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("없는 id입니다"));
        postRepository.delete(post);
    }

    public void updateById(Long id, PostUpdateReq postUpdateReq){
        Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("없는 id입니다"));
        post.updatePost(postUpdateReq.getTitle(), postUpdateReq.getContents());
    }

    public PostDetailRes findById(Long id){
        return postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("없는 ID입니다")).detailFromEntity();
    }


}
