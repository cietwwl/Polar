package com.game.guild.message;

import com.game.message.Message;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * 通知世界服务器修改职权消息
 */
public class ReqInnerGuildChangePowerLevelToWorldMessage extends Message{

	//角色Id
	private long playerId;
	
	//战盟Id
	private long guildId;
	
	//被操作玩家ID
	private long userId;
	
	//新职权
	private byte newPowerLevel;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(IoBuffer buf){
		//角色Id
		writeLong(buf, this.playerId);
		//战盟Id
		writeLong(buf, this.guildId);
		//被操作玩家ID
		writeLong(buf, this.userId);
		//新职权
		writeByte(buf, this.newPowerLevel);
		return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(IoBuffer buf){
		//角色Id
		this.playerId = readLong(buf);
		//战盟Id
		this.guildId = readLong(buf);
		//被操作玩家ID
		this.userId = readLong(buf);
		//新职权
		this.newPowerLevel = readByte(buf);
		return true;
	}
	
	/**
	 * get 角色Id
	 * @return 
	 */
	public long getPlayerId(){
		return playerId;
	}
	
	/**
	 * set 角色Id
	 */
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	
	/**
	 * get 战盟Id
	 * @return 
	 */
	public long getGuildId(){
		return guildId;
	}
	
	/**
	 * set 战盟Id
	 */
	public void setGuildId(long guildId){
		this.guildId = guildId;
	}
	
	/**
	 * get 被操作玩家ID
	 * @return 
	 */
	public long getUserId(){
		return userId;
	}
	
	/**
	 * set 被操作玩家ID
	 */
	public void setUserId(long userId){
		this.userId = userId;
	}
	
	/**
	 * get 新职权
	 * @return 
	 */
	public byte getNewPowerLevel(){
		return newPowerLevel;
	}
	
	/**
	 * set 新职权
	 */
	public void setNewPowerLevel(byte newPowerLevel){
		this.newPowerLevel = newPowerLevel;
	}
	
	
	@Override
	public int getId() {
		return 121310;
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
		//角色Id
		buf.append("playerId:" + playerId +",");
		//战盟Id
		buf.append("guildId:" + guildId +",");
		//被操作玩家ID
		buf.append("userId:" + userId +",");
		//新职权
		buf.append("newPowerLevel:" + newPowerLevel +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}