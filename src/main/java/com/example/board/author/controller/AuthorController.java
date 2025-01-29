package com.example.board.author.controller;

import com.example.board.author.domain.Author;
import com.example.board.author.dtos.AuthorDetailRes;
import com.example.board.author.dtos.AuthorListRes;
import com.example.board.author.dtos.AuthorSaveReq;
import com.example.board.author.dtos.AuthorUpdateReq;
import com.example.board.author.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @GetMapping("/list")
    public String memberList(Model model){
        List<AuthorListRes> authorListResList =  authorService.findAll();
        model.addAttribute("authorList",authorListResList);
        return "/author/author_list";
    }

    @GetMapping("/create")
    public String authorCreate2(){
        return "/author/author_create";
    }
    @GetMapping("/login")
    public String authorLoginScreen(){
        return "/author/author_login";
    }

    @PostMapping("/create")
    public String authorCreate(@Valid AuthorSaveReq authorSaveReq){
        authorService.save(authorSaveReq);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteId(@PathVariable Long id){
        authorService.delete(id);
        return "ok";
    }
    @GetMapping("/detail/{id}")
    public String memberDetail(@PathVariable long id,Model model){
        AuthorDetailRes dto = authorService.findById(id);
        model.addAttribute("author",dto);
        return "author/author_detail";
    }

    @PostMapping("/update/{id}")
    public String updatePw(@PathVariable Long id, @Valid AuthorUpdateReq authorUpdateReq){
        authorService.updateById(id, authorUpdateReq);
        return "ok";
    }

}
