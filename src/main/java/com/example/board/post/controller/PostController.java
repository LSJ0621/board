package com.example.board.post.controller;

import com.example.board.author.dtos.AuthorDetailRes;
import com.example.board.author.dtos.AuthorUpdateReq;
import com.example.board.post.dtos.PostDetailRes;
import com.example.board.post.dtos.PostUpdateReq;
import com.example.board.post.service.PostService;
import com.example.board.post.dtos.PostListRes;
import com.example.board.post.dtos.PostSaveReq;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/list")
    public List<PostListRes> postList(){
        return postService.findAll();
    }

    @GetMapping("/list/paging")
//    페이징 처리를 위한 데이터 형식
    public List<PostListRes> postListPaging(){
        return postService.findAll();
    }

    @PostMapping("/create")
    public String authorCreatePost(@Valid PostSaveReq postSaveReq){
        postService.save(postSaveReq);
        return "ok";
    }

    @GetMapping("/delete/{id}")
    public String deleteId(@PathVariable Long id){
        postService.delete(id);
        return "ok";
    }
    @GetMapping("/detail/{id}")
    public PostDetailRes postDetail(@PathVariable long id){
        PostDetailRes dto = postService.findById(id);
        return dto;
    }

    @PostMapping("/update/{id}")
    public String updatePw(@PathVariable Long id, @Valid PostUpdateReq postUpdateReq){
        postService.updateById(id, postUpdateReq);
        return "ok";
    }

}
