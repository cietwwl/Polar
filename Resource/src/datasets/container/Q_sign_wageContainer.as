package datasets.container {
	import flash.utils.Dictionary;
	import datasets.bean.Q_sign_wage;

	import flash.utils.ByteArray;
	/** 
	 * @author ExcelUtil Auto Maker
	 * 
	 * @version 1.0.0
	 * 
	 * @since 2011-5-8
	 * 
	 * Q_sign_wage
	 */
	public class Q_sign_wageContainer {
		
		private var _list:Vector.<Q_sign_wage> = new Vector.<Q_sign_wage>();
		
		private var _dict:Dictionary = new Dictionary();
		
		private var _version:int;
		
		public function Q_sign_wageContainer(bytes: ByteArray){
			_version = bytes.readInt();
			var num:int = bytes.readInt();
			for (var i : int = 0; i < num; i++) {
				var bean:Q_sign_wage = new Q_sign_wage();
				bean.read(bytes);
				_list.push(bean);
				_dict[String(bean.q_sign_num)] = bean;
			}
		}
		
		public function get list(): Vector.<Q_sign_wage>{
			return _list;
		}
		
		public function get dict(): Dictionary{
			return _dict;
		}
		
		public function get version(): int{
			return _version;
		}
	}
}
