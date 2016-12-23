package com.dahai.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dahai.po.TextMessage;
import com.sun.org.apache.xerces.internal.xs.XSTerm;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {
	public static String MESSAGE_TEXT="text";
	public static String MESSAGE_IMAGE="image";
	public static String MESSAGE_VOICE="voice";
	public static String MESSAGE_VIDEO="video";
	public static String MESSAGE_LINK="link";
	public static String MESSAGE_LOCATION="location";
	public static String MESSAGE_EVENT="event";
	public static String MESSAGE_SUBSCRIBE="subscribe";
	public static String MESSAGE_UNSUBSCRIBE="unsubscribe";
	public static String MESSAGE_CLICK="CLICK";
	public static String MESSAGE_VIEM="VIEM";
	
	
	
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
	 * Map转化为xml
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream=new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	/*
	 *拼接字符串，主菜单
	 *
	 */
	  	public static String menuText(){
	  		StringBuffer sb=new StringBuffer();
	  		sb.append("欢迎你的操作，请按照菜单进行操作：\n\n");
	  		sb.append("1、前端开发\n");
	  		sb.append("1、java开发\n");
	  		sb.append("回复?调出此菜单。");
	  		return sb.toString();
	  	}
	  	/*
		 *拼接文本消息，主菜单
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
}
