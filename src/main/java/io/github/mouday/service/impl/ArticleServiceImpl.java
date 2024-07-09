package io.github.mouday.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.github.mouday.annotation.JsonTable;
import io.github.mouday.entity.Article;
import io.github.mouday.service.ArticleService;
import io.github.mouday.utils.JSONUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {

    // 获取启动目录
    private static final String runtimeDir = System.getProperty("user.dir");
    private static final String databaseDir = "database";

    public File getTableFile(Class clazz) {
        String name = null;

        if (clazz.isAnnotationPresent(JsonTable.class)) {
            JsonTable annotation = (JsonTable) clazz.getAnnotation(JsonTable.class);
            name = annotation.name();
        } else {
            throw new RuntimeException("not found JsonTable annotation");
        }

        File file = new File(runtimeDir, databaseDir);

        if (!file.exists()) {
            file.mkdirs();
        }

        return new File(file, name + ".json");
    }

    public List<Article> copyFile(Class clazz, String keyword, Integer page, Integer size) {

        List<Article> list = new ArrayList<>();

        File file = this.getTableFile(clazz);
        File tempFile = new File(file, ".tmp");

        if (!file.exists()) {
            return list;
        }

        ObjectMapper mapper = JSONUtil.getObjectMapper();

        try {

            Scanner scanner = new Scanner(file);
            PrintWriter writer = new PrintWriter(tempFile);

            String line;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                // Article article = mapper.readValue(scanner.nextLine(), Article.class);

                writer.println(line);
            }

            scanner.close();
            writer.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return list;
    }

    public List<Article> loadData(Class clazz, String keyword, Integer page, Integer size) {

        List<Article> list = new ArrayList<>();

        File file = this.getTableFile(clazz);

        if (!file.exists()) {
            return list;
        }

        ObjectMapper mapper = JSONUtil.getObjectMapper();

        try {

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                try {
                    Article article = mapper.readValue(scanner.nextLine(), Article.class);
                    list.add(article);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override
    public List<Article> loadData(Class clazz, Integer page, Integer size) {
        return this.loadData(clazz, null, page, size);
    }

    @Override
    public List<Article> loadData(Class clazz, String keyword) {
        return this.loadData(clazz, keyword, null, null);
    }

    @Override
    public List<Article> loadData(Class clazz) {
        return this.loadData(clazz, null, null, null);
    }

    public void dumpData(Class clazz, List<Article> list) {
        File file = this.getTableFile(clazz);

        ObjectMapper mapper = JSONUtil.getObjectMapper();

        try {
            PrintWriter writer = new PrintWriter(file);
            for (Article article : list) {

                try {
                    writer.println(mapper.writeValueAsString(article));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }

            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Article createData(Class clazz, Article article) {

        article.setUid(UUID.randomUUID().toString());
        List<Article> list = this.loadData(clazz);
        list.add(0, article);
        this.dumpData(clazz, list);
        return article;
    }

    @Override
    public void updateData(Class clazz, Article article) {

    }

    @Override
    public void deleteData(Class clazz, Article article) {

    }
}
