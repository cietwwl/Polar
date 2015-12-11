package com.game.activities.message;

import com.game.activities.bean.ActivityInfo;
import com.game.message.Message;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * 向世界服请求领取消息
 */
public class ReqGetRewardToWorldMessage extends Message{

	//角色ID
	private long playerid;
	
	//活动信息
	private ActivityInfo activityinfo;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(IoBuffer buf){
		//角色ID
		writeLong(buf, this.playerid);
		//活动信息
		writeBean(buf, this.activityinfo);
		return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(IoBuffer buf){
		//角色ID
		this.playerid = readLong(buf);
		//活动信息
		this.activityinfo = (ActivityInfo)readBean(buf, ActivityInfo.class);
		return true;
	}
	
	/**
	 * get 角色ID
	 * @return 
	 */
	public long getPlayerid(){
		return playerid;
	}
	
	/**
	 * set 角色ID
	 */
	public void setPlayerid(long playerid){
		this.playerid = playerid;
	}
	
	/**
	 * get 活动信息
	 * @return 
	 */
	public ActivityInfo getActivityinfo(){
		return activityinfo;
	}
	
	/**
	 * set 活动信息
	 */
	public void setActivityinfo(ActivityInfo activityinfo){
		this.activityinfo = activityinfo;
	}
	
	
	@Override
	public int getId() {
		return 138302;
	}

	@Override
	public String getQueue() {
		return null;
	}
	
	@Override
	public String getServer() {
		return null;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//角色ID
		buf.append("playerid:" + playerid +",");
		//活动信息
		if(this.activityinfo!=null) buf.append("activityinfo:" + activityinfo.toString() +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}