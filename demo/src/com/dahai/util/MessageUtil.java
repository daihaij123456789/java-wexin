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
	//ע��΢�ſ����ĵ��д�����λ��������location;
	public static String MESSAGE_LOCATION="location";
	public static String MESSAGE_EVENT="event";
	public static String MESSAGE_SUBSCRIBE="subscribe";
	public static String MESSAGE_UNSUBSCRIBE="unsubscribe";
	public static String MESSAGE_CLICK="CLICK";
	public static String MESSAGE_VIEW="VIEW";
	public static String MESSAGE_SCANCODE="scancode_push";
	
	
	
	/*
	 * xml ת��ΪMap
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
	 * Mapת��Ϊxml(�ı���Ϣ)
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream=new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	/*
	 *ƴ���ַ��������˵�Text
	 *
	 */
	  	public static String menuText(){
	  		StringBuffer sb=new StringBuffer();
	  		sb.append("��ӭ��Ĳ������밴�ղ˵����в�����\n\n");
	  		sb.append("1��ǰ�˿���\n");
	  		sb.append("2��java����\n");
	  		sb.append("�ظ�?�����˲˵���");
	  		return sb.toString();
	  	}
	  	/*
		 *ƴ���ı���Ϣ�����˵�Text
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
		 *�ظ�
		 *
		 */
	  	public static String fristMenu(){
	  		StringBuffer sb=new StringBuffer();
	  		sb.append("���׿γ̽���ǰ�˱�̿���");
	  		return sb.toString();
	  	}
	  	public static String secondMenu(){
	  		StringBuffer sb=new StringBuffer();
	  		sb.append("���׿γ̽���java��̿���");
	  		return sb.toString();
	  	}
	  	
	  	
	  	/*
		 * Map(ͼ����Ϣ)ת��Ϊxml
		 */
		public static String newsMessageToXml(NewsMessage newsMessage){
			XStream xstream=new XStream();
			xstream.alias("xml", newsMessage.getClass());
			xstream.alias("item", new News().getClass());
			return xstream.toXML(newsMessage);
		}
		/*
		 * Map(ͼƬ��Ϣ)ת��Ϊxml
		 */
		public static String imageMessageToXml(ImageMessage imageMessage){
			XStream xstream=new XStream();
			xstream.alias("xml", imageMessage.getClass());
			return xstream.toXML(imageMessage);
		}
		/*
		 * Map(������Ϣ)ת��Ϊxml
		 */
		public static String musicMessageToXml(MusicMessage musicMessage){
			XStream xstream=new XStream();
			xstream.alias("xml", musicMessage.getClass());
			return xstream.toXML(musicMessage);
		}
		/*
		 *ƴ��ͼ����Ϣ
		 *
		 */
	  	public static String initNewsMessage(String toUsername,String fromUserName){
	  		String message =null;
	  		List<News> newsList=new ArrayList<News>();
	  		NewsMessage newsMessage=new NewsMessage();
	  		//��ͼ�Ĳ��ô������news����Ȼ����ӵ�newsList��
	  		News news=new News();
	  		news.setTitle("ǰ�˽���");
	  		news.setDescription("ǰ��ѧϰ�뽻��ƽ̨");
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
		 *ƴ��ͼƬ��Ϣ
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
		 *ƴ��������Ϣ
		 *
		 */
	  	public static String initMusicMessage(String toUserName,String fromUserName){
			String message = null;
			Music music = new Music();
			music.setThumbMediaId("5qjCLZNsznI4JRg9ytkmQKZSFB4djIZQUfocuQzS5KHaE1hy_iom8zidC_9UasgS");
			music.setTitle("see you again");
			music.setDescription("�ٶ��뼤��7Ƭβ��");
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
