package com.example.demo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class GreetingController {

	@MessageMapping("/hello")
	@SendTo("/topic/brocast")
	public Greeting brocast(@Header("xmtopic") String topic, @Headers Map<String, Object> headers,
			HelloMessage message) throws Exception {
		Thread.sleep(1000); // simulated delay
		System.out.println(topic);
		System.out.println(headers);
		return new Greeting("brocast: " + message.getName() + " says hello!");
	}

	@MessageMapping("/message")
	@SendToUser(value = "/topic/single", broadcast = false) // --> maps to "/user/topic/single" --> /user/xm/topic/single
	public Greeting single(@Payload String payload) {
		System.out.println("");
		return new Greeting(payload);
	}
	
	@Autowired  
    private SimpMessageSendingOperations simpMessageSendingOperations;  

	@RequestMapping(path = "/send2", method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(value = HttpStatus.OK)
	public void send2(String toName,String text) {
		simpMessageSendingOperations.convertAndSendToUser(toName, "/topic/single",
				new Greeting(text));
	}
	
	@RequestMapping(path = "/send", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Greeting send(String toName,String text) {
		simpMessageSendingOperations.convertAndSendToUser(toName, "/topic/single",
				new Greeting(text));
		return new Greeting(text);
	}
}
