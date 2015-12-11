package com.game.guild.bean;

import java.util.List;
import java.util.ArrayList;

import com.game.message.Bean;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * 友好战盟信息
 */
public class FriendGuild extends Bean {

	//战盟id
	private long guildId;
	
	//友好战盟列表
	private List<Long> friendGuilds = new ArrayList<Long>();
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(IoBuffer buf){
		//战盟id
		writeLong(buf, this.guildId);
		//友好战盟列表
		writeShort(buf, friendGuilds.size());
		for (int i = 0; i < friendGuilds.size(); i++) {
			writeLong(buf, friendGuilds.get(i));
		}
		return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(IoBuffer buf){
		//战盟id
		this.guildId = readLong(buf);
		//友好战盟列表
		int friendGuilds_length = readShort(buf);
		for (int i = 0; i < friendGuilds_length; i++) {
			friendGuilds.add(readLong(buf));
		}
		return true;
	}
	
	/**
	 * get 战盟id
	 * @return 
	 */
	public long getGuildId(){
		return guildId;
	}
	
	/**
	 * set 战盟id
	 */
	public void setGuildId(long guildId){
		this.guildId = guildId;
	}
	
	/**
	 * get 友好战盟列表
	 * @return 
	 */
	public List<Long> getFriendGuilds(){
		return friendGuilds;
	}
	
	/**
	 * set 友好战盟列表
	 */
	public void setFriendGuilds(List<Long> friendGuilds){
		this.friendGuilds = friendGuilds;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//战盟id
		buf.append("guildId:" + guildId +",");
		//友好战盟列表
		buf.append("friendGuilds:{");
		for (int i = 0; i < friendGuilds.size(); i++) {
			buf.append(friendGuilds.get(i) +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}