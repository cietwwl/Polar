package com.game.transactions.handler;

import org.apache.log4j.Logger;

import com.game.manager.ManagerPool;
import com.game.player.structs.Player;
import com.game.transactions.message.ReqAutorefusaldealMessage;
import com.game.command.Handler;

public class ReqAutorefusaldealHandler extends Handler{

	Logger log = Logger.getLogger(ReqAutorefusaldealHandler.class);

	public void action(){
		try{
			ReqAutorefusaldealMessage msg = (ReqAutorefusaldealMessage)this.getMessage();
			
			ManagerPool.transactionsManager.stAutorefusaldeal((Player)this.getParameter(),msg.getState());
		}catch(ClassCastException e){
			log.error(e);
		}
	}
}