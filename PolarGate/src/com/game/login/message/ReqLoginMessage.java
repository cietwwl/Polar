package com.game.login.message;

import com.game.message.Message;

import org.apache.mina.core.buffer.IoBuffer;

/** 
 * @author Commuication Auto Maker
 * 
 * @version 1.0.0
 * 
 * 登陆消息
 */
public class ReqLoginMessage extends Message{

	//服务器Id
	private int serverId;
	
	//玩家用户名
	private String name;
	
	//玩家用户名
	private String password;
	
	
	/**
	 * 写入字节缓存
	 */
	public boolean write(IoBuffer buf){
		//服务器Id
		writeInt(buf, this.serverId);
		//玩家用户名
		writeString(buf, this.name);
		//玩家用户名
		writeString(buf, this.password);
		return true;
	}
	
	/**
	 * 读取字节缓存
	 */
	public boolean read(IoBuffer buf){
		//服务器Id
		this.serverId = readInt(buf);
		//玩家用户名
		this.name = readString(buf);
		//玩家用户名
		this.password = readString(buf);
		return true;
	}
	
	/**
	 * get 服务器Id
	 * @return 
	 */
	public int getServerId(){
		return serverId;
	}
	
	/**
	 * set 服务器Id
	 */
	public void setServerId(int serverId){
		this.serverId = serverId;
	}
	
	/**
	 * get 玩家用户名
	 * @return 
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * set 玩家用户名
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * get 玩家用户名
	 * @return 
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * set 玩家用户名
	 */
	public void setPassword(String password){
		this.password = password;
	}
	
	
	@Override
	public int getId() {
		return 100201;
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
		//服务器Id
		buf.append("serverId:" + serverId +",");
		//玩家用户名
		if(this.name!=null) buf.append("name:" + name.toString() +",");
		//玩家用户名
		if(this.password!=null) buf.append("password:" + password.toString() +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}
//package com.game.login.message;
//
//import java.io.IOException;
//
//import com.game.message.Message;
//import com.game.message.PBMessage;
//import com.game.qmm.pb.login.ReqLoginMsgProto;
//import com.google.protobuf.InvalidProtocolBufferException;
//
//import org.apache.mina.core.buffer.IoBuffer;
//
///**
// * @author Commuication Auto Maker
// * 
// * @version 1.0.0
// * 
// *          登陆消息
// */
//public class ReqLoginMessage extends Message {
//
//	@Override
//	public int getId() {
//		return 100201;
//	}
//
//	// 服务器Id
//	private int serverId;
//
//	// 玩家用户名
//	private String name;
//
//	// 玩家密码
//	private String password;
//
//	/**
//	 * 写入字节缓存
//	 * 
//	 * @throws IOException
//	 */
//	public boolean write(IoBuffer buf) {
//		try {
//			ReqLoginMsgProto.ReqLoginMsg.Builder msgbuild = ReqLoginMsgProto.ReqLoginMsg
//					.newBuilder();
//			msgbuild.setServerId(this.serverId);
//			msgbuild.setName(this.name);
//			msgbuild.setPassword(this.password);
//			PBMessage pkg = PBMessage.buildMessage(msgbuild);
//			buf = PBMessage.encode(pkg);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return true;
//
//		// //服务器Id
//		// writeInt(buf, this.serverId);
//		// //玩家用户名
//		// writeString(buf, this.name);
//		// //玩家用户名
//		// writeString(buf, this.password);
//
//		// Message msg = pkg.getMessage();
//		// int size = 20;
//		// if (msg != null) {
//		// size = 20 + msg.getSerializedSize();
//		// }
//		// //长度不得大于 32767
//		// if (size > 32767) {
//		// return null;
//		// }
//		// IoBuffer bufferout = IoBuffer.allocate(size);
//		// pkg.writeHeader(size, bufferout);
//		// if (msg != null) {
//		// msg.writeTo(buffer.asOutputStream());
//		// }
//	}
//
//	/**
//	 * 读取字节缓存
//	 * 
//	 * @throws InvalidProtocolBufferException
//	 */
//	public boolean read(IoBuffer buf) {
//		try {
//			ReqLoginMsgProto.ReqLoginMsg reqLogin = ReqLoginMsgProto.ReqLoginMsg
//					.parseFrom(PBMessage.decode(buf));
//			// 服务器Id
//			this.serverId = reqLogin.getServerId();
//			// 玩家用户名
//			this.name = reqLogin.getName();
//			// 玩家用户名
//			this.password = reqLogin.getPassword();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return true;
//		/*
//		 * 
//		 */
//		// PBMessage packet = PBMessage.buildPBMessage();
//		// int bodyLen = readInt(buf);
//		// if (bodyLen > 0) {
//		// byte[] bytes = new byte[bodyLen];
//		// buf.get(bytes, 0, bodyLen);
//		// packet.setBytes(bytes);
//		// }else{
//		// return false;
//		// }
//		/*
//		 * int bodyLen = readInt(buf) - 12; if (bodyLen < 0) { return false; }
//		 * byte[] bytes = new byte[bodyLen]; buf.get(bytes, 0, bodyLen);
//		 */
//
//		// ReqLoginMsgProto.ReqLoginMsg reqLogin =
//		// ReqLoginMsgProto.ReqLoginMsg.parseFrom(PBMessage.decode(buf));
//		// //服务器Id
//		// this.serverId = reqLogin.getServerId();
//		// //玩家用户名
//		// this.name = reqLogin.getName();
//		// //玩家用户名
//		// this.password = reqLogin.getPassword();
//	}
//
//	/**
//	 * get 服务器Id
//	 * 
//	 * @return
//	 */
//	public int getServerId() {
//		return serverId;
//	}
//
//	/**
//	 * set 服务器Id
//	 */
//	public void setServerId(int serverId) {
//		this.serverId = serverId;
//	}
//
//	/**
//	 * get 玩家用户名
//	 * 
//	 * @return
//	 */
//	public String getName() {
//		return name;
//	}
//
//	/**
//	 * set 玩家用户名
//	 */
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	/**
//	 * get 玩家用户名
//	 * 
//	 * @return
//	 */
//	public String getPassword() {
//		return password;
//	}
//
//	/**
//	 * set 玩家用户名
//	 */
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//
//	@Override
//	public String getQueue() {
//		return null;
//	}
//
//	@Override
//	public String getServer() {
//		return null;
//	}
//
//	@Override
//	public String toString() {
//		StringBuffer buf = new StringBuffer("[");
//		// 服务器Id
//		buf.append("serverId:" + serverId + ",");
//		// 玩家用户名
//		if (this.name != null)
//			buf.append("name:" + name.toString() + ",");
//		// 玩家用户名
//		if (this.password != null)
//			buf.append("password:" + password.toString() + ",");
//		if (buf.charAt(buf.length() - 1) == ',')
//			buf.deleteCharAt(buf.length() - 1);
//		buf.append("]");
//		return buf.toString();
//	}
//}