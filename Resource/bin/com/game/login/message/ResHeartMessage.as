package com.game.login.message{
	import net.Message;
	
	/** 
	 * @author Commuication Auto Maker
	 * 
	 * @version 1.0.0
	 * 
	 * @since 2011-5-8
	 * 
	 * 心跳消息
	 */
	public class ResHeartMessage extends Message {
	
		//当前时间
		private var _time: int;
		
		//服务器启动时间
		private var _time2: int;
		
		
		/**
		 * 写入字节缓存
		 */
		override protected function writing(): Boolean{
			//当前时间
			writeInt(_time);
			//服务器启动时间
			writeInt(_time2);
			return true;
		}
		
		/**
		 * 读取字节缓存
		 */
		override protected function reading(): Boolean{
			//当前时间
			_time = readInt();
			//服务器启动时间
			_time2 = readInt();
			return true;
		}
		
		/**
		 * get id
		 * @return 
		 */
		override public function getId(): int {
			return 100108;
		}
		
		/**
		 * get 当前时间
		 * @return 
		 */
		public function get time(): int{
			return _time;
		}
		
		/**
		 * set 当前时间
		 */
		public function set time(value: int): void{
			this._time = value;
		}
		
		/**
		 * get 服务器启动时间
		 * @return 
		 */
		public function get time2(): int{
			return _time2;
		}
		
		/**
		 * set 服务器启动时间
		 */
		public function set time2(value: int): void{
			this._time2 = value;
		}
		
	}
}