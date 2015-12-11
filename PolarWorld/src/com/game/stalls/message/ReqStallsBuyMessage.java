package com.game.stalls.message;

import com.game.message.Message;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * 购买商品消息
 */
public class ReqStallsBuyMessage extends Message{

	//摆摊的玩家ID
	private long playerid;
	
	//商品钻石价格
	private int pricegold;
	
	//商品钻石价格
	private int priceyuanbao;
	
	//商品道具唯一ID，-1金币，-2钻石
	private long goodsid;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(IoBuffer buf){
		//摆摊的玩家ID
		writeLong(buf, this.playerid);
		//商品钻石价格
		writeInt(buf, this.pricegold);
		//商品钻石价格
		writeInt(buf, this.priceyuanbao);
		//商品道具唯一ID，-1金币，-2钻石
		writeLong(buf, this.goodsid);
		return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(IoBuffer buf){
		//摆摊的玩家ID
		this.playerid = readLong(buf);
		//商品钻石价格
		this.pricegold = readInt(buf);
		//商品钻石价格
		this.priceyuanbao = readInt(buf);
		//商品道具唯一ID，-1金币，-2钻石
		this.goodsid = readLong(buf);
		return true;
	}
	
	/**
	 * get 摆摊的玩家ID
	 * @return 
	 */
	public long getPlayerid(){
		return playerid;
	}
	
	/**
	 * set 摆摊的玩家ID
	 */
	public void setPlayerid(long playerid){
		this.playerid = playerid;
	}
	
	/**
	 * get 商品钻石价格
	 * @return 
	 */
	public int getPricegold(){
		return pricegold;
	}
	
	/**
	 * set 商品钻石价格
	 */
	public void setPricegold(int pricegold){
		this.pricegold = pricegold;
	}
	
	/**
	 * get 商品钻石价格
	 * @return 
	 */
	public int getPriceyuanbao(){
		return priceyuanbao;
	}
	
	/**
	 * set 商品钻石价格
	 */
	public void setPriceyuanbao(int priceyuanbao){
		this.priceyuanbao = priceyuanbao;
	}
	
	/**
	 * get 商品道具唯一ID，-1金币，-2钻石
	 * @return 
	 */
	public long getGoodsid(){
		return goodsid;
	}
	
	/**
	 * set 商品道具唯一ID，-1金币，-2钻石
	 */
	public void setGoodsid(long goodsid){
		this.goodsid = goodsid;
	}
	
	
	@Override
	public int getId() {
		return 123204;
	}

	@Override
	public String getQueue() {
		return "Server";
	}
	
	@Override
	public String getServer() {
		return null;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//摆摊的玩家ID
		buf.append("playerid:" + playerid +",");
		//商品钻石价格
		buf.append("pricegold:" + pricegold +",");
		//商品钻石价格
		buf.append("priceyuanbao:" + priceyuanbao +",");
		//商品道具唯一ID，-1金币，-2钻石
		buf.append("goodsid:" + goodsid +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}