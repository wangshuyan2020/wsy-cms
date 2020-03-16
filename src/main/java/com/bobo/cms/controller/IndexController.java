package com.bobo.cms.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bobo.cms.domain.Article;
import com.bobo.cms.domain.Category;
import com.bobo.cms.domain.Channel;
import com.bobo.cms.domain.Comment;
import com.bobo.cms.domain.Slide;
import com.bobo.cms.domain.User;
import com.bobo.cms.service.ArticleService;
import com.bobo.cms.service.ChannelService;
import com.bobo.cms.service.CommentService;
import com.bobo.cms.service.SlideService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @ClassName: IndexController 
 * @Description: 系统首页入口
 * @author: charles
 * @date: 2020年3月9日 上午11:20:23
 */
@Controller
public class IndexController {
	
	@Resource
	private ChannelService channelService;
	
	@Resource
	private ArticleService articleService;
	
	@Resource
	private SlideService slideService;
	
	
	@Resource
	private CommentService commentService;
	/**
	 * 
	 * @Title: index 
	 * @Description:进入首页
	 * @param model
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = {"","/","index"})
	public String index(Model model,Article article,@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "5")Integer pageSize) {
		article.setStatus(1);//只显示审核过的文章
		article.setDeleted(0);//只显示未删除
	    model.addAttribute("article", article);//封装查询条件
	    
		//查询左侧栏目
		List<Channel> channels = channelService.selects();
		model.addAttribute("channels", channels);
		
		//如果栏目ID 不为空则查查其下所有的分类
		if(article.getChannelId()!=null) {
		List<Category> categorys = channelService.selectsByChannelId(article.getChannelId());
		model.addAttribute("categorys", categorys);
		}
		//如果栏目为空，说明没有点击左侧栏目，则默认为热点文章
		if(article.getChannelId()==null) {
			article.setHot(1);//
			//查询轮播图的
			List<Slide> slides = slideService.selects();
			model.addAttribute("slides", slides);
		}
		
		//查询所有的文章
		PageInfo<Article> info = articleService.selects(article, page, pageSize);
		model.addAttribute("info", info);
		
		
		
		//在右侧显示最新10遍文章
		Article article2 = new Article();
		article2.setStatus(1);//
		article2.setDeleted(0);
		PageInfo<Article> lastArticles = articleService.selects(article2, 1, 10);
		model.addAttribute("lastArticles", lastArticles);
		return "index/index";
		
	}
	/**
	 * 
	 * @Title: articleDetail 
	 * @Description: 文章详情
	 * @param id
	 * @return
	 * @return: String
	 */
	@RequestMapping("articleDetail")
	public String articleDetail(Integer id,Model model,@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "5")Integer pageSize) {
		Article article = articleService.select(id);
		model.addAttribute("article", article);
		// 查询出当前文章的评论信息
		PageInfo<Comment> info = commentService.selects(article, page, pageSize);
		
		model.addAttribute("info", info);
		return "index/articleDetail";
	}

	
	//增加评论
	@ResponseBody
	@RequestMapping("addComment")
	public boolean addComment(Comment comment,Integer articleId,HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(null ==user)
		return false;//没有登录的用户不能评论
		comment.setUserId(user.getId());
		comment.setArticleId(articleId);
		comment.setCreated(new Date());
		
		return commentService.insert(comment)>0;
	}
}
