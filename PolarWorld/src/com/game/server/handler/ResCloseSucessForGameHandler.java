package com.game.server.handler;

import org.apache.log4j.Logger;

import com.game.server.message.ResCloseSucessForGameMessage;
import com.game.command.Handler;

public class ResCloseSucessForGameHandler extends Handler{

	Logger log = Logger.getLogger(ResCloseSucessForGameHandler.class);

	public void action(){
		try{
			ResCloseSucessForGameMessage msg = (ResCloseSucessForGameMessage)this.getMessage();
			//TODO 添加消息处理
			
		}catch(ClassCastException e){
			log.error(e);
		}
	}
}