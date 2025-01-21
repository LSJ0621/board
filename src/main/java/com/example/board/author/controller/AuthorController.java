package com.example.board.author.controller;

import com.example.board.author.dtos.AuthorDetailRes;
import com.example.board.author.dtos.AuthorListRes;
import com.example.board.author.dtos.AuthorSaveReq;
import com.example.board.author.dtos.AuthorUpdateReq;
import com.example.board.author.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @GetMapping("/list")
    public List<AuthorListRes> memberList(){
        return authorService.findAll();
    }

    @PostMapping("/create")
    public String authorCreatePost(@Valid AuthorSaveReq authorSaveReq){
        authorService.save(authorSaveReq);
        return "ok";
    }

    @GetMapping("/delete/{id}")
    public String deleteId(@PathVariable Long id){
        authorService.delete(id);
        return "ok";
    }
    @GetMapping("/detail/{id}")
    public AuthorDetailRes memberDetail(@PathVariable long id){
        AuthorDetailRes dto = authorService.findById(id);
        return dto;
    }

    @PostMapping("/update/{id}")
    public String updatePw(@PathVariable Long id, @Valid AuthorUpdateReq authorUpdateReq){
        authorService.updateById(id, authorUpdateReq);
        return "ok";
    }

}
