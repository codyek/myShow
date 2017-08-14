package com.btcdata.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btcdata.dao.DemoDao;
import com.btcdata.entity.DemoInfo;
import com.btcdata.service.DemoInfoInterface;
import com.btcdata.test.WebsocketClientEndpoint;

@RestController
@RequestMapping("/demo")
public class DemoController {

	@Autowired
	private DemoDao demoDao;
	
	@Autowired
    private DemoInfoInterface demoInfoInterface;
	
	@RequestMapping("save")
    public String save(){
       DemoInfo demoInfo = new DemoInfo();
       demoInfo.setName("张三");
       demoInfo.setAge(20);
       demoInfoInterface.save(demoInfo);
      
       demoInfo = new DemoInfo();
       demoInfo.setName("李四");
       demoInfo.setAge(30);
       demoInfoInterface.save(demoInfo);
      
       return "ok";
    }
	
	@RequestMapping("find")
    public List<DemoInfo> find(){
       return demoInfoInterface.findAll();
    }
   
    @RequestMapping("findByName")
    public DemoInfo findByName(String name){
       return demoInfoInterface.findByName(name);
    }
    
	@RequestMapping("saveDao")
	public String saveDao() {
		DemoInfo demoInfo = new DemoInfo();
		demoInfo.setName("张案例");
		demoInfo.setAge(21);
		demoDao.saveDemoInfo(demoInfo);
		return "ookk";
	}
	
	@RequestMapping("findDao")
    public DemoInfo findDao(String name){
		return demoDao.findUserByUserName(name);
	}
	
	@RequestMapping("websk")
    public String websk(String name){
		System.out.println("websk ====" + name);
		
		//String url = "wss://real.okcoin.cn:10440/websocket/okcoinapi";
		String url = "wss://www.bitmex.com/realtime";
		
		//String msg = "{'event':'addChannel','channel':'ok_btccny_ticker'}";
		//String msg = "{\"op\":\"help\"}";
		String msg = "{\"op\":\"subscribe\",\"args\":[\"orderBookL2:XBTUSD\"]}";
		try {
			// open websocket
			final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(
					new URI(url));

			// add listener
			clientEndPoint
					.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
						public void handleMessage(String message) {
							System.out.println("return =" + message);
						}
					});

			// send message to websocket
			clientEndPoint.sendMessage(msg);

			// wait 5 seconds for messages from websocket
			Thread.sleep(5000);

		} catch (InterruptedException ex) {
			System.err.println("InterruptedException exception: "
					+ ex.getMessage());
		} catch (URISyntaxException ex) {
			System.err.println("URISyntaxException exception: "
					+ ex.getMessage());
		}

		return "success";
	}
	
}
