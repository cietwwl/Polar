package com.game.stalls.message;

import com.game.message.Message;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * 打开摊位列表面板消息
 */
public class ReqStallsOpenUpToWorldMessage extends Message{

	//角色ID
	private long playerid;
	
	//索引起点
	private int indexlittle;
	
	//索引终点
	private int indexLarge;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(IoBuffer buf){
		//角色ID
		writeLong(buf, this.playerid);
		//索引起点
		writeInt(buf, this.indexlittle);
		//索引终点
		writeInt(buf, this.indexLarge);
		return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(IoBuffer buf){
		//角色ID
		this.playerid = readLong(buf);
		//索引起点
		this.indexlittle = readInt(buf);
		//索引终点
		this.indexLarge = readInt(buf);
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
	 * get 索引起点
	 * @return 
	 */
	public int getIndexlittle(){
		return indexlittle;
	}
	
	/**
	 * set 索引起点
	 */
	public void setIndexlittle(int indexlittle){
		this.indexlittle = indexlittle;
	}
	
	/**
	 * get 索引终点
	 * @return 
	 */
	public int getIndexLarge(){
		return indexLarge;
	}
	
	/**
	 * set 索引终点
	 */
	public void setIndexLarge(int indexLarge){
		this.indexLarge = indexLarge;
	}
	
	
	@Override
	public int getId() {
		return 123301;
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
		//索引起点
		buf.append("indexlittle:" + indexlittle +",");
		//索引终点
		buf.append("indexLarge:" + indexLarge +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}