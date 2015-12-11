package com.game.arrow.bean{
	import net.Bean;
	
	/** 
	 * @author Commuication Auto Maker
	 * 
	 * @version 1.0.0
	 * 
	 * @since 2011-5-8
	 * 
	 * 箭支信息
	 */
	public class BowInfo extends Bean {
	
		//箭支主等阶
		private var _bowmainlv: int;
		
		//箭支子等阶
		private var _bowsublv: int;
		
		/**
		 * 写入字节缓存
		 */
		override protected function writing(): Boolean{
			//箭支主等阶
			writeInt(_bowmainlv);
			//箭支子等阶
			writeInt(_bowsublv);
			return true;
		}
		
		/**
		 * 读取字节缓存
		 */
		override protected function reading(): Boolean{
			//箭支主等阶
			_bowmainlv = readInt();
			//箭支子等阶
			_bowsublv = readInt();
			return true;
		}
		
		/**
		 * get 箭支主等阶
		 * @return 
		 */
		public function get bowmainlv(): int{
			return _bowmainlv;
		}
		
		/**
		 * set 箭支主等阶
		 */
		public function set bowmainlv(value: int): void{
			this._bowmainlv = value;
		}
		
		/**
		 * get 箭支子等阶
		 * @return 
		 */
		public function get bowsublv(): int{
			return _bowsublv;
		}
		
		/**
		 * set 箭支子等阶
		 */
		public function set bowsublv(value: int): void{
			this._bowsublv = value;
		}
		
	}
}