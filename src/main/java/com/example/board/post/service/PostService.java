package com.example.board.post.service;

import com.example.board.author.domain.Author;
import com.example.board.author.dtos.AuthorDetailRes;
import com.example.board.author.dtos.AuthorUpdateReq;
import com.example.board.author.repository.AuthorRepository;
import com.example.board.common.service.LoginSuccessHandler;
import com.example.board.post.domain.Post;
import com.example.board.post.dtos.PostDetailRes;
import com.example.board.post.dtos.PostListRes;
import com.example.board.post.dtos.PostSaveReq;
import com.example.board.post.dtos.PostUpdateReq;
import com.example.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        Page<Post> pagePosts = postRepository.findAllByAppointment(pageable,"N");
        return pagePosts.map(p->p.postListFromEntity());
    }

    public List<PostListRes> listFetchJoin(){
//        일반 JOIN : author를 join해서 post를 조회하긴 하나, author의 데이터는 실제 참조할때 쿼리가 N번 발생
//        List<Post> postList = postRepository.findAllJoin();
//        FETCH JOIN : author를 join해서 조회하고, author의 데이터도 join시점에서 가져옴. 쿼리 한번 발생
        List<Post> postList = postRepository.findAllFetchJoin();
        return postList.stream().map(m -> m.postListFromEntity()).collect(Collectors.toList());
    }

    public Post save(PostSaveReq postSaveReq) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Author author = authorRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException());
        LocalDateTime appointmentTime = null;
        if(postSaveReq.getAppointment().equals("Y")){
            if(postSaveReq.getAppointmentTime().isEmpty() || postSaveReq.getAppointmentTime()==null){
                throw new IllegalArgumentException("시간이 비어져 있습니다.");
            }else{
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                appointmentTime = LocalDateTime.parse(postSaveReq.getAppointmentTime(),dateTimeFormatter);
                LocalDateTime now = LocalDateTime.now();
                if(appointmentTime.isBefore(now)){
                    throw new IllegalArgumentException("시간이 과거입니다.");
                }
            }
        }
        Post post = postRepository.save(postSaveReq.toEntity(author,appointmentTime));
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
