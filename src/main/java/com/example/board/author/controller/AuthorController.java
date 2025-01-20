package com.example.board.author.controller;

import com.example.board.author.domain.Author;
import com.example.board.author.dtos.AuthorListRes;
import com.example.board.author.dtos.AuthorSaveReq;
import com.example.board.author.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
