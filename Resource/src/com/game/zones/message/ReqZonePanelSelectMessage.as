package com.game.zones.message{
	import net.Message;
	
	/** 
	 * @author Commuication Auto Maker
	 * 
	 * @version 1.0.0
	 * 
	 * @since 2011-5-8
	 * 
	 * 在副本面板进行选择
	 */
	public class ReqZonePanelSelectMessage extends Message {
	
		//配置的副本编号
		private var _zoneid: int;
		
		
		/**
		 * 写入字节缓存
		 */
		override protected function writing(): Boolean{
			//配置的副本编号
			writeInt(_zoneid);
			return true;
		}
		
		/**
		 * 读取字节缓存
		 */
		override protected function reading(): Boolean{
			//配置的副本编号
			_zoneid = readInt();
			return true;
		}
		
		/**
		 * get id
		 * @return 
		 */
		override public function getId(): int {
			return 128201;
		}
		
		/**
		 * get 配置的副本编号
		 * @return 
		 */
		public function get zoneid(): int{
			return _zoneid;
		}
		
		/**
		 * set 配置的副本编号
		 */
		public function set zoneid(value: int): void{
			this._zoneid = value;
		}
		
	}
}