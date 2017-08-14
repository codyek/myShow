package com.btcdata.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.btcdata.constants.URLConstant;
import com.btcdata.util.RestClient;

@Service
public class OkexService {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * 获取最新成交价
	 * @param symbol  btc \ ltc
	 * @param type    合约周期
	 * @return
	 */
	public Object getOkexPrice(String symbol, String type){
		String msg = getOkexData(symbol,type);
		if(!StringUtils.isEmpty(msg)){
			JSONObject json = JSONObject.parseObject(msg);
			if(null != json.get("ticker")){
				JSONObject ticker = json.getJSONObject("ticker");
				if(null != ticker.get("last")){
					return ticker.get("last");
				}
			}
		}
		log.error(">> symbol="+symbol+",type="+type+"  return null!");
		return null;
	}
	
	private String getOkexData(String symbol, String type){
		String url = URLConstant.OKEX_URL;
		url = url.replace("{0}", symbol).replace("{1}", type);
		RestTemplate restTemplate = RestClient.getClient();
		String msg = restTemplate.getForEntity(url, String.class).getBody();
		log.debug(">> return  msg="+msg);
		return msg;
	}
}
