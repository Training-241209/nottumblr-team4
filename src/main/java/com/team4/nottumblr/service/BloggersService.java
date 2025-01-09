package com.team4.nottumblr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team4.nottumblr.dto.BloggersDTO;
import com.team4.nottumblr.model.Bloggers;
import com.team4.nottumblr.model.Blogs;
import com.team4.nottumblr.repository.BloggersRepository;

@Service
public class BloggersService {

    @Autowired
    private BloggersRepository bloggersRepository;

    @Autowired
    private JwtService jwtService;


    public boolean existsBloggerByBloggerId(long bloggerId) {
        return bloggersRepository.existsById(bloggerId);
    }

    public Bloggers getBloggerById(long bloggerId) {
        if (!bloggersRepository.existsById(bloggerId)) {
            throw new IllegalArgumentException("Blogger with ID: " + bloggerId + " does not exist.");
        }
        return bloggersRepository.findById(bloggerId).orElse(null);
    }

    public Bloggers updateBlogger(long bloggerId, BloggersDTO bloggersDTO, String token) {
        Bloggers currentBlogger = jwtService.decodeToken(token);

        if (!currentBlogger.getRole().getRoleName().equals("ADMIN")) {
            throw new IllegalArgumentException("Only admins can update bloggers.");
        }

        Bloggers bloggerToUpdate = bloggersRepository.findById(bloggerId)
                .orElseThrow(() -> new IllegalArgumentException("Blogger with ID: " + bloggerId + " does not exist."));

        if (bloggersDTO.getUsername() != null && !bloggersDTO.getUsername().isEmpty()) {
            bloggerToUpdate.setUsername(bloggersDTO.getUsername());
        }

        return bloggersRepository.save(bloggerToUpdate);
    }

    public List<Blogs> getAllBlogsByBlogger(long bloggerId) {
        Bloggers blogger = bloggersRepository.findById(bloggerId)
                .orElseThrow(() -> new IllegalArgumentException("Blogger with ID: " + bloggerId + " not found"));
        return blogger.getBlogs();
    }

}
