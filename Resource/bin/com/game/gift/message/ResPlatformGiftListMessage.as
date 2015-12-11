package com.game.gift.message{
	import com.game.gift.bean.GiftInfo;
	import net.Message;
	
	/** 
	 * @author Commuication Auto Maker
	 * 
	 * @version 1.0.0
	 * 
	 * @since 2011-5-8
	 * 
	 * 发送平台礼包信息
	 */
	public class ResPlatformGiftListMessage extends Message {
	
		//礼包信息列表
		private var _gifts: Vector.<GiftInfo> = new Vector.<GiftInfo>();
		//面板标识
		private var _tag: String;
		
		
		/**
		 * 写入字节缓存
		 */
		override protected function writing(): Boolean{
			var i: int = 0;
			//礼包信息列表
			writeShort(_gifts.length);
			for (i = 0; i < _gifts.length; i++) {
				writeBean(_gifts[i]);
			}
			//面板标识
			writeString(_tag);
			return true;
		}
		
		/**
		 * 读取字节缓存
		 */
		override protected function reading(): Boolean{
			var i: int = 0;
			//礼包信息列表
			var gifts_length : int = readShort();
			for (i = 0; i < gifts_length; i++) {
				_gifts[i] = readBean(GiftInfo) as GiftInfo;
			}
			//面板标识
			_tag = readString();
			return true;
		}
		
		/**
		 * get id
		 * @return 
		 */
		override public function getId(): int {
			return 129103;
		}
		
		/**
		 * get 礼包信息列表
		 * @return 
		 */
		public function get gifts(): Vector.<GiftInfo>{
			return _gifts;
		}
		
		/**
		 * set 礼包信息列表
		 */
		public function set gifts(value: Vector.<GiftInfo>): void{
			this._gifts = value;
		}
		
		/**
		 * get 面板标识
		 * @return 
		 */
		public function get tag(): String{
			return _tag;
		}
		
		/**
		 * set 面板标识
		 */
		public function set tag(value: String): void{
			this._tag = value;
		}
		
	}
}