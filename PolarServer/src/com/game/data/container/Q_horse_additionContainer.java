package com.game.data.container;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.game.data.bean.Q_horse_additionBean;
import com.game.data.dao.Q_horse_additionDao;
import com.game.utils.Symbol;

/** 
 * @author ExcelUtil Auto Maker
 * 
 * @version 1.0.0
 * 
 * Q_horse_addition数据容器
 */
public class Q_horse_additionContainer {

	private List<Q_horse_additionBean> list;
	
	private HashMap<String, Q_horse_additionBean> map = new HashMap<String, Q_horse_additionBean>();

	private Q_horse_additionDao dao = new Q_horse_additionDao();
	
	public void load(){
		list = dao.select();
		Iterator<Q_horse_additionBean> iter = list.iterator();
		while (iter.hasNext()) {
			Q_horse_additionBean bean = (Q_horse_additionBean) iter
					.next();
			map.put(bean.getQ_id(), bean);
		}
	}

	public List<Q_horse_additionBean> getList(){
		return list;
	}
	
	public HashMap<String, Q_horse_additionBean> getMap(){
		return map;
	}
	
	public Q_horse_additionBean getQ_horse_additionBeanByCurLayer(int curLayer) {
		for(String key : this.map.keySet()) {
			if(key == null) {
				continue;
			}
			
			String[] keyArr = key.split(Symbol.XIAHUAXIAN_REG);
			if(keyArr.length !=2 || Integer.valueOf(keyArr[1]) != curLayer) {
				continue;
			}
			
			return this.map.get(key);
		}
		
		return null;
	}
}