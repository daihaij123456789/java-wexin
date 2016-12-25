package com.dahai.servlet;

import java.awt.Checkbox;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.dahai.po.TextMessage;
import com.dahai.util.CheckUtil;
import com.dahai.util.MessageUtil;

public class WexinServlet extends HttpServlet {
	@Override
	/*
	 * 验证
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String signature=req.getParameter("signature");
		String timestamp=req.getParameter("timestamp");
		String nonce=req.getParameter("nonce");
		String echostr=req.getParameter("echostr");
		PrintWriter out=resp.getWriter();
	
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		
	}
	@Override
	/*
	 * 消息响应
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		PrintWriter out=resp.getWriter();
		try {
			Map<String, String>map=MessageUtil.xmlToMap(req);
			String toUsername=map.get("ToUserName");
			String fromUserName=map.get("FromUserName");
			String msgType=map.get("MsgType");
			String content=map.get("Content");
			
			String message=null;
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				if ("1".equals(content)) {
					message=MessageUtil.initText(toUsername, fromUserName, MessageUtil.fristMenu());
				}else if("2".equals(content)){
					message=MessageUtil.initNewsMessage(toUsername, fromUserName);
				}else if("3".equals(content)){
					message=MessageUtil.initImageMessage(toUsername, fromUserName);
				}else if("4".equals(content)){
					message=MessageUtil.initMusicMessage(toUsername, fromUserName);
				}else if("?".equals(content)||"？".equals(content)){
					message=MessageUtil.initText(toUsername, fromUserName, MessageUtil.menuText());
				}
			}else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
				String eventType=map.get("Event");
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
					message=MessageUtil.initText(toUsername, fromUserName, MessageUtil.menuText());
				}
			}
			//System.out.println("message");
			out.print(message);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
}
