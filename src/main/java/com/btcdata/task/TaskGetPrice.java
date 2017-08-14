package com.btcdata.task;

import java.text.DecimalFormat;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.btcdata.entity.BtcTradeData;
import com.btcdata.service.BtcTradeDataInterface;
import com.btcdata.util.DateUtils;

@Component
public class TaskGetPrice {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AsyncGetPrice asyncGetPrice;
	
	@Autowired
	private BtcTradeDataInterface btcTradeData;
	
	/**
	 * 实时获取价格,每5秒获取一次
	 */
	@Async
	public void taskPrice() {
		try {
			Long time = DateUtils.getDateTimeInt();
			log.info("start BTC task time =" +time);
	        long start = System.currentTimeMillis();
	        Future<Object> task1 = asyncGetPrice.getOkPrice();
	        Future<Object> task2 = asyncGetPrice.getMexUSDPrice();
	        Future<Object> task3 = asyncGetPrice.getMexU17Price();
	        while(true) {
	    		if(task1.isDone() && task2.isDone() && task3.isDone()) {
	    			// 三个任务都调用完成，退出循环等待
	    			Object obj_ok = task1.get();
	    			Object obj_usd = task2.get();
	    			Object obj_u17 = task3.get();
	    			log.info(">> ok= "+ obj_ok +", usd= "+ obj_usd +", u17= "+ obj_u17);
	    			double ok = null== obj_ok ? 0 : Double.parseDouble(obj_ok.toString());
	    			double usd = null== obj_usd ? 0 : Double.parseDouble(obj_usd.toString());
	    			double u17 = null== obj_u17 ? 0 : Double.parseDouble(obj_u17.toString());
	    			if(ok > 0 && usd > 0 && u17 > 0){
	    				savePrice(time,ok,usd,u17);
	    			}
	    			break;
	    		}
	    		// 等待0.5秒
	    		Thread.sleep(300);
	    	}
	        long end = System.currentTimeMillis();
	        log.info("over Btc,time:" + (end - start) + " ms");
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
	private void savePrice(Long time, double ok, double usd, double u17){
		BtcTradeData data = new BtcTradeData();
		data.setTime(time);
		data.setOkPrice(ok);
		data.setMexUSDPrice(usd);
		data.setMexU17Price(u17);
		data.setOkToUSDAgio(get2Double(ok - usd));
		data.setOkToU17Agio(get2Double(ok - u17));
		data.setUsdToU17Agio(get2Double(usd - u17));
		btcTradeData.save(data);
	}
	
	//保留2位小数  
	public static double get2Double(double a){  
	    //DecimalFormat df=new DecimalFormat("0.00");  
	    DecimalFormat df=new DecimalFormat("#.00");  
	    return new Double(df.format(a).toString());  
	}  
}
