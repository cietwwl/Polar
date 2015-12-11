package com.game.country.handler;

import org.apache.log4j.Logger;

import com.game.country.message.ReqCountryWarCarInAdvanceToGameMessage;
import com.game.command.Handler;
import com.game.manager.ManagerPool;
import com.game.player.structs.Player;

public class ReqCountryWarCarInAdvanceToGameHandler extends Handler{

	Logger log = Logger.getLogger(ReqCountryWarCarInAdvanceToGameHandler.class);

	public void action(){
		try{
			ReqCountryWarCarInAdvanceToGameMessage msg = (ReqCountryWarCarInAdvanceToGameMessage)this.getMessage();
			ManagerPool.countryManager.stReqCountryWarCarInAdvanceToGameMessage((Player)this.getParameter(),msg);
			
		}catch(ClassCastException e){
			log.error(e);
		}
	}
}