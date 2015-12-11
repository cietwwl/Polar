package com.game.store.message;

import java.util.List;
import java.util.ArrayList;
import com.game.message.Message;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * 物品信息列表消息
 */
public class ResStoreItemInfosMessage extends Message{

	//已开格子数量
	private int cellnum;
	
	//物品信息列表
	private List<com.game.backpack.bean.ItemInfo> items = new ArrayList<com.game.backpack.bean.ItemInfo>();
	
	/**
	 * 正在进行倒计时开启的仓库格子
	 */
	private short coolDowningGridId;
	/**
	 * 正在进行倒计时开启的仓库格子的结束时间，秒
	 */
	private int coolDowningGridTime;
	/**
	 * 写入字节缓存
	 */
	public boolean write(IoBuffer buf){
		//已开格子数量
		writeInt(buf, this.cellnum);
		//物品信息列表
		writeShort(buf, items.size());
		for (int i = 0; i < items.size(); i++) {
			writeBean(buf, items.get(i));
		}
//		writeShort(buf,coolDowningGridId);
//		writeInt(buf,coolDowningGridTime);
		return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(IoBuffer buf){
		//已开格子数量
		this.cellnum = readInt(buf);
		//物品信息列表
		int items_length = readShort(buf);
		for (int i = 0; i < items_length; i++) {
			items.add((com.game.backpack.bean.ItemInfo)readBean(buf, com.game.backpack.bean.ItemInfo.class));
		}
		return true;
	}
	
	/**
	 * get 已开格子数量
	 * @return 
	 */
	public int getCellnum(){
		return cellnum;
	}
	
	/**
	 * set 已开格子数量
	 */
	public void setCellnum(int cellnum){
		this.cellnum = cellnum;
	}
	
	/**
	 * get 物品信息列表
	 * @return 
	 */
	public List<com.game.backpack.bean.ItemInfo> getItems(){
		return items;
	}
	
	/**
	 * set 物品信息列表
	 */
	public void setItems(List<com.game.backpack.bean.ItemInfo> items){
		this.items = items;
	}
	
	
	/**
	 * @return the coolDowningGridId
	 */
	public short getCoolDowningGridId() {
		return coolDowningGridId;
	}

	/**
	 * @param coolDowningGridId the coolDowningGridId to set
	 */
	public void setCoolDowningGridId(short coolDowningGridId) {
		this.coolDowningGridId = coolDowningGridId;
	}

	/**
	 * @return the coolDowningGridTime
	 */
	public int getCoolDowningGridTime() {
		return coolDowningGridTime;
	}

	/**
	 * @param coolDowningGridTime the coolDowningGridTime to set
	 */
	public void setCoolDowningGridTime(int coolDowningGridTime) {
		this.coolDowningGridTime = coolDowningGridTime;
	}

	@Override
	public int getId() {
		return 112100;
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
		//已开格子数量
		buf.append("cellnum:" + cellnum +",");
		//物品信息列表
		buf.append("items:{");
		for (int i = 0; i < items.size(); i++) {
			buf.append(items.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}