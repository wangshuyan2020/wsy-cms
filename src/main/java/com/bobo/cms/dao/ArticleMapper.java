package com.bobo.cms.dao;

import java.util.List;

import com.bobo.cms.domain.Article;

public interface ArticleMapper {

	/**
	 * 
	 * @Title: insert 
	 * @Description: 增加文章
	 * @param article
	 * @return
	 * @return: int
	 */
	int insert(Article article);
	/**
	 * 
	 * @Title: selects 
	 * @Description: 文章列表
	 * @param article
	 * @return
	 * @return: List<Article>
	 */
	List<Article> selects(Article article);
	/**
	 * 
	 * @Title: select 
	 * @Description: 单个文章
	 * @param id
	 * @return
	 * @return: Article
	 */
	Article select(Integer id);
	/**
	 * 
	 * @Title: update 
	 * @Description: 更新
	 * @param article
	 * @return
	 * @return: int
	 */
	int update(Article article);
}
