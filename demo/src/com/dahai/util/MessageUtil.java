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
	 * Mapת��Ϊxml
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream=new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	/*
	 *ƴ���ַ��������˵�
	 *
	 */
	  	public static String menuText(){
	  		StringBuffer sb=new StringBuffer();
	  		sb.append("��ӭ��Ĳ������밴�ղ˵����в�����\n\n");
	  		sb.append("1��ǰ�˿���\n");
	  		sb.append("1��java����\n");
	  		sb.append("�ظ�?�����˲˵���");
	  		return sb.toString();
	  	}
	  	/*
		 *ƴ���ı���Ϣ�����˵�
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
}
