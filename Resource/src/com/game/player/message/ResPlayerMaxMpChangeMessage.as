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
	 * 最大内力变化
	 */
	public class ResPlayerMaxMpChangeMessage extends Message {
	
		//角色Id
		private var _personId: long;
		
		//当前MP
		private var _currentMp: int;
		
		//最大MP
		private var _maxMp: int;
		
		
		/**
		 * 写入字节缓存
		 */
		override protected function writing(): Boolean{
			//角色Id
			writeLong(_personId);
			//当前MP
			writeInt(_currentMp);
			//最大MP
			writeInt(_maxMp);
			return true;
		}
		
		/**
		 * 读取字节缓存
		 */
		override protected function reading(): Boolean{
			//角色Id
			_personId = readLong();
			//当前MP
			_currentMp = readInt();
			//最大MP
			_maxMp = readInt();
			return true;
		}
		
		/**
		 * get id
		 * @return 
		 */
		override public function getId(): int {
			return 103110;
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
		 * get 当前MP
		 * @return 
		 */
		public function get currentMp(): int{
			return _currentMp;
		}
		
		/**
		 * set 当前MP
		 */
		public function set currentMp(value: int): void{
			this._currentMp = value;
		}
		
		/**
		 * get 最大MP
		 * @return 
		 */
		public function get maxMp(): int{
			return _maxMp;
		}
		
		/**
		 * set 最大MP
		 */
		public function set maxMp(value: int): void{
			this._maxMp = value;
		}
		
	}
}