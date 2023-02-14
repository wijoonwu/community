package com.callbus.community.web.controller;

import com.callbus.community.biz.domain.Article;
import com.callbus.community.biz.domain.ThumbsUp;
import com.callbus.community.biz.domain.properties.Message;
import com.callbus.community.biz.domain.properties.StatusEnum;
import com.callbus.community.biz.service.ArticleService;
import com.callbus.community.biz.service.ThumbsUpService;
import com.callbus.community.web.dto.ReqArticle;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ThumbsUpService thumbsUpService;

    @PostMapping("/new")
    public ResponseEntity<Message> createArticle(@RequestBody ReqArticle articleForm,
        @RequestHeader(required = false, value = "Authentication") String id) {

        Article article = articleService.createArticle(articleForm, id);
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        message.setStatus(StatusEnum.OK);
        message.setMessage("게시글 등록에 성공했습니다.");
        message.setData(article);

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> readArticle(@PathVariable("id") long id) {
        Article article = articleService.readArticle(id);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @PostMapping("/{id}/thumbsUp")
    public ResponseEntity<Message> thumbsUp(@PathVariable("id") long id,
        @RequestHeader(required = false, value = "Authentication") String accountId) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        message.setStatus(StatusEnum.OK);
        message.setMessage(thumbsUpService.thumbsUp(id, accountId));
        return new ResponseEntity<>(message, headers, HttpStatus.OK);

    }

}
