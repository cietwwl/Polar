package com.game.guild.message{
	import net.Message;
	
	/** 
	 * @author Commuication Auto Maker
	 * 
	 * @version 1.0.0
	 * 
	 * @since 2011-5-8
	 * 
	 * 提交帮会贡献物品返回
	 */
	public class ResGuildSubmitItemToClientMessage extends Message {
	
		//错误代码
		private var _btErrorCode: int;
		
		
		/**
		 * 写入字节缓存
		 */
		override protected function writing(): Boolean{
			//错误代码
			writeByte(_btErrorCode);
			return true;
		}
		
		/**
		 * 读取字节缓存
		 */
		override protected function reading(): Boolean{
			//错误代码
			_btErrorCode = readByte();
			return true;
		}
		
		/**
		 * get id
		 * @return 
		 */
		override public function getId(): int {
			return 121114;
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
		
	}
}