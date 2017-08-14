package com.btcdata.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btcdata.constants.URLConstant;
import com.btcdata.service.BitmexService;
import com.btcdata.service.OkexService;

@RestController
@RequestMapping("/btc")
public class OkexController {

	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private OkexService okexService;
	
	@Autowired
	private BitmexService mexService;
	
	@RequestMapping("getBtc")
    public String getBtcQuarter(){
		log.info(">>>11111111111111111");
		return "mex = "+mexService.getBitMexPrice(URLConstant.BITMEX_SYMBOL_XBTUSD);
		//return "btc = "+okexService.getOkexPrice(URLConstant.OKEX_SYMBOL_BTC, URLConstant.OKEX_TYPE_QUARTER);
	}
	
	
	
	/*@RequestMapping("getMex")
    public String getMexQuarter(){
		return ""+mexService.getBitMexPrice(URLConstant.BITMEX_SYMBOL_XBTUSD);
	}*/
}
