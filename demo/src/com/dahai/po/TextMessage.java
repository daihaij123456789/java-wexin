package com.dahai.po;

public class TextMessage extends BaseMessage {
	private String Content;
	private String MsgId;
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
}
