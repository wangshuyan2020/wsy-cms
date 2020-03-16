package com.bobo.cms.service;

import com.bobo.cms.domain.Article;
import com.bobo.cms.domain.Comment;
import com.github.pagehelper.PageInfo;

public interface CommentService {

	

	/**
	 * 
	 * @Title: insert 
	 * @Description: 增加评论
	 * @param comment
	 * @return
	 * @return: int
	 */
	int insert(Comment comment);
	/**
	 * 
	 * @Title: selects 
	 * @Description: 根据文章查询文章评论
	 * @param article
	 * @return
	 * @return: List<Comment>
	 */
	PageInfo<Comment> selects(Article article,Integer page,Integer pageSize);
}
