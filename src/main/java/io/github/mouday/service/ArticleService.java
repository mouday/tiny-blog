package io.github.mouday.service;

import io.github.mouday.entity.Article;

import java.io.File;
import java.util.List;


public interface ArticleService {

    /**
     * 获取表数据文件路径
     * @param clazz
     * @return
     */
    public File getTableFile(Class clazz);

    /**
     * 读取数据
     * @param clazz
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    public List<Article> loadData(Class clazz, String keyword, Integer page, Integer size);
    public List<Article> loadData(Class clazz,Integer page, Integer size);
    public List<Article> loadData(Class clazz, String keyword);
    public List<Article> loadData(Class clazz);

    /**
     * 保存数据
     * @param clazz
     * @param list
     */
    public void dumpData(Class clazz, List<Article> list);

    Article createData(Class clazz, Article article);

    void updateData(Class clazz, Article article);

    void deleteData(Class clazz, Article article);
}
