package cn.chinaunicom.woplus.openapi.spring.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.chinaunicom.woplus.openapi.spring.WoPlusClient;
import cn.chinaunicom.woplus.openapi.spring.WoPlusResponse;

@Controller
public class DemoController {

	private static final String platformID="501e9618-4305-4834-8847-d8044f8bad28";
	private static final String password ="sharetop1234";
	
	@Autowired
	WoPlusClient woplusClient;
	
	@RequestMapping(value="/api/getchannelpaymentsms",method=RequestMethod.POST,headers = {"content-type=application/json;charset=utf-8", "Accept=application/json"})
	public @ResponseBody WoPlusResponse getchannelpaymentsms(){
		
		WoPlusResponse resp = new WoPlusResponse();
		
		String api_url="http://open.wo.com.cn/openapi/getchannelpaymentsms/v1.0";
		
		HashMap<String,Object> params = new HashMap<String,Object>();
		
		long num=new Random().nextLong();
		params.put("outTradeNo",Long.toString(num));
		params.put("timeStamp", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		params.put("subject", "金币一堆");
		params.put("totalFee", 0.05f);
		params.put("callbackUrl", "http://test.com:8080/notifycallback");
		params.put("appKey", "000000001");
		params.put("appName", "APP");
		
		try {
			resp = woplusClient.post(api_url, params, platformID, password);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return resp;
	}
	
}
