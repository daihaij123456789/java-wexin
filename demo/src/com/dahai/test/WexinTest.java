package com.dahai.test;

import net.sf.json.JSONObject;

import com.dahai.po.AccessToken;
import com.dahai.util.WexinUtil;

public class WexinTest {
	public static void main(String[] args) {
		try {
			AccessToken token = WexinUtil.getAccessToken();
			System.out.println("票据"+token.getToken());
			System.out.println("有效时间"+token.getExpiresIn());
			
			//String path = "D:/imooc1.jpg";
			//String mediaId = WexinUtil.upload(path, token.getToken(), "thumb");
			//System.out.println(mediaId);
			
			
			String menu=JSONObject.fromObject(WexinUtil.initMenu()).toString();	
			int result=WexinUtil.createMenu(token.getToken(), menu);
			
			if (result==0) {
				System.out.println("创建菜单成功");
			}else{
				System.out.println("错误码"+result);
			}
			
			
			//JSONObject jsonObject=WexinUtil.queryMenu(token.getToken());
			//System.out.println(jsonObject);
			
			/*int result=WexinUtil.deleteMenu(token.getToken());
			if(result==0){
				System.out.println("删除成功");
			}else{
				System.out.println("错误码"+result);
			}*/
			//String result = WexinUtil.translate("my name is laobi");
			//String result = WeixinUtil.translateFull("");
			//System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}