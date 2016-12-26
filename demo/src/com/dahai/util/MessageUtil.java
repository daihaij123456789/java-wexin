package com.dahai.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dahai.po.Image;
import com.dahai.po.ImageMessage;
import com.dahai.po.Music;
import com.dahai.po.MusicMessage;
import com.dahai.po.News;
import com.dahai.po.NewsMessage;
import com.dahai.po.TextMessage;
import com.sun.org.apache.xerces.internal.xs.XSTerm;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {
	public static String MESSAGE_TEXT="text";
	public static String MESSAGE_NEWS="news";
	public static String MESSAGE_MUSIC="music";
	public static String MESSAGE_IMAGE="image";
	public static String MESSAGE_VOICE="voice";
	public static String MESSAGE_VIDEO="video";
	public static String MESSAGE_LINK="link";
	//注意微信开发文档有错，地理位置类型是location;
	public static String MESSAGE_LOCATION="location";
	public static String MESSAGE_EVENT="event";
	public static String MESSAGE_SUBSCRIBE="subscribe";
	public static String MESSAGE_UNSUBSCRIBE="unsubscribe";
	public static String MESSAGE_CLICK="CLICK";
	public static String MESSAGE_VIEW="VIEW";
	public static String MESSAGE_SCANCODE="scancode_push";
	
	
	
	/*
	 * xml 转化为Map
	 */
	public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
		Map<String,String>map=new HashMap<String, String>();
		SAXReader reader =new SAXReader();
		
		InputStream ins=request.getInputStream();
		Document doc=reader.read(ins);
		Element root=doc.getRootElement();
		List<Element> list=root.elements();
		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}
	/*
	 * Map转化为xml(文本消息)
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream=new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	/*
	 *拼接字符串，主菜单Text
	 *
	 */
	  	public static String menuText(){
	  		StringBuffer sb=new StringBuffer();
	  		sb.append("欢迎你的操作，请按照菜单进行操作：\n\n");
	  		sb.append("1、前端开发\n");
	  		sb.append("2、java开发\n");
	  		sb.append("回复?调出此菜单。");
	  		return sb.toString();
	  	}
	  	/*
		 *拼接文本消息，主菜单Text
		 *
		 */
	  	public static String initText(String toUsername,String fromUserName,String content){
	  		TextMessage text=new TextMessage();
			text.setFromUserName(toUsername);
			text.setToUserName(fromUserName);
			text.setMsgType(MessageUtil.MESSAGE_TEXT);
			text.setCreateTime(new Date().getTime());
			text.setContent(content);
			return MessageUtil.textMessageToXml(text);
	  	}
	  	/*
		 *回复
		 *
		 */
	  	public static String fristMenu(){
	  		StringBuffer sb=new StringBuffer();
	  		sb.append("本套课程介绍前端编程开发");
	  		return sb.toString();
	  	}
	  	public static String secondMenu(){
	  		StringBuffer sb=new StringBuffer();
	  		sb.append("本套课程介绍java编程开发");
	  		return sb.toString();
	  	}
	  	
	  	
	  	/*
		 * Map(图文消息)转化为xml
		 */
		public static String newsMessageToXml(NewsMessage newsMessage){
			XStream xstream=new XStream();
			xstream.alias("xml", newsMessage.getClass());
			xstream.alias("item", new News().getClass());
			return xstream.toXML(newsMessage);
		}
		/*
		 * Map(图片消息)转化为xml
		 */
		public static String imageMessageToXml(ImageMessage imageMessage){
			XStream xstream=new XStream();
			xstream.alias("xml", imageMessage.getClass());
			return xstream.toXML(imageMessage);
		}
		/*
		 * Map(音乐消息)转化为xml
		 */
		public static String musicMessageToXml(MusicMessage musicMessage){
			XStream xstream=new XStream();
			xstream.alias("xml", musicMessage.getClass());
			return xstream.toXML(musicMessage);
		}
		/*
		 *拼接图文消息
		 *
		 */
	  	public static String initNewsMessage(String toUsername,String fromUserName){
	  		String message =null;
	  		List<News> newsList=new ArrayList<News>();
	  		NewsMessage newsMessage=new NewsMessage();
	  		//多图文采用创建多个news对象，然后添加到newsList中
	  		News news=new News();
	  		news.setTitle("前端介绍");
	  		news.setDescription("前端学习与交流平台");
	  		news.setPicUrl("http://dahai.tunnel.qydev.com/demo/image/imooc.jpg");
	  		news.setUrl("www.imooc.com");
	  		newsList.add(news);
	  		
	  		
	  		newsMessage.setToUserName(fromUserName);
	  		newsMessage.setFromUserName(toUsername);
	  		newsMessage.setCreateTime(new Date().getTime());
	  		newsMessage.setMsgType(MESSAGE_NEWS);
	  		newsMessage.setArticles(newsList);
	  		newsMessage.setArticleCount(newsList.size());
	  		message=newsMessageToXml(newsMessage);
	  		return message;
	  	}
	  	/*
		 *拼接图片消息
		 *
		 */
	  	public static String initImageMessage(String toUserName,String fromUserName){
			String message = null;
			Image image = new Image();
			image.setMediaId("bhppaBBnWnJtNqOFlM6v5zY51V-dT8fJ2a1rcD0BXYgezlZDm2DVW6Xpx5Mj8KWm");
			ImageMessage imageMessage = new ImageMessage();
			imageMessage.setFromUserName(toUserName);
			imageMessage.setToUserName(fromUserName);
			imageMessage.setMsgType(MESSAGE_IMAGE);
			imageMessage.setCreateTime(new Date().getTime());
			imageMessage.setImage(image);
			message = imageMessageToXml(imageMessage);
			return message;
		}
	  	/*
		 *拼接音乐消息
		 *
		 */
	  	public static String initMusicMessage(String toUserName,String fromUserName){
			String message = null;
			Music music = new Music();
			music.setThumbMediaId("5qjCLZNsznI4JRg9ytkmQKZSFB4djIZQUfocuQzS5KHaE1hy_iom8zidC_9UasgS");
			music.setTitle("see you again");
			music.setDescription("速度与激情7片尾曲");
			music.setMusicUrl("http://dahai.tunnel.qydev.com/demo/resource/See.mp3");
			music.setHQMusicUrl("http://dahai.tunnel.qydev.com/demo/resource/See.mp3");
			MusicMessage musicMessage = new MusicMessage();
			musicMessage.setFromUserName(toUserName);
			musicMessage.setToUserName(fromUserName);
			musicMessage.setMsgType(MESSAGE_MUSIC);
			musicMessage.setCreateTime(new Date().getTime());
			musicMessage.setMusic(music);
			message = musicMessageToXml(musicMessage);
			return message;
		}
}
