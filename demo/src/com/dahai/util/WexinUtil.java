package com.dahai.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.dahai.menu.Button;
import com.dahai.menu.ClickButton;
import com.dahai.menu.Menu;
import com.dahai.menu.ViewButton;
import com.dahai.po.AccessToken;
/*
import com.dahai.menu.Button;
import com.dahai.menu.ClickButton;
import com.dahai.menu.Menu;
import com.dahai.menu.ViewButton;
import com.dahai.po.AccessToken;
import com.dahai.trans.Data;
import com.dahai.trans.Parts;
import com.dahai.trans.Symbols;
import com.dahai.trans.TransResult;*/
/*
 * GET 请求
 * 
 */
public class WexinUtil {
	private static final String PREFIX="https://api.weixin.qq.com/cgi-bin";
	//测试号信息
	private static final String APPID="wx9226474d248fd396";
	private static final String APPSECRET="e022d368d3010321e9af508dac8c66da";
	//票据接口
	private static final String ACCESS_TOKEN_URL = PREFIX+"/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	//消息接口
	private static final String UPLOAD_URL =PREFIX+ "/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	//菜单推送接口
	private static final String CREATE_MENU_URL=PREFIX+"/menu/create?access_token=ACCESS_TOKEN";
	//菜单查询接口
	private static final String QUERY_MENU_URL=PREFIX+"/menu/get?access_token=ACCESS_TOKEN";
	//菜单删除接口
	private static final String DELETE_MENU_URL=PREFIX+"/menu/delete?access_token=ACCESS_TOKEN";
	public static JSONObject doGetStr(String url) throws ParseException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		HttpResponse httpResponse = client.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();
		if(entity != null){
			String result = EntityUtils.toString(entity,"UTF-8");
			jsonObject = JSONObject.fromObject(result);
		}
		return jsonObject;
	}
	/*
	 * POST  请求
	 * 
	 */
	public static JSONObject doPostStr(String url,String outStr) throws ParseException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		JSONObject jsonObject = null;
		httpost.setEntity(new StringEntity(outStr,"UTF-8"));
		HttpResponse response = client.execute(httpost);
		String result = EntityUtils.toString(response.getEntity(),"UTF-8");
		jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}
	/***
	 * 获取access_token
	 * @return
	 */
	public static AccessToken getAccessToken() throws ParseException, IOException{
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject =  doGetStr(url);
		if(jsonObject!=null){
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return token;
	}
	
	public static String upload(String filePath, String accessToken,String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}

		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);
		
		URL urlObj = new URL(url);
		//链接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST"); 
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); 

		//设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		//设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		//获取输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		//输出表头
		out.write(head);

		//文件正文部分
		//把文件已读流文件的方式推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		//结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			//定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		JSONObject jsonObj = JSONObject.fromObject(result);
		System.out.println(jsonObj);
		String typeName = "media_id";
		if(!"image".equals(type)){
			typeName = type + "_media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}
/*
 * 组装菜单
 * @return
 */
	public static Menu initMenu(){
		Menu menu=new Menu();
		ClickButton button11=new ClickButton();
		button11.setName("click菜单");
		button11.setType("click");
		button11.setKey("11");
		
		ViewButton button21=new ViewButton();
		button21.setName("view 菜单");
		button21.setType("view");
		button21.setUrl("http://www.imooc.com");
		
		ClickButton button31=new ClickButton();
		button31.setName("扫描菜单");
		button31.setType("scancode_push");
		button31.setKey("31");
		
		ClickButton button41=new ClickButton();
		button41.setName("地理位置");
		button41.setType("location_select");
		button41.setKey("41");
		
		Button button =new Button();
		button.setName("菜单");
		button.setSub_button(new Button[]{button31,button41});
		
		menu.setButton(new Button[]{button11,button21,button});
		return menu;
	}

	/*
	 * 获取菜单与推送接口
	 * @return
	 */
	public static int createMenu(String token,String menu) throws ParseException, IOException{
		int result=0;
		String url=CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject=doPostStr(url, menu);
		if (jsonObject  !=null) {
			result=jsonObject.getInt("errcode");
		}
		return result;
	}
	/*
	 * 获取查询菜单
	 * @return
	 */
	public static JSONObject queryMenu(String token) throws ParseException, IOException{
		String url=QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject=doGetStr(url);
		return jsonObject;
	}
	/*
	 * 删除查询菜单
	 * @return
	 */
	public static int deleteMenu(String token) throws ParseException, IOException{
		int result=0;
		String url=DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject=doGetStr(url);
		if (jsonObject!=null) {
			result=jsonObject.getInt("errcode");
		}
		return result;
	}
}
	
