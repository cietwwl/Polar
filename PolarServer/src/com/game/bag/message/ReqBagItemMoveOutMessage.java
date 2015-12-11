package com.game.bag.message;

import com.game.message.Message;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * 物品从跨服包裹取出消息
 */
public class ReqBagItemMoveOutMessage extends Message{

	//包裹格子号(跨服包裹)
	private int cellId;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(IoBuffer buf){
		//包裹格子号(跨服包裹)
		writeInt(buf, this.cellId);
		return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(IoBuffer buf){
		//包裹格子号(跨服包裹)
		this.cellId = readInt(buf);
		return true;
	}
	
	/**
	 * get 包裹格子号(跨服包裹)
	 * @return 
	 */
	public int getCellId(){
		return cellId;
	}
	
	/**
	 * set 包裹格子号(跨服包裹)
	 */
	public void setCellId(int cellId){
		this.cellId = cellId;
	}
	
	
	@Override
	public int getId() {
		return 160203;
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
		//包裹格子号(跨服包裹)
		buf.append("cellId:" + cellId +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}