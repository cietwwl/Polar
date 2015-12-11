package com.game.ybcard.message;

import com.game.message.Message;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * 前端发起打开钻石卡消息
 */
public class ReqYBCardToGameMessage extends Message{

	//类型，0打开面板，1使用钻石卡，2领取钻石
	private byte type;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(IoBuffer buf){
		//类型，0打开面板，1使用钻石卡，2领取钻石
		writeByte(buf, this.type);
		return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(IoBuffer buf){
		//类型，0打开面板，1使用钻石卡，2领取钻石
		this.type = readByte(buf);
		return true;
	}
	
	/**
	 * get 类型，0打开面板，1使用钻石卡，2领取钻石
	 * @return 
	 */
	public byte getType(){
		return type;
	}
	
	/**
	 * set 类型，0打开面板，1使用钻石卡，2领取钻石
	 */
	public void setType(byte type){
		this.type = type;
	}
	
	
	@Override
	public int getId() {
		return 139201;
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
		//类型，0打开面板，1使用钻石卡，2领取钻石
		buf.append("type:" + type +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}