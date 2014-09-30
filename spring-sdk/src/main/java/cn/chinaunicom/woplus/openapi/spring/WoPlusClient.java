package cn.chinaunicom.woplus.openapi.spring;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class WoPlusClient {
	static org.apache.log4j.Logger logger=org.apache.log4j.Logger.getLogger(WoPlusClient.class);
	
	private RestTemplate restTemplate;
	//private String appKey;
	//private String appSecret;
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		
		this.restTemplate.setErrorHandler(new ResponseErrorHandler(){

			public boolean hasError(ClientHttpResponse response)
					throws IOException {
				// TODO Auto-generated method stub
				return false;
			}

			public void handleError(ClientHttpResponse response)
					throws IOException {
				// TODO Auto-generated method stub
			}});
		
	}
	
	/**
	 * @param appKey 		应用标识
	 * @param appSecret 	应用密钥
	 * */
	public WoPlusClient(String appKey,String appSecret){
		Authenticate.getInstance().config(appKey, appSecret).setHttpClient(this);
	}
	
	/**
	 * @param api_url API接口地址
	 * @param params 参数集
	 * @return 返回值 @see WoPlusResponse
	 * @exception 
	 * */
	public WoPlusResponse post(String api_url,HashMap<String,Object> params) throws Exception{
		
		HashMap<String,String> auth = Authenticate.getInstance().getAuthorization();
		if(auth==null) throw new Exception("Token is Null");
		
		String secretKey=Authenticate.getInstance().getAppKey()+"&"+Authenticate.getInstance().getAppSecret();
		String signature = EMCSign.signValue(params, secretKey);
		
		params.put("signType", "HMAC-SHA1");
		params.put("signature", signature);
		
		return postJSONEntity(api_url,auth,params);
		
	}
	
	/**
	 * @param api_url API接口地址
	 * @param params 参数集
	 * @param platform 平台ID
	 * @param password 平台密码
	 * 
	 * @return 返回值 @see WoPlusResponse
	 * @exception 
	 * */
	public WoPlusResponse post(String api_url,HashMap<String,Object> params,String platform,String password) throws Exception{
		
		HashMap<String,String> auth = Authenticate.getAuthorizationWithPlatform(platform, password);
		
		String secretKey=platform+"&"+password;
		String signature = EMCSign.signValue(params, secretKey);
		
		params.put("signType", "HMAC-SHA1");
		params.put("signature", signature);
		
		return postJSONEntity(api_url,auth,params);
		
	}
	/**
	 * @param api_url API接口地址
	 * @param params 参数集
	 * @return 返回值 @see WoPlusResponse
	 * @exception 
	 * */
	public WoPlusResponse get(String api_url,HashMap<String,Object> params) throws Exception{
		
		HashMap<String,String> auth = Authenticate.getInstance().getAuthorization();
		if(auth==null) throw new Exception("Token is Null");
		
		return getJSONEntity(api_url,auth,params);
		
	}
	
	
//	public String getAppKey() {
//		return appKey;
//	}
//	public void setAppKey(String appKey) {
//		this.appKey = appKey;
//	}
//	public String getAppSecret() {
//		return appSecret;
//	}
//	public void setAppSecret(String appSecret) {
//		this.appSecret = appSecret;
//	}
	
	
	private static synchronized WoPlusResponse _transObject(@SuppressWarnings("rawtypes") Map obj){
		
		WoPlusResponse resp = new WoPlusResponse();
		
		HashMap<String,Object> msg=new HashMap<String,Object>();
		
		for(Object k:obj.keySet()){
			if(k.toString().equals("resultCode")) 
				resp.resultCode=obj.get(k).toString();
			else if(k.toString().equals("resultDescription"))
				resp.resultDescription=obj.get(k).toString();
			else
				msg.put(k.toString(), obj.get(k));
		}
		resp.content=msg;
		
		return resp;
		
	}

	
	WoPlusResponse postJSONEntity(String api_url,final HashMap<String,String> auth,HashMap<String,Object> params){
		
		HttpHeaders requestHeaders = new HttpHeaders(); 
		
		if(auth!=null && auth.values().size()>0){
			StringBuilder sb=new StringBuilder();
			for(String k : auth.keySet()){
				sb.append(",")
				.append(k)
				.append("=\"")
				.append(auth.get(k))
				.append("\"");
			}
			requestHeaders.set("Authorization",sb.toString().substring(1));
		}
		requestHeaders.set("Content-Type", "application/json;charset=utf-8");
		requestHeaders.set("Accept","application/json");
		
		@SuppressWarnings("rawtypes")
		HttpEntity<Map> requestEntity = new HttpEntity<Map>(params,requestHeaders); 
		
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> response = 
				restTemplate.exchange(api_url, 
						HttpMethod.POST, 
						requestEntity, 
						Map.class); 
			
		if(response!=null && response.getBody()!=null){
			logger.debug(response.getBody());
			return _transObject(response.getBody());
			
		}
		else
			return null;
		
		
	}

	WoPlusResponse getJSONEntity(String api_url,HashMap<String,String> auth,HashMap<String,Object> params) throws Exception{
		
		HttpHeaders requestHeaders = new HttpHeaders(); 
		
		if(auth!=null && auth.values().size()>0){
			StringBuilder sb=new StringBuilder();
			for(String k : auth.keySet()){
				sb.append(",")
				.append(k)
				.append("=\"")
				.append(auth.get(k))
				.append("\"");
			}
			requestHeaders.set("Authorization",sb.toString().substring(1));
		}
		
		requestHeaders.set("Content-Type", "application/json;charset=utf-8");
		requestHeaders.set("Accept","application/json");
		
		StringBuilder sb = new StringBuilder();
		sb.append(api_url);
		sb.append("?");
		for(String key:params.keySet()){
			sb.append(key)
			.append("=")
			.append(params.get(key))
			.append("&");
		}
		String full_api_url = sb.toString().substring(0, sb.toString().lastIndexOf("&"));
		
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> response = restTemplate.exchange(full_api_url, HttpMethod.GET, null, Map.class);
		
		if(response!=null && response.getBody()!=null){
			logger.debug(response.getBody());
			return _transObject(response.getBody());
		}
		
		return null;
	}
	
}
