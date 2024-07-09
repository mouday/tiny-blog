package io.github.mouday.service;

import io.github.mouday.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Test
    void getTableFile() {
        File tableFile = articleService.getTableFile(Article.class);
        System.out.println(tableFile);
    }

    @Test
    void loadData() {
        List<Article> list = articleService.loadData(Article.class);
        for (Article article : list) {
            System.out.println(article);
        }
    }

    @Test
    void dumpData() {
        List<Article> list = new ArrayList<>();
        Article article = new Article();
        article.setTitle("标题");
        article.setCreateTime(LocalDateTime.now());
        list.add(article);

        articleService.dumpData(Article.class, list);
    }

    @Test
    void createData() {
        Article article = new Article();
        article.setTitle("标题1");
        article.setCreateTime(LocalDateTime.now());

        articleService.createData(Article.class, article);
    }

    @Test
    void updateData() {
    }

    @Test
    void deleteData() {
    }
}