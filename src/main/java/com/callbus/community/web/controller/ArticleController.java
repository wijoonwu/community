package com.callbus.community.web.controller;

import com.callbus.community.biz.domain.properties.Message;
import com.callbus.community.biz.domain.properties.StatusEnum;
import com.callbus.community.biz.service.ArticleService;
import com.callbus.community.biz.service.ThumbsUpService;
import com.callbus.community.web.dto.ArticleDto;
import com.callbus.community.web.dto.ArticleForm;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<Message> createArticle(@RequestBody ArticleForm articleForm,
        @RequestHeader(required = false, value = "Authentication") String accountId) {
        ArticleDto articleDto = articleService.createArticle(articleForm, accountId);
        HttpHeaders headers = getHttpHeaders();
        Message message = getMessage();
        message.setMessage("게시글 등록에 성공했습니다.");
        message.setData(articleDto);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> readArticle(@PathVariable("id") long id,
    @RequestHeader(required = false, value = "Authentication") String accountId) {
        ArticleDto articleDto = articleService.readArticle(id, accountId);
        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }
    
    @GetMapping("/list")
    public ResponseEntity<List<ArticleDto>> readArticles(
        @RequestHeader(required = false, value = "Authentication") String accountId){
        List<ArticleDto> articleDtoList = articleService.readArticles(accountId);
        return new ResponseEntity<>(articleDtoList, HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Message> updateArticle(@PathVariable("id") long articleId,
        @RequestBody ArticleForm articleForm,
        @RequestHeader(required = false, value = "Authentication") String accountId){
        ArticleDto articleDto = articleService.updateArticle(articleId, articleForm, accountId);
        Message message = getMessage();
        HttpHeaders headers = getHttpHeaders();
        message.setMessage("게시글을 수정 했습니다.");
        message.setData(articleDto);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Message> deleteArticle(@PathVariable("id") long articleId,
        @RequestHeader(required = false, value = "Authentication") String accountId){
        Message message = getMessage();
        HttpHeaders headers = getHttpHeaders();
        message.setMessage(articleService.deleteArticle(articleId, accountId));
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @PostMapping("/{id}/thumbsUp")
    public ResponseEntity<Message> createThumbsUp(@PathVariable("id") long id,
        @RequestHeader(required = false, value = "Authentication") String accountId) {
        Message message = getMessage();
        HttpHeaders headers = getHttpHeaders();
        message.setMessage(thumbsUpService.createThumbsUp(id, accountId));
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("/thumbsUps")
    public ResponseEntity<List<ArticleDto>> readThumbsUps(@RequestHeader(required = false, value = "Authentication") String accountId){
        List<ArticleDto> articleDtoList = thumbsUpService.readThumbsUpList(accountId);
        return new ResponseEntity<>(articleDtoList, HttpStatus.OK);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return headers;
    }

    private Message getMessage() {
        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        return message;
    }
}
