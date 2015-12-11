package com.game.login.message;

import com.game.message.Message;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * 登陆玩家失败消息
 */
public class ResLoginCharacterFailedMessage extends Message{

	//服务器编号
	private int createServerId;
	
	//用户id
	private String userId;
	
	//失败原因
	private byte reason;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(IoBuffer buf){
		//服务器编号
		writeInt(buf, this.createServerId);
		//用户id
		writeString(buf, this.userId);
		//失败原因
		writeByte(buf, this.reason);
		return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(IoBuffer buf){
		//服务器编号
		this.createServerId = readInt(buf);
		//用户id
		this.userId = readString(buf);
		//失败原因
		this.reason = readByte(buf);
		return true;
	}
	
	/**
	 * get 服务器编号
	 * @return 
	 */
	public int getCreateServerId(){
		return createServerId;
	}
	
	/**
	 * set 服务器编号
	 */
	public void setCreateServerId(int createServerId){
		this.createServerId = createServerId;
	}
	
	/**
	 * get 用户id
	 * @return 
	 */
	public String getUserId(){
		return userId;
	}
	
	/**
	 * set 用户id
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	/**
	 * get 失败原因
	 * @return 
	 */
	public byte getReason(){
		return reason;
	}
	
	/**
	 * set 失败原因
	 */
	public void setReason(byte reason){
		this.reason = reason;
	}
	
	
	@Override
	public int getId() {
		return 100311;
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
		//服务器编号
		buf.append("createServerId:" + createServerId +",");
		//用户id
		if(this.userId!=null) buf.append("userId:" + userId.toString() +",");
		//失败原因
		buf.append("reason:" + reason +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}