package com.bobo.cms.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bobo.cms.domain.User;
import com.bobo.cms.service.UserService;
import com.bobo.cms.util.CMSException;
import com.bobo.cms.util.Result;
/**
 * 
 * @ClassName: PassportController 
 * @Description: 系统的注册登录入口
 * @author: charles
 * @date: 2020年3月11日 上午10:36:20
 */
@RequestMapping("passport")
@Controller
public class PassportController {
	
	@Resource
	private UserService userService;
	/**
	 * 
	 * @Title: reg 
	 * @Description: 去注册
	 * @return
	 * @return: String
	 */
	@GetMapping("reg")
	public String reg() {
		
		return "passport/reg";
		
	}
	
	/**
	 * 
	 * @Title: reg 
	 * @Description: 执行注册
	 * @return
	 * @return: boolean
	 */
	@PostMapping("reg")
	@ResponseBody
	public Result<User> reg(User user,Model model) {
		Result<User> result=  new Result<User>();
		try {
			 if(userService.insert(user)>0) {//如果注册成功
				 result.setCode(200);//成功
				 result.setMsg("注册成功");
			 }
		} catch (CMSException e) {//如果是自定义异常
			 e.printStackTrace();
			 result.setCode(300);//注册失败
			 result.setMsg("注册失败:"+e.getMessage());
			
		}catch (Exception e) {//其他异常
			e.printStackTrace();//把异常消息在控制台打印，以便程序员找BUG
			 result.setCode(500);//注册失败,不可预知的异常
			 result.setMsg("注册失败，系统出现不可预知异常，请联系管理员");//给用户看的
		}
		return result;
	
	}
	/**
	 * 
	 * @Title: login 
	 * @Description: 去登录-普通用户
	 * @return
	 * @return: String
	 */
	@GetMapping("login")
	public String login() {
		
		
		return "passport/login";
		
	}
	
	
	/**
	 * 
	 * @Title: login 
	 * @Description: 去登录-管理员用户
	 * @return
	 * @return: String
	 */
	@GetMapping("admin/login")
	public String adminLogin() {
		return "passport/adminLogin";
		
	}
	
	

	
	
	/**
	 * 
	 * @Title: login 
	 * @Description:执行登录
	 * @param user
	 * @param model
	 * @return
	 * @return: Result<User>
	 */
	@PostMapping("login")
	@ResponseBody
	public Result<User> login(User formUser,Model model,HttpSession session) {
		Result<User> result=  new Result<User>();
		try {
			//去登录，如果成功则返回用户的基本信息 
			User user = userService.login(formUser);
			if(null !=user) {//有该用户
				result.setCode(200);
				result.setMsg("登录成功");
				if(user.getRole()==0) {//根据角色判断。存不同的session
					session.setAttribute("user", user);//登录成功，把用户信息存入session
				}else {
					session.setAttribute("admin", user);//登录成功，把用户信息存入session
				}
			}
			
		
		} catch (CMSException e) {//如果是自定义异常
			 e.printStackTrace();
			 result.setCode(300);//登录失败
			 result.setMsg("登录失败:"+e.getMessage());
			
		}catch (Exception e) {//其他异常
			e.printStackTrace();//把异常消息在控制台打印，以便程序员找BUG
			 result.setCode(500);//登录失败,不可预知的异常
			 result.setMsg("登录失败，系统出现不可预知异常，请联系管理员");//给用户看的
		}
		return result;
		
	}
	
	/**
	 * 
	 * @Title: logout 
	 * @Description: 注销用户
	 * @return
	 * @return: String
	 */
	@GetMapping("logout")
	public String  logout(HttpSession session) {
		
		//清除session
		session.invalidate();
		
		return "redirect:/";//回到系统首页
		
	}
	/**
	 * 
	 * @Title: checkName 
	 * @Description: 检查注册用户是否存在
	 * @param username
	 * @return
	 * @return: boolean
	 */
	@ResponseBody
	@PostMapping("checkName")
	public boolean checkName(String username) {
		
		return userService.selectByUsername(username) == null;
		
	}
}
