package com.example.proyecto.post.infrastructure;

import com.example.proyecto.post.domail.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
