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
public class AsyncGetPrice {

	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private OkexService okexService;

	@Autowired
	private BitmexService mexService;

	@Autowired
	private BitmexService mexService2;

	/**
	 * 异步获取 OKex 实时价格
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	@Async
	public Future<Object> getOkPrice() throws InterruptedException {
		log.debug(" >> start getOkPrice ");
		Object price = okexService.getOkexPrice(URLConstant.OKEX_SYMBOL_BTC,
				URLConstant.OKEX_TYPE_QUARTER);
		return new AsyncResult<>(price);
	}

	/**
	 * 异步获取 mex USD 实时价格
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	@Async
	public Future<Object> getMexUSDPrice() throws InterruptedException {
		log.debug(" >> start getMexUSDPrice ");
		Object price = mexService
				.getBitMexPrice(URLConstant.BITMEX_SYMBOL_XBTUSD);
		return new AsyncResult<>(price);
	}

	/**
	 * 异步获取 mex U17 实时价格
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	@Async
	public Future<Object> getMexU17Price() throws InterruptedException {
		log.debug(" >> start getMexU17Price ");
		Object price = mexService2
				.getBitMexPrice(URLConstant.BITMEX_SYMBOL_XBTU17);
		return new AsyncResult<>(price);
	}
}
