package com.callbus.community.biz.repository;

import com.callbus.community.biz.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {

}
