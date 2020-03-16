package com.bobo.cms.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bobo.cms.domain.Article;
import com.bobo.cms.domain.User;
import com.bobo.cms.service.ArticleService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @ClassName: MyController 
 * @Description: 个人中心
 * @author: charles
 * @date: 2020年3月4日 上午10:50:25
 */
@RequestMapping("my")
@Controller
public class MyController {
	
	
	@Resource
	private ArticleService articleService;
	
	/**
	 * 
	 * @Title: index 
	 * @Description: 进入个人中心的首页
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = {"","/","index"})
	public String index() {
		
		
		return "my/index";
		
	}
	
	/**
	 * 
	 * @Title: articleDetail 
	 * @Description: 单个文章么内容
	 * @param id
	 * @return
	 * @return: Article
	 */
	@ResponseBody
	@RequestMapping("articleDetail")
	public Article articleDetail(Integer id) {
		
		return articleService.select(id);
		
	}
	
	/**
	 * 
	 * @Title: articles 
	 * @Description: 我的文章
	 * @return
	 * @return: String
	 */
	@RequestMapping("articles")
	public String articles(Model model,HttpSession  session ,@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "3")Integer pageSize) {
		Article article = new Article();
		User user =(User) session.getAttribute("user");//从session获取当前登录人的信息
		article.setUserId(user.getId());//只显示当前登录的人文章
		PageInfo<Article> info = articleService.selects(article, page, pageSize);
		
		model.addAttribute("info", info);
		return "my/articles";
		
	}
	/**
	 * 
	 * @Title: publish 
	 * @Description: 去发布文章
	 * @return
	 * @return: String
	 */
	@GetMapping("publish")
	public String publish() {
		return "my/publish";
		
	}
	/**
	 * 
	 * @Title: publish 
	 * @Description: 发布文章
	 * @param file
	 * @param article
	 * @return
	 * @return: boolean
	 */
	@ResponseBody
	@PostMapping("publish")
	public boolean publish(MultipartFile file,   Article article,HttpSession session) {
		//文件上传
		if(null!=file &&!file.isEmpty()) {
			String path ="d:/pic/";
		  	
			   //文件的原始名称 1.jpg
			String filename = file.getOriginalFilename();
			//为了防止文件重名，需要改变文件的名字
			String newFilename = UUID.randomUUID() + filename.substring(filename.lastIndexOf("."));
			
			File f = new File(path, newFilename);
			
			//把文件写入硬盘
			try {
				file.transferTo(f);
				article.setPicture(newFilename);//文件的名称
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		//文章初始数据
		User user = (User) session.getAttribute("user");
		article.setUserId(user.getId());//发布人
		article.setCreated(new Date());
		article.setHits(0);//点击量默认 0
		article.setDeleted(0);//默认未删除
		article.setHot(0);//默认非热门
		article.setStatus(0);//默认待审核
		return articleService.insert(article) >0;//增加文章
		
	}
	

}
