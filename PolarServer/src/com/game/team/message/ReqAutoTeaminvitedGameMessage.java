package com.game.team.message;

import com.game.message.Message;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * 自动组队邀请消息
 */
public class ReqAutoTeaminvitedGameMessage extends Message{

	//0手动，1自动邀请
	private byte autoTeaminvited;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(IoBuffer buf){
		//0手动，1自动邀请
		writeByte(buf, this.autoTeaminvited);
		return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(IoBuffer buf){
		//0手动，1自动邀请
		this.autoTeaminvited = readByte(buf);
		return true;
	}
	
	/**
	 * get 0手动，1自动邀请
	 * @return 
	 */
	public byte getAutoTeaminvited(){
		return autoTeaminvited;
	}
	
	/**
	 * set 0手动，1自动邀请
	 */
	public void setAutoTeaminvited(byte autoTeaminvited){
		this.autoTeaminvited = autoTeaminvited;
	}
	
	
	@Override
	public int getId() {
		return 118209;
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
		//0手动，1自动邀请
		buf.append("autoTeaminvited:" + autoTeaminvited +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}