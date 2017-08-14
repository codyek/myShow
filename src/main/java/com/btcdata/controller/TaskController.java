package com.btcdata.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.btcdata.entity.BtcTradeData;
import com.btcdata.entity.LtcTradeData;
import com.btcdata.service.BtcTradeDataInterface;
import com.btcdata.service.LtcTradeDataInterface;
import com.btcdata.util.DateUtils;

@Controller
@RequestMapping(value = "/task")
public class TaskController {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BtcTradeDataInterface btcTradeData;
	
	@Autowired
	private LtcTradeDataInterface ltcTradeData;

	@RequestMapping(value = "showPrice")
	public String priceBtc(Model model, String temp) throws Exception {
		log.info(">>> showPrice BTC  input="+ temp);
		if("btcTask".equals(temp)){
	        return "showBtc";
		}else{
			return "errMsg";
		}
		
	}
	
	@RequestMapping(value = "showPriceLtc")
	public String priceLtc(Model model, String temp) throws Exception {
		log.info(">>> showPrice LTC  input="+ temp);
		if("ltcTask".equals(temp)){
	        return "showLtc";
		}else{
			return "errMsg";
		}
		
	}
	
	@RequestMapping(value = "postPrice")
	@ResponseBody
	public JSONArray taskPostBtc(String startDt, String endDt) throws Exception {
		log.info(">>>-- postPrice BTC  input="+ startDt + " , " +endDt);
		if(null != startDt && null != endDt){
			long start = Long.parseLong(
					DateUtils.formatDate(
							DateUtils.parseDate(startDt), "yyyyMMddHHmmss"));
			long end = Long.parseLong(
					DateUtils.formatDate(
							DateUtils.parseDate(endDt), "yyyyMMddHHmmss"));
			log.info(">>>-- postPrice Btc  start="+ start + " , end=" +end);
			Direction direction = Direction.ASC;
			List<BtcTradeData> list = btcTradeData.findByTimeBetween(start, end,new Sort(direction,"time"));
			JSONArray arr = JSONArray.parseArray(JSON.toJSONString(list));
	        return arr;
		}else{
			return null;
		}
	}
	
	@RequestMapping(value = "postPriceLtc")
	@ResponseBody
	public JSONArray taskPostLtc(String startDt, String endDt) throws Exception {
		log.info(">>>-- postPrice LTC  input="+ startDt + " , " +endDt);
		if(null != startDt && null != endDt){
			long start = Long.parseLong(
					DateUtils.formatDate(
							DateUtils.parseDate(startDt), "yyyyMMddHHmmss"));
			long end = Long.parseLong(
					DateUtils.formatDate(
							DateUtils.parseDate(endDt), "yyyyMMddHHmmss"));
			log.info(">>>-- postPrice LTC  start="+ start + " , end=" +end);
			Direction direction = Direction.ASC;
			List<LtcTradeData> list = ltcTradeData.findByTimeBetween(start, end,new Sort(direction,"time"));
			JSONArray arr = JSONArray.parseArray(JSON.toJSONString(list));
	        return arr;
		}else{
			return null;
		}
	}
}
