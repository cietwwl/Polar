package com.game.player.message;

import com.game.message.Message;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * 得到玩家造型信息ToWorld消息
 */
public class ReqGetPlayerAppearanceInfoToWorldMessage extends Message{

	//玩家本人id
	private long playerId;
	
	//他人ID
	private long othersid;
	
	//面板类型
	private byte type;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(IoBuffer buf){
		//玩家本人id
		writeLong(buf, this.playerId);
		//他人ID
		writeLong(buf, this.othersid);
		//面板类型
		writeByte(buf, this.type);
		return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(IoBuffer buf){
		//玩家本人id
		this.playerId = readLong(buf);
		//他人ID
		this.othersid = readLong(buf);
		//面板类型
		this.type = readByte(buf);
		return true;
	}
	
	/**
	 * get 玩家本人id
	 * @return 
	 */
	public long getPlayerId(){
		return playerId;
	}
	
	/**
	 * set 玩家本人id
	 */
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	
	/**
	 * get 他人ID
	 * @return 
	 */
	public long getOthersid(){
		return othersid;
	}
	
	/**
	 * set 他人ID
	 */
	public void setOthersid(long othersid){
		this.othersid = othersid;
	}
	
	/**
	 * get 面板类型
	 * @return 
	 */
	public byte getType(){
		return type;
	}
	
	/**
	 * set 面板类型
	 */
	public void setType(byte type){
		this.type = type;
	}
	
	
	@Override
	public int getId() {
		return 103312;
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
		//玩家本人id
		buf.append("playerId:" + playerId +",");
		//他人ID
		buf.append("othersid:" + othersid +",");
		//面板类型
		buf.append("type:" + type +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}