package com.game.dataserver.handler;

import org.apache.log4j.Logger;

import com.game.command.Handler;

public class ResCheckTeamEnterToDataServerHandler extends Handler{

	Logger log = Logger.getLogger(ResCheckTeamEnterToDataServerHandler.class);

	public void action(){
		try{
			//公共数据服务器处理
		}catch(ClassCastException e){
			log.error(e);
		}
	}
}