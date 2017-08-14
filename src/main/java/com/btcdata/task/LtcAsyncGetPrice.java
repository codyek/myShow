package com.btcdata.task;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.btcdata.constants.URLConstant;
import com.btcdata.service.BitmexService;
import com.btcdata.service.OkexService;

@Component
public class LtcAsyncGetPrice {

	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private OkexService okexService;

	@Autowired
	private BitmexService mexService;

	/**
	 * 异步获取 OKex LTC实时价格
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	@Async
	public Future<Object> getOkPrice() throws InterruptedException {
		log.debug(" >> start getOkPrice LTC");
		Object price = okexService.getOkexPrice(URLConstant.OKEX_SYMBOL_LTC,
				URLConstant.OKEX_TYPE_QUARTER);
		return new AsyncResult<>(price);
	}

	/**
	 * 异步获取 mex LTC 实时价格
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	@Async
	public Future<Object> getMexLTCPrice() throws InterruptedException {
		log.debug(" >> start getMexLTCPrice LTC");
		Object price = mexService.getBitMexPrice(URLConstant.BITMEX_SYMBOL_LTC);
		return new AsyncResult<>(price);
	}

}
