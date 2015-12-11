package com.game.zones.message{
	import net.Message;
	
	/** 
	 * @author Commuication Auto Maker
	 * 
	 * @version 1.0.0
	 * 
	 * @since 2011-5-8
	 * 
	 * 连续扫荡完成
	 */
	public class ResZoneContinuousRaidsEndMessage extends Message {
	
		//当前完成的副本编号
		private var _zoneid: int;
		
		//1表示全部完成，2表示停止扫荡
		private var _type: int;
		
		
		/**
		 * 写入字节缓存
		 */
		override protected function writing(): Boolean{
			//当前完成的副本编号
			writeInt(_zoneid);
			//1表示全部完成，2表示停止扫荡
			writeByte(_type);
			return true;
		}
		
		/**
		 * 读取字节缓存
		 */
		override protected function reading(): Boolean{
			//当前完成的副本编号
			_zoneid = readInt();
			//1表示全部完成，2表示停止扫荡
			_type = readByte();
			return true;
		}
		
		/**
		 * get id
		 * @return 
		 */
		override public function getId(): int {
			return 128115;
		}
		
		/**
		 * get 当前完成的副本编号
		 * @return 
		 */
		public function get zoneid(): int{
			return _zoneid;
		}
		
		/**
		 * set 当前完成的副本编号
		 */
		public function set zoneid(value: int): void{
			this._zoneid = value;
		}
		
		/**
		 * get 1表示全部完成，2表示停止扫荡
		 * @return 
		 */
		public function get type(): int{
			return _type;
		}
		
		/**
		 * set 1表示全部完成，2表示停止扫荡
		 */
		public function set type(value: int): void{
			this._type = value;
		}
		
	}
}