package com.example.proyecto.post.controller;

import com.example.proyecto.comentario.domail.Comentario;
import com.example.proyecto.post.domail.PostService;
import com.example.proyecto.post.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/carreras")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        PostResponseDto response = postService.createPost(postRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/carreras/{carreraId}")
    public ResponseEntity<Page<PostResponseDto>> getPosts(@PathVariable Long carreraId ,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostResponseDto> posts = postService.getAll(carreraId,pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        PostResponseDto response = postService.getPostById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id,
                                                      @RequestBody PostUpdate requestDto) {
        PostResponseDto response = postService.updatePost(id, requestDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/chats")
    public ResponseEntity<Page<PostResponseDto>> getChats(
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "3") int size) {
        Page<PostResponseDto> posts = postService.getChats(page,size);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/chats")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestChatDto postRequestChasDto) {
        PostResponseDto response = postService.createChat(postRequestChasDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/chats/{id}")
    public ResponseEntity<List<Comentario>> getMessages(@PathVariable Long id) {
        List<Comentario> response = postService.getMessages(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping("api/messages")
    public ResponseEntity<PostResposeChatDto> createPost(@RequestBody PostRequestMakeChatDto postRequestChasDto) {
        PostResposeChatDto response = postService.makeAiRequest(postRequestChasDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



}
