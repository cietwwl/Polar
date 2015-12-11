package com.game.stalls.message;

import com.game.message.Message;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * 商品调整，返回原始数据到GAME(暂时无用)消息
 */
public class ReqStallsAdjustPricesCheckToGameMessage extends Message{

	//摆摊的玩家ID
	private long stallsplayerid;
	
	//道具唯一ID，-1金币，-2钻石
	private long goodsid;
	
	//道具模版ID，-1金币，-2钻石
	private int goodsmodelid;
	
	//金币价格
	private int pricegold;
	
	//钻石价格
	private int priceyuanbao;
	
	//新位置
	private int pos;
	
	//新的数量
	private int num;
	
	//原始数量
	private int currentnum;
	
	//世界服务器商品item序列化（发到GAME验证用）
	private String goodsitem;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(IoBuffer buf){
		//摆摊的玩家ID
		writeLong(buf, this.stallsplayerid);
		//道具唯一ID，-1金币，-2钻石
		writeLong(buf, this.goodsid);
		//道具模版ID，-1金币，-2钻石
		writeInt(buf, this.goodsmodelid);
		//金币价格
		writeInt(buf, this.pricegold);
		//钻石价格
		writeInt(buf, this.priceyuanbao);
		//新位置
		writeInt(buf, this.pos);
		//新的数量
		writeInt(buf, this.num);
		//原始数量
		writeInt(buf, this.currentnum);
		//世界服务器商品item序列化（发到GAME验证用）
		writeString(buf, this.goodsitem);
		return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(IoBuffer buf){
		//摆摊的玩家ID
		this.stallsplayerid = readLong(buf);
		//道具唯一ID，-1金币，-2钻石
		this.goodsid = readLong(buf);
		//道具模版ID，-1金币，-2钻石
		this.goodsmodelid = readInt(buf);
		//金币价格
		this.pricegold = readInt(buf);
		//钻石价格
		this.priceyuanbao = readInt(buf);
		//新位置
		this.pos = readInt(buf);
		//新的数量
		this.num = readInt(buf);
		//原始数量
		this.currentnum = readInt(buf);
		//世界服务器商品item序列化（发到GAME验证用）
		this.goodsitem = readString(buf);
		return true;
	}
	
	/**
	 * get 摆摊的玩家ID
	 * @return 
	 */
	public long getStallsplayerid(){
		return stallsplayerid;
	}
	
	/**
	 * set 摆摊的玩家ID
	 */
	public void setStallsplayerid(long stallsplayerid){
		this.stallsplayerid = stallsplayerid;
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
	 * get 道具模版ID，-1金币，-2钻石
	 * @return 
	 */
	public int getGoodsmodelid(){
		return goodsmodelid;
	}
	
	/**
	 * set 道具模版ID，-1金币，-2钻石
	 */
	public void setGoodsmodelid(int goodsmodelid){
		this.goodsmodelid = goodsmodelid;
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
	 * get 新位置
	 * @return 
	 */
	public int getPos(){
		return pos;
	}
	
	/**
	 * set 新位置
	 */
	public void setPos(int pos){
		this.pos = pos;
	}
	
	/**
	 * get 新的数量
	 * @return 
	 */
	public int getNum(){
		return num;
	}
	
	/**
	 * set 新的数量
	 */
	public void setNum(int num){
		this.num = num;
	}
	
	/**
	 * get 原始数量
	 * @return 
	 */
	public int getCurrentnum(){
		return currentnum;
	}
	
	/**
	 * set 原始数量
	 */
	public void setCurrentnum(int currentnum){
		this.currentnum = currentnum;
	}
	
	/**
	 * get 世界服务器商品item序列化（发到GAME验证用）
	 * @return 
	 */
	public String getGoodsitem(){
		return goodsitem;
	}
	
	/**
	 * set 世界服务器商品item序列化（发到GAME验证用）
	 */
	public void setGoodsitem(String goodsitem){
		this.goodsitem = goodsitem;
	}
	
	
	@Override
	public int getId() {
		return 123319;
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
		//摆摊的玩家ID
		buf.append("stallsplayerid:" + stallsplayerid +",");
		//道具唯一ID，-1金币，-2钻石
		buf.append("goodsid:" + goodsid +",");
		//道具模版ID，-1金币，-2钻石
		buf.append("goodsmodelid:" + goodsmodelid +",");
		//金币价格
		buf.append("pricegold:" + pricegold +",");
		//钻石价格
		buf.append("priceyuanbao:" + priceyuanbao +",");
		//新位置
		buf.append("pos:" + pos +",");
		//新的数量
		buf.append("num:" + num +",");
		//原始数量
		buf.append("currentnum:" + currentnum +",");
		//世界服务器商品item序列化（发到GAME验证用）
		if(this.goodsitem!=null) buf.append("goodsitem:" + goodsitem.toString() +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}