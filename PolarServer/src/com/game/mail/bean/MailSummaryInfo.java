package com.game.mail.bean;

import java.util.List;
import java.util.ArrayList;

import com.game.message.Bean;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * 邮件摘要
 */
public class MailSummaryInfo extends Bean {

	//邮件Id
	private long mailid;
	
	//发件人
	private String szSenderName;
	
	//邮件标题
	private String szTitle;
	
	//是否已读取
	private byte btRead;
	
	//是否有附件
	private byte btAccessory;
	
	//发送时间
	private int nSendTime;
	
	//是否系统邮件
	private byte btSystem;
	
	//邮件摘要物品列表
	private List<MailSummaryItem> summaryitemlist = new ArrayList<MailSummaryItem>();
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(IoBuffer buf){
		//邮件Id
		writeLong(buf, this.mailid);
		//发件人
		writeString(buf, this.szSenderName);
		//邮件标题
		writeString(buf, this.szTitle);
		//是否已读取
		writeByte(buf, this.btRead);
		//是否有附件
		writeByte(buf, this.btAccessory);
		//发送时间
		writeInt(buf, this.nSendTime);
		//是否系统邮件
		writeByte(buf, this.btSystem);
		//邮件摘要物品列表
		writeShort(buf, summaryitemlist.size());
		for (int i = 0; i < summaryitemlist.size(); i++) {
			writeBean(buf, summaryitemlist.get(i));
		}
		return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(IoBuffer buf){
		//邮件Id
		this.mailid = readLong(buf);
		//发件人
		this.szSenderName = readString(buf);
		//邮件标题
		this.szTitle = readString(buf);
		//是否已读取
		this.btRead = readByte(buf);
		//是否有附件
		this.btAccessory = readByte(buf);
		//发送时间
		this.nSendTime = readInt(buf);
		//是否系统邮件
		this.btSystem = readByte(buf);
		//邮件摘要物品列表
		int summaryitemlist_length = readShort(buf);
		for (int i = 0; i < summaryitemlist_length; i++) {
			summaryitemlist.add((MailSummaryItem)readBean(buf, MailSummaryItem.class));
		}
		return true;
	}
	
	/**
	 * get 邮件Id
	 * @return 
	 */
	public long getMailid(){
		return mailid;
	}
	
	/**
	 * set 邮件Id
	 */
	public void setMailid(long mailid){
		this.mailid = mailid;
	}
	
	/**
	 * get 发件人
	 * @return 
	 */
	public String getSzSenderName(){
		return szSenderName;
	}
	
	/**
	 * set 发件人
	 */
	public void setSzSenderName(String szSenderName){
		this.szSenderName = szSenderName;
	}
	
	/**
	 * get 邮件标题
	 * @return 
	 */
	public String getSzTitle(){
		return szTitle;
	}
	
	/**
	 * set 邮件标题
	 */
	public void setSzTitle(String szTitle){
		this.szTitle = szTitle;
	}
	
	/**
	 * get 是否已读取
	 * @return 
	 */
	public byte getBtRead(){
		return btRead;
	}
	
	/**
	 * set 是否已读取
	 */
	public void setBtRead(byte btRead){
		this.btRead = btRead;
	}
	
	/**
	 * get 是否有附件
	 * @return 
	 */
	public byte getBtAccessory(){
		return btAccessory;
	}
	
	/**
	 * set 是否有附件
	 */
	public void setBtAccessory(byte btAccessory){
		this.btAccessory = btAccessory;
	}
	
	/**
	 * get 发送时间
	 * @return 
	 */
	public int getNSendTime(){
		return nSendTime;
	}
	
	/**
	 * set 发送时间
	 */
	public void setNSendTime(int nSendTime){
		this.nSendTime = nSendTime;
	}
	
	/**
	 * get 是否系统邮件
	 * @return 
	 */
	public byte getBtSystem(){
		return btSystem;
	}
	
	/**
	 * set 是否系统邮件
	 */
	public void setBtSystem(byte btSystem){
		this.btSystem = btSystem;
	}
	
	/**
	 * get 邮件摘要物品列表
	 * @return 
	 */
	public List<MailSummaryItem> getSummaryitemlist(){
		return summaryitemlist;
	}
	
	/**
	 * set 邮件摘要物品列表
	 */
	public void setSummaryitemlist(List<MailSummaryItem> summaryitemlist){
		this.summaryitemlist = summaryitemlist;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//邮件Id
		buf.append("mailid:" + mailid +",");
		//发件人
		if(this.szSenderName!=null) buf.append("szSenderName:" + szSenderName.toString() +",");
		//邮件标题
		if(this.szTitle!=null) buf.append("szTitle:" + szTitle.toString() +",");
		//是否已读取
		buf.append("btRead:" + btRead +",");
		//是否有附件
		buf.append("btAccessory:" + btAccessory +",");
		//发送时间
		buf.append("nSendTime:" + nSendTime +",");
		//是否系统邮件
		buf.append("btSystem:" + btSystem +",");
		//邮件摘要物品列表
		buf.append("summaryitemlist:{");
		for (int i = 0; i < summaryitemlist.size(); i++) {
			buf.append(summaryitemlist.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}