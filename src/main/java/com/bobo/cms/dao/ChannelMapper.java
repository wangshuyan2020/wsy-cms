package com.bobo.cms.dao;

import java.util.List;

import com.bobo.cms.domain.Category;
import com.bobo.cms.domain.Channel;

/**
 * 
 * @ClassName: ChannelMapper 
 * @Description: 栏目
 * @author: charles
 * @date: 2020年3月5日 上午10:06:21
 */
public interface ChannelMapper {

	/**
	 * 
	 * @Title: selects 
	 * @Description: 查询所有的栏目
	 * @return
	 * @return: List<Channel>
	 */
	List<Channel> selects();
	
	/**
	 * 
	 * @Title: selectsByChannelId 
	 * @Description: 根据栏目查询分类
	 * @param channelId
	 * @return
	 * @return: List<Category>
	 */
	List<Category> selectsByChannelId(Integer channelId);
}
