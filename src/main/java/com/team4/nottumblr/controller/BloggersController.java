package com.team4.nottumblr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team4.nottumblr.dto.BloggersDTO;
import com.team4.nottumblr.model.Bloggers;
import com.team4.nottumblr.service.BloggerMapper;
import com.team4.nottumblr.service.BloggersService;

@RestController
@RequestMapping("/bloggers")
public class BloggersController {
    
    @Autowired
    private BloggersService bloggersService;

    @Autowired
    private BloggerMapper bloggerMapper;

    @GetMapping("/{bloggerId}")
    public ResponseEntity<?> getBlogger(@CookieValue(name = "jwt") String token, @PathVariable long bloggerId) {
        Bloggers blogger = bloggersService.getBloggerById(bloggerId);
        BloggersDTO bloggerDTO = bloggerMapper.convertToBloggersDTO(blogger);
        return ResponseEntity.ok().body(bloggerDTO);
    }

}
