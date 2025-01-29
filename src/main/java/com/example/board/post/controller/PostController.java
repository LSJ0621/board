package com.example.board.post.controller;

import com.example.board.author.dtos.AuthorDetailRes;
import com.example.board.author.dtos.AuthorListRes;
import com.example.board.author.dtos.AuthorUpdateReq;
import com.example.board.post.dtos.PostDetailRes;
import com.example.board.post.dtos.PostUpdateReq;
import com.example.board.post.service.PostService;
import com.example.board.post.dtos.PostListRes;
import com.example.board.post.dtos.PostSaveReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/list")

    public String postList(Model model){
        List<PostListRes> postListResList =  postService.findAll();
        model.addAttribute("postList",postListResList);
        return "/post/post_list";
    }

    @GetMapping("/list/paging")
//    페이징 처리를 위한 데이터 형식 :localhost:8080/host/list/paging?size=10&page=0&sort=createdTime,desc
    public String postListPaging(Model model,@PageableDefault(size=10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        model.addAttribute("postList",postService.findAllPaging(pageable));
        return "post/post_list";
    }

    @GetMapping("list/fetchjoin")
    @ResponseBody
    public String postListFetchJoin(){
        postService.listFetchJoin();
        return "ok";
    }
    @GetMapping("/create")
    public String authorCreatePost2(){
        return "/post/post_create";
    }

    @PostMapping("/create")
    public String authorCreatePost(@Valid PostSaveReq postSaveReq){
        postService.save(postSaveReq);
        return "redirect:/post/list/paging";
    }

    @GetMapping("/delete/{id}")
    public String deleteId(@PathVariable Long id){
        postService.delete(id);
        return "ok";
    }
    @GetMapping("/detail/{id}")
    public String postDetail(@PathVariable long id, Model model){
        PostDetailRes dto = postService.findById(id);
        model.addAttribute("post",dto);
        return "post/post_detail";
    }

    @PostMapping("/update/{id}")
    public String updatePw(@PathVariable Long id, @Valid PostUpdateReq postUpdateReq){
        postService.updateById(id, postUpdateReq);
        return "ok";
    }
}
