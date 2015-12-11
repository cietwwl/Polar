package com.game.mail.message{
	import net.Message;
	
	/** 
	 * @author Commuication Auto Maker
	 * 
	 * @version 1.0.0
	 * 
	 * @since 2011-5-8
	 * 
	 * 查询玩家返回
	 */
	public class ResMailQueryUserToClientMessage extends Message {
	
		//错误代码
		private var _btErrorCode: int;
		
		//查询玩家名字
		private var _szName: String;
		
		
		/**
		 * 写入字节缓存
		 */
		override protected function writing(): Boolean{
			//错误代码
			writeByte(_btErrorCode);
			//查询玩家名字
			writeString(_szName);
			return true;
		}
		
		/**
		 * 读取字节缓存
		 */
		override protected function reading(): Boolean{
			//错误代码
			_btErrorCode = readByte();
			//查询玩家名字
			_szName = readString();
			return true;
		}
		
		/**
		 * get id
		 * @return 
		 */
		override public function getId(): int {
			return 124106;
		}
		
		/**
		 * get 错误代码
		 * @return 
		 */
		public function get btErrorCode(): int{
			return _btErrorCode;
		}
		
		/**
		 * set 错误代码
		 */
		public function set btErrorCode(value: int): void{
			this._btErrorCode = value;
		}
		
		/**
		 * get 查询玩家名字
		 * @return 
		 */
		public function get szName(): String{
			return _szName;
		}
		
		/**
		 * set 查询玩家名字
		 */
		public function set szName(value: String): void{
			this._szName = value;
		}
		
	}
}