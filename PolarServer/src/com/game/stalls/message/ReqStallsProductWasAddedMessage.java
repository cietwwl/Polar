package com.game.stalls.message;

import com.game.message.Message;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * 商品上架消息
 */
public class ReqStallsProductWasAddedMessage extends Message{

	//道具唯一ID，-1金币，-2钻石
	private long goodsid;
	
	//金币价格
	private int pricegold;
	
	//钻石价格
	private int priceyuanbao;
	
	//要上架的道具数量
	private int num;
	
	//商品总数
	private int goodsnum;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(IoBuffer buf){
		//道具唯一ID，-1金币，-2钻石
		writeLong(buf, this.goodsid);
		//金币价格
		writeInt(buf, this.pricegold);
		//钻石价格
		writeInt(buf, this.priceyuanbao);
		//要上架的道具数量
		writeInt(buf, this.num);
		//商品总数
		writeInt(buf, this.goodsnum);
		return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(IoBuffer buf){
		//道具唯一ID，-1金币，-2钻石
		this.goodsid = readLong(buf);
		//金币价格
		this.pricegold = readInt(buf);
		//钻石价格
		this.priceyuanbao = readInt(buf);
		//要上架的道具数量
		this.num = readInt(buf);
		//商品总数
		this.goodsnum = readInt(buf);
		return true;
	}
	
	/**
	 * get 道具唯一ID，-1金币，-2钻石
	 * @return 
	 */
	public long getGoodsid(){
		return goodsid;
	}
	
	/**
	 * set 道具唯一ID，-1金币，-2钻石
	 */
	public void setGoodsid(long goodsid){
		this.goodsid = goodsid;
	}
	
	/**
	 * get 金币价格
	 * @return 
	 */
	public int getPricegold(){
		return pricegold;
	}
	
	/**
	 * set 金币价格
	 */
	public void setPricegold(int pricegold){
		this.pricegold = pricegold;
	}
	
	/**
	 * get 钻石价格
	 * @return 
	 */
	public int getPriceyuanbao(){
		return priceyuanbao;
	}
	
	/**
	 * set 钻石价格
	 */
	public void setPriceyuanbao(int priceyuanbao){
		this.priceyuanbao = priceyuanbao;
	}
	
	/**
	 * get 要上架的道具数量
	 * @return 
	 */
	public int getNum(){
		return num;
	}
	
	/**
	 * set 要上架的道具数量
	 */
	public void setNum(int num){
		this.num = num;
	}
	
	/**
	 * get 商品总数
	 * @return 
	 */
	public int getGoodsnum(){
		return goodsnum;
	}
	
	/**
	 * set 商品总数
	 */
	public void setGoodsnum(int goodsnum){
		this.goodsnum = goodsnum;
	}
	
	
	@Override
	public int getId() {
		return 123205;
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
		//道具唯一ID，-1金币，-2钻石
		buf.append("goodsid:" + goodsid +",");
		//金币价格
		buf.append("pricegold:" + pricegold +",");
		//钻石价格
		buf.append("priceyuanbao:" + priceyuanbao +",");
		//要上架的道具数量
		buf.append("num:" + num +",");
		//商品总数
		buf.append("goodsnum:" + goodsnum +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}