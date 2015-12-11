package com.game.marriage.message;

import com.game.message.Message;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * 配偶在线状态消息
 */
public class ResOnlineStatusToClientMessage extends Message{

	//在线状态，1上线，2下线
	private byte type;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(IoBuffer buf){
		//在线状态，1上线，2下线
		writeByte(buf, this.type);
		return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(IoBuffer buf){
		//在线状态，1上线，2下线
		this.type = readByte(buf);
		return true;
	}
	
	/**
	 * get 在线状态，1上线，2下线
	 * @return 
	 */
	public byte getType(){
		return type;
	}
	
	/**
	 * set 在线状态，1上线，2下线
	 */
	public void setType(byte type){
		this.type = type;
	}
	
	
	@Override
	public int getId() {
		return 163120;
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
		//在线状态，1上线，2下线
		buf.append("type:" + type +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}