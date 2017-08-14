package com.btcdata.task;

import java.text.DecimalFormat;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.btcdata.entity.LtcTradeData;
import com.btcdata.service.LtcTradeDataInterface;
import com.btcdata.util.DateUtils;

@Component
public class LtcTaskGetPrice {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LtcAsyncGetPrice LtcasyncGetPrice;
	
	@Autowired
	private LtcTradeDataInterface ltcTradeData;
	
	@Autowired
	private AsyncGetPrice asyncGetPrice;
	
	/**
	 * 实时获取价格,每5秒获取一次
	 */
	@Async
	public void taskPrice() {
		try {
			Long time = DateUtils.getDateTimeInt();
			log.info("start task LTC  time =" +time);
	        long start = System.currentTimeMillis();
	        Future<Object> task1 = LtcasyncGetPrice.getOkPrice();
	        Future<Object> task2 = LtcasyncGetPrice.getMexLTCPrice();
	        // XBTUSD 
	        Future<Object> task3 = asyncGetPrice.getMexUSDPrice();
	        while(true) {
	    		if(task1.isDone() && task2.isDone()) {
	    			// 三个任务都调用完成，退出循环等待
	    			Object obj_okLtc = task1.get();
	    			Object obj_mexLtc = task2.get();
	    			Object obj_usd = task3.get();
	    			log.info(">> okLtc= "+ obj_okLtc +", mexLtc= "+ obj_mexLtc);
	    			double ok = null== obj_okLtc ? 0 : Double.parseDouble(obj_okLtc.toString());
	    			double mex = null== obj_mexLtc ? 0 : Double.parseDouble(obj_mexLtc.toString());
	    			double usd = null== obj_usd ? 0 : Double.parseDouble(obj_usd.toString());
	    			// mexLtc合约的美金价值=合约的比特币价值 * XBTUSD
	    			if(ok > 0 && mex > 0 && usd > 0){
	    				mex = get2Double(mex * usd);
	    				savePrice(time,ok,mex);
	    			}
	    			break;
	    		}
	    		// 等待0.5秒
	    		Thread.sleep(300);
	    	}
	        long end = System.currentTimeMillis();
	        log.info("over ltc time:" + (end - start) + " ms");
		} catch (Exception e) {
			log.error(" >>　taskPrice　error: "+e);
		}
	}
	
	/**
	 * 保存交易价格
	 * @param time
	 * @param ok
	 * @param usd
	 * @param u17
	 */
	private void savePrice(Long time, double ok, double mex){
		LtcTradeData data = new LtcTradeData();
		data.setTime(time);
		data.setOkLTCPrice(ok);
		data.setMexLTCPrice(mex);
		data.setOkToMexAgio(get2Double(ok - mex));
		ltcTradeData.save(data);
	}
	
	//保留2位小数  
	public static double get2Double(double a){  
	    DecimalFormat df=new DecimalFormat("#.00");  
	    return new Double(df.format(a).toString());  
	}  
}
