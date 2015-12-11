package com.game.hiddenweapon.message;

import com.game.hiddenweapon.bean.HiddenWeaponInfo;
import com.game.message.Message;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * 返回客户端暗器信息消息
 */
public class ResHiddenWeaponInfoMessage extends Message{

	//暗器使用者ID
	private long playerid;
	
	//暗器信息
	private HiddenWeaponInfo info;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(IoBuffer buf){
		//暗器使用者ID
		writeLong(buf, this.playerid);
		//暗器信息
		writeBean(buf, this.info);
		return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(IoBuffer buf){
		//暗器使用者ID
		this.playerid = readLong(buf);
		//暗器信息
		this.info = (HiddenWeaponInfo)readBean(buf, HiddenWeaponInfo.class);
		return true;
	}
	
	/**
	 * get 暗器使用者ID
	 * @return 
	 */
	public long getPlayerid(){
		return playerid;
	}
	
	/**
	 * set 暗器使用者ID
	 */
	public void setPlayerid(long playerid){
		this.playerid = playerid;
	}
	
	/**
	 * get 暗器信息
	 * @return 
	 */
	public HiddenWeaponInfo getInfo(){
		return info;
	}
	
	/**
	 * set 暗器信息
	 */
	public void setInfo(HiddenWeaponInfo info){
		this.info = info;
	}
	
	
	@Override
	public int getId() {
		return 162101;
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
		//暗器使用者ID
		buf.append("playerid:" + playerid +",");
		//暗器信息
		if(this.info!=null) buf.append("info:" + info.toString() +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}