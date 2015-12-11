package com.game.horse.handler;

import org.apache.log4j.Logger;

import com.game.horse.message.ReqhorseStageUpStartMessage;
import com.game.manager.ManagerPool;
import com.game.player.structs.Player;
import com.game.command.Handler;

/**
 * 
 * @author xiaozhuoming
 * 
 * @since 2013-11-16
 *
 */
public class ReqhorseStageUpStartHandler extends Handler{

	Logger log = Logger.getLogger(ReqhorseStageUpStartHandler.class);

	public void action(){
		try{
			ReqhorseStageUpStartMessage msg = (ReqhorseStageUpStartMessage)this.getMessage();
			ManagerPool.horseManager.stReqhorseStageUpStartMessage((Player)this.getParameter(),msg.getType());
			
		}catch(ClassCastException e){
			log.error(e);
		}
	}
}