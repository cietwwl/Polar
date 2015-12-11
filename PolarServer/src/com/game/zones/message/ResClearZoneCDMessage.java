package com.game.zones.message;

import com.game.message.Message;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * 翻牌-副本通关奖励信息消息
 */
public class ResClearZoneCDMessage extends Message{

	
	//副本ID
		private int zoneid;
		/**
		 * 写入字节缓存
		 */
		public boolean write(IoBuffer buf){
			//副本ID
			writeInt(buf, this.zoneid);
			return true;
		}
		
		/**
		 * 读取字节缓存
		 */
		public boolean read(IoBuffer buf){
			//副本ID
			this.zoneid = readInt(buf);
			return true;
		}
		
		
		/**
		 * get 副本ID
		 * @return 
		 */
		public int getZoneid(){
			return zoneid;
		}
		
		/**
		 * set 副本ID
		 */
		public void setZoneid(int zoneid){
			this.zoneid = zoneid;
		}
	
	
	@Override
	public int getId() {
		return 528319;
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
		buf.append("zoneid:" + zoneid +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}