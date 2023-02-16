package com.callbus.community.web.controller;

import com.callbus.community.biz.domain.properties.ErrorCode;
import com.callbus.community.biz.domain.properties.Message;
import com.callbus.community.biz.domain.properties.StatusEnum;
import com.callbus.community.biz.exception.CustomException;
import com.callbus.community.biz.service.ArticleService;
import com.callbus.community.biz.service.ThumbsUpService;
import com.callbus.community.web.dto.ArticleDto.ArticleForm;
import com.callbus.community.web.dto.ArticleDto.ResArticle;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
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
    public ResponseEntity<Message> createArticle(@RequestBody @Valid ArticleForm articleForm, Errors errors,
        @RequestHeader(required = false, value = "Authentication") String accountId) {
        checkEmpty(errors);
        ResArticle articleDto = articleService.createArticle(articleForm, accountId);
        HttpHeaders headers = getHttpHeaders();
        Message message = getMessage();
        message.setMessage("게시글 등록에 성공했습니다.");
        message.setData(articleDto);
        return new ResponseEntity<>(message, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> readArticle(@PathVariable("id") long id,
    @RequestHeader(required = false, value = "Authentication") String accountId) {
        Object articleDto = articleService.readArticle(id, accountId);
        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }
    
    @GetMapping("/list")
    public ResponseEntity<List<ResArticle>> readArticles(
        @RequestHeader(required = false, value = "Authentication") String accountId){
        List<ResArticle> articleDtoList = articleService.readArticles(accountId);
        return new ResponseEntity<>(articleDtoList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateArticle(@PathVariable("id") long articleId,
        @RequestBody @Valid ArticleForm articleForm, Errors errors,
        @RequestHeader(required = false, value = "Authentication") String accountId){
        checkEmpty(errors);
        ResArticle articleDto = articleService.updateArticle(articleId, articleForm, accountId);
        Message message = getMessage();
        HttpHeaders headers = getHttpHeaders();
        message.setMessage("게시글을 수정 했습니다.");
        message.setData(articleDto);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
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
    public ResponseEntity<List<ResArticle>> readThumbsUps(
        @RequestHeader(required = false, value = "Authentication") String accountId){
        List<ResArticle> articleDtoList = thumbsUpService.readThumbsUpList(accountId);
        return new ResponseEntity<>(articleDtoList, HttpStatus.OK);
    }

    private Message getMessage() {
        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        return message;
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return headers;
    }
    private void checkEmpty(Errors errors) {
        if(errors.hasErrors()){
            throw new CustomException(ErrorCode.CANNOT_BE_EMPTY);
        }
    }
}
