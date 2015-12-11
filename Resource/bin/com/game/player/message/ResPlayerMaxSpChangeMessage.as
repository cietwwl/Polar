package com.game.player.message{
	import com.game.utils.long;
	import net.Message;
	
	/** 
	 * @author Commuication Auto Maker
	 * 
	 * @version 1.0.0
	 * 
	 * @since 2011-5-8
	 * 
	 * 最大体力变化
	 */
	public class ResPlayerMaxSpChangeMessage extends Message {
	
		//角色Id
		private var _personId: long;
		
		//当前SP
		private var _currentSp: int;
		
		//最大SP
		private var _maxSp: int;
		
		
		/**
		 * 写入字节缓存
		 */
		override protected function writing(): Boolean{
			//角色Id
			writeLong(_personId);
			//当前SP
			writeInt(_currentSp);
			//最大SP
			writeInt(_maxSp);
			return true;
		}
		
		/**
		 * 读取字节缓存
		 */
		override protected function reading(): Boolean{
			//角色Id
			_personId = readLong();
			//当前SP
			_currentSp = readInt();
			//最大SP
			_maxSp = readInt();
			return true;
		}
		
		/**
		 * get id
		 * @return 
		 */
		override public function getId(): int {
			return 103111;
		}
		
		/**
		 * get 角色Id
		 * @return 
		 */
		public function get personId(): long{
			return _personId;
		}
		
		/**
		 * set 角色Id
		 */
		public function set personId(value: long): void{
			this._personId = value;
		}
		
		/**
		 * get 当前SP
		 * @return 
		 */
		public function get currentSp(): int{
			return _currentSp;
		}
		
		/**
		 * set 当前SP
		 */
		public function set currentSp(value: int): void{
			this._currentSp = value;
		}
		
		/**
		 * get 最大SP
		 * @return 
		 */
		public function get maxSp(): int{
			return _maxSp;
		}
		
		/**
		 * set 最大SP
		 */
		public function set maxSp(value: int): void{
			this._maxSp = value;
		}
		
	}
}