package com.bobo.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bobo.cms.dao.CommentMapper;
import com.bobo.cms.domain.Article;
import com.bobo.cms.domain.Comment;
import com.bobo.cms.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Resource
	private CommentMapper commentMapper;

	@Override
	public int insert(Comment comment) {
		return commentMapper.insert(comment);
	}

	@Override
	public PageInfo<Comment> selects(Article article, Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		List<Comment> list = commentMapper.selects(article);
		return new PageInfo<Comment>(list);
	}

}
