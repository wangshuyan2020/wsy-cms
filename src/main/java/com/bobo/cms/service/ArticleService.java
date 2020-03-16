package com.bobo.cms.service;

import java.util.List;

import com.bobo.cms.domain.Article;
import com.github.pagehelper.PageInfo;

public interface ArticleService {
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
	PageInfo<Article> selects(Article article,Integer page,Integer pgeSize);
	
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
