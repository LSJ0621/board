package com.example.board.author.service;

import com.example.board.author.domain.Author;
import com.example.board.author.dtos.AuthorListRes;
import com.example.board.author.dtos.AuthorSaveReq;
import com.example.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public List<AuthorListRes> findAll() {
        return authorRepository.findAll().stream().map(m -> m.listFromEntity()).collect(Collectors.toList());
    }

    public Author save(AuthorSaveReq authorSaveReq) {
        if(authorRepository.findByEmail(authorSaveReq.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        Author author = authorRepository.save(authorSaveReq.toEntity());
        return author;
    }
}
