package com.game.team.handler;

import org.apache.log4j.Logger;

import com.game.command.Handler;
import com.game.manager.ManagerPool;
import com.game.team.message.ReqAppointWorldMessage;

/**
 * @author xiaozhuoming
 * 
 * @since 2013-11-28
 *
 */
public class ReqAppointWorldHandler extends Handler{

	Logger log = Logger.getLogger(ReqAppointWorldHandler.class);

	public void action(){
		try{
			ReqAppointWorldMessage msg = (ReqAppointWorldMessage)this.getMessage();
			ManagerPool.teamManager.stReqAppoint(msg.getTeamid(), msg.getPlayerid(), msg.getCaptainid());
		}catch(ClassCastException e){
			log.error(e);
		}
	}
}