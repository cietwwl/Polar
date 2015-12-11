package com.game.backpack.message;

import com.game.message.Message;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * 钻石变化消息
 */
public class ResGoldChangeMessage extends Message{

	//钻石数量
	private int gold;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(IoBuffer buf){
		//钻石数量
		writeInt(buf, this.gold);
		return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(IoBuffer buf){
		//钻石数量
		this.gold = readInt(buf);
		return true;
	}
	
	/**
	 * get 钻石数量
	 * @return 
	 */
	public int getGold(){
		return gold;
	}
	
	/**
	 * set 钻石数量
	 */
	public void setGold(int gold){
		this.gold = gold;
	}
	
	
	@Override
	public int getId() {
		return 104106;
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
		//钻石数量
		buf.append("gold:" + gold +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}