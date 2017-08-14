package com.btcdata.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.btcdata.constants.URLConstant;
import com.btcdata.util.HttpUtils;

@Service
public class BitmexService {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * 获取最新成交价
	 * @param symbol  XBTUSD\ XBTU17\ LTCU17
	 * @return
	 */
	public Object getBitMexPrice(String symbol){
		String msg = getBitMexData(symbol);
		if(!StringUtils.isEmpty(msg)){
			JSONArray jArr = JSONArray.parseArray(msg);
			if(null != jArr && !jArr.isEmpty()){
				JSONObject json = jArr.getJSONObject(0);
				if(null != json.get("lastPrice")){
					return json.get("lastPrice");
				}
			}
		}
		log.error(">> symbol="+symbol+"  return null!");
		return null;
	}
	
	private String getBitMexData(String symbol){
		String url = URLConstant.BITMEX_URL;
		url = url.replace("{0}", symbol);
		String msg = null;
		try {
			msg = HttpUtils.httpsGet(url, new HashMap<>());
		} catch (Exception e) {
			log.error(" >> getBitMexData error="+e);
		}
		log.debug(">> return msg="+msg);
		return msg;
	}
}
