package com.game.monster.message{
	import com.game.utils.long;
	import net.Message;
	
	/** 
	 * @author Commuication Auto Maker
	 * 
	 * @version 1.0.0
	 * 
	 * @since 2011-5-8
	 * 
	 * 魔法变化
	 */
	public class ResMonsterMpChangeMessage extends Message {
	
		//角色Id
		private var _monsterId: long;
		
		//当前MP
		private var _currentMp: int;
		
		
		/**
		 * 写入字节缓存
		 */
		override protected function writing(): Boolean{
			//角色Id
			writeLong(_monsterId);
			//当前MP
			writeInt(_currentMp);
			return true;
		}
		
		/**
		 * 读取字节缓存
		 */
		override protected function reading(): Boolean{
			//角色Id
			_monsterId = readLong();
			//当前MP
			_currentMp = readInt();
			return true;
		}
		
		/**
		 * get id
		 * @return 
		 */
		override public function getId(): int {
			return 114102;
		}
		
		/**
		 * get 角色Id
		 * @return 
		 */
		public function get monsterId(): long{
			return _monsterId;
		}
		
		/**
		 * set 角色Id
		 */
		public function set monsterId(value: long): void{
			this._monsterId = value;
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
		
	}
}