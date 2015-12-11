package com.game.backpack.structs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.game.attribute.ActivateAttributeManager;
import com.game.backpack.bean.ItemInfo;
import com.game.backpack.manager.ItemAppendManager;
import com.game.chat.bean.GoodsInfoRes;
import com.game.config.Config;
import com.game.data.bean.Q_itemBean;
import com.game.data.manager.DataManager;
import com.game.json.JSONserializable;
import com.game.map.bean.DropGoodsInfo;
import com.game.monster.structs.Monster;
import com.game.object.GameObject;
import com.game.player.structs.Player;
import com.game.structs.Attributes;
import com.game.structs.Position;
import com.game.transactions.structs.ItemAttributeLogData;
import com.game.transactions.structs.ItemLogData;
import com.game.utils.Global;
import com.game.utils.RandomUtils;

public abstract class Item extends GameObject implements Comparable<Item>,Cloneable {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Item.class);

	private static final long serialVersionUID = -961848290443833161L;

	private int itemModelId;
	
	private int num;
	
	private int packageId;
	
	private int gridId;
	//是否绑定
	private boolean bind;
	//失效时间
	private int losttime;
	//道具状态
	private transient byte status ;
	//是否交易状态
	protected transient boolean trade;
	
	protected transient boolean lost;
	//临时数据
	private HashMap<String, String> parameters = new HashMap<String, String>();
	
	protected transient GoodsInfoRes chatres=new GoodsInfoRes();
	/**
	 * 
	 * @param player
	 * @param parameters parameters[0]使用数量
	 */
	public abstract void use(Player player, String... parameters);
	
	public abstract void unuse(Player player, String... parameters);
	
	/**
	 * 创建物品对象 带有强化等级和附加属性的
	 * 
	 * @param itemModelId
	 * @param num
	 * @param bind
	 * @param losttime
	 * @param gradeNum
	 * @param append
	 * @return
	 */
	public static List<Item> createItems(int itemModelId, int num, boolean bind, long losttime, int grade, String append) {
		return createItems(itemModelId, num, bind, losttime, grade, 0, append);
	}
	
	/**
	 * 创建物品对象 带有强化等级
	 * 
	 * @param itemModelId
	 *            道具id
	 * @param num
	 *            数量
	 * @param bind
	 *            是否绑定
	 * @param losttime
	 *            过期时间
	 * @param grade
	 *            等级
	 * @param appendcount
	 *            没有用到
	 * @return
	 */
	public static List<Item> createItems(int itemModelId, int num, boolean bind, long losttime, int grade, int unuse) {
		return createItems(itemModelId, num, bind, losttime, grade, 0, null);
	}
	
	/**
	 * 掉落物专用
	 * 
	 * @param itemModelId
	 *            道具id
	 * @param num
	 *            数量
	 * @param bind
	 *            是否绑定
	 * @param losttime
	 *            过期时间
	 * @param grade
	 *            等级
	 * @param addAttribute
	 *            追加等级
	 * @return
	 */
	public static List<Item> createItemsForDropItem(int itemModelId, int num, boolean bind, long losttime, int grade, int addAttribute) {
		return createItems(itemModelId, num, bind, losttime, grade, addAttribute, null);
	}

	/**
	 * 创建带有强化等级、追加等级、卓越属性的物品
	 * 
	 * @param itemModelId
	 * @param num
	 * @param bind
	 * @param losttime
	 * @param grade
	 *            强化等级
	 * @param addAttribute
	 *            追加等级
	 * @param zhuoyue
	 *            卓越属性
	 * @return
	 */
	public static List<Item> createItems(int itemModelId, int num, boolean bind, long losttime, int grade, int addAttribute, String zhuoyue) {
		List<Item> createItems = createItemsBase(itemModelId, num, bind, losttime);
		if (createItems == null || createItems.size() <= 0) {
			return createItems;
		}
		Q_itemBean q_itemBean = DataManager.getInstance().q_itemContainer.getMap().get(itemModelId);
		if (q_itemBean.getQ_type() == ItemTypeConst.EQUIP || q_itemBean.getQ_type() == ItemTypeConst.HORSEEQUIP || q_itemBean.getQ_type() == ItemTypeConst.WING) {
			for (Item item : createItems) {
				Equip equip = (Equip) item;
				equip.setGradeNum(grade);
				equip.setAddAttributeLevel(addAttribute);
				// 如果是卓越物品
				if (q_itemBean.getQ_remarkable() == 1)
					ItemAppendManager.getInstance().buildAppend(equip);
				// 如果附加了属性
				if (!StringUtils.isBlank(zhuoyue)) {
					ItemAppendManager.getInstance().buildAppend(equip, zhuoyue);
				}
				buildKnowingAttackPercent(equip);
				buildIgnoreAttackPercent(equip);
				equip.setAttributeCount(ActivateAttributeManager.getInstance().calAttributePower(equip));
				equip.calculateFightPower();
			}
		}
		return createItems;
	}
	
	/**
	 * 不含特殊属性的道具创建
	 * @param itemModelId
	 * @param num
	 * @param bind
	 * @param losttime
	 * @return
	 * @create	hongxiao.z      2014-2-15 上午11:22:53
	 */
	public static List<Item> createItems(int itemModelId, int num, boolean bind, long losttime)
	{
		return createItems(itemModelId,num,bind,losttime,0,0,null);
	}
	/**
	 * 创建基本物品对象
	 * 
	 * @param itemModelId
	 * @param num
	 * @param bind
	 * @param losttime
	 * 		毫秒
	 * @return
	 */
	private static List<Item> createItemsBase(int itemModelId, int num, boolean bind, long losttime){
		Q_itemBean q_itemBean = DataManager.getInstance().q_itemContainer.getMap().get(itemModelId);
		List<Item> list=new ArrayList<Item>();
		if(q_itemBean==null){
			logger.error("物品模型ID"+itemModelId+"找不到",new Exception("通知策划检查数据物品模型ID"+itemModelId+"找不到"));
			return list;
		}
		int count=num;
		while (count>0) {
			Item item=null;
			int itemnum=0;
			if(count>q_itemBean.getQ_max()){
				itemnum=q_itemBean.getQ_max();
			}else{
				itemnum=count;
			}
			
			if(itemnum<=0) itemnum = 1;
			count-=itemnum;
			switch (q_itemBean.getQ_type()) {
			case ItemTypeConst.COPPER:
			case ItemTypeConst.BINDGOLD:
//				list.add(createMoney(num));
				item = new CommonGoods();
				item.setItemModelId(itemModelId);
				item.setNum(num);
				item.setId(Config.getId());
				list.add(item);
				return list;
			case ItemTypeConst.CURRENCY:
				item = new Currency();
				break;
			case ItemTypeConst.EQUIP:
			case ItemTypeConst.WING:
				item = new Equip();
				break;
			case ItemTypeConst.MEDICINES:
				item = new Medicines();
				break;
			case ItemTypeConst.TRANSSCROLL:
				item = new ScrollTrans();
				break;
			case ItemTypeConst.TASKSCROLL:
				item = new ScrollTask();
				break;
			case ItemTypeConst.SKILLBOOK://技能书
				item = new Book();
				break;
			case ItemTypeConst.GIFT:
				item = new Gift();				
				break;
			case ItemTypeConst.SCRIPT:
				item = new ScriptItem();				
				break;
			case ItemTypeConst.PANEL:
				item = new PanelItem();				
				break;
			case ItemTypeConst.HORSEEQUIP:
				item=new HorseEquip();
				break;
			case ItemTypeConst.BUFF:
				item=new BuffGoods();
				break;
			case ItemTypeConst.ATTRIBUTE:
				item=new AttributeItem();
				break;
			case ItemTypeConst.HORSE:
				item=new horse();
				break;
			case ItemTypeConst.HORSE_MEDICINES:
				item=new HorseMedicines();
				break;
			case ItemTypeConst.PANDORA:
				item = new Pandora();
				break;
			case ItemTypeConst.COMMONGOODS:
			case ItemTypeConst.RING: // 结婚纪念戒指
			case ItemTypeConst.METRIAL_JEWEL:
			case ItemTypeConst.SYNTHGOODS:
			case ItemTypeConst.TASKGOODS:
			case ItemTypeConst.RESURRECTION:
			case ItemTypeConst.TECHNOLOGY:
			case ItemTypeConst.JINGPO:
				item = new CommonGoods();
				break;
			case ItemTypeConst.XIEFU:
				item = new XiEFu();
				break;
			default:
				logger.error("找不到定义的物品类型"+q_itemBean.getQ_type(),new Exception());
				item=new CommonGoods();
				break;
			}
			item.setBind(bind);	
			item.setItemModelId(itemModelId);
			item.setNum(itemnum);
			item.setId(Config.getId());

			if(q_itemBean.getQ_effective_time() != 0) {
				item.setLosttime((int) (System.currentTimeMillis()/1000+q_itemBean.getQ_effective_time()*60));
			}
			if(losttime!=0){
				item.setLosttime((int) (losttime/1000));	
			}
			list.add(item);
		}
		return list;
	}
	
	/**
	 * 赋予装备会心一击触发概率
	 * 
	 * @param equip
	 */
	private static void buildKnowingAttackPercent(Equip equip) {
		try {
			if (equip != null) {
				Q_itemBean q_itemBean = DataManager.getInstance().q_itemContainer.getMap().get(equip.getItemModelId());
				if (q_itemBean != null) {
					int q_max_damage_percent = q_itemBean.getQ_max_damage_percent();
					if (q_max_damage_percent > 0 && RandomUtils.isGenerate(q_max_damage_percent, Global.MAX_PROBABILITY)) {
						Attribute attribute = new Attribute();
						attribute.setType(Attributes.KNOWING_ATTACKPERCENT.getValue());
						attribute.setValue(q_itemBean.getQ_max_damage_hit());
						equip.getAttributes().add(attribute);
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}

	}

	/**
	 * 赋予装备无视一击触发概率
	 * 
	 * @param equip
	 */
	private static void buildIgnoreAttackPercent(Equip equip) {
		try {
			if (equip != null) {
				Q_itemBean q_itemBean = DataManager.getInstance().q_itemContainer.getMap().get(equip.getItemModelId());
				if (q_itemBean != null) {
					int q_ignore_attack_percent = q_itemBean.getQ_ignore_attack_percent();
					if (q_ignore_attack_percent > 0 && RandomUtils.isGenerate(q_ignore_attack_percent, Global.MAX_PROBABILITY)) {
						Attribute attribute = new Attribute();
						attribute.setType(Attributes.IGNORE_ATTACKPERCENT.getValue());
						attribute.setValue(q_itemBean.getQ_ignore_attack());
						equip.getAttributes().add(attribute);
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	// -1金币，-2钻石，-3经验，-4真气  -5绑定钻石 -6精魄 -7军功
	
	public static Item createGold(int num,boolean isbind){
		Item item=new CommonGoods();
		item.setBind(isbind);
		item.setItemModelId(-2);
		item.setNum(num);
		item.setId(Config.getId());
		return item;
	}
	
	public static Item createMoney(int num){
		Item item=new CommonGoods();
		item.setItemModelId(-1);
		item.setNum(num);
		item.setId(Config.getId());
		return item;
	}
	
	public static Item createZhenQi(int num){
		Item item=new CommonGoods();
		item.setItemModelId(-3);
		item.setNum(num);
		item.setId(Config.getId());
		return item;
	}
	
	
	public static Item createExp(int num){
		Item item=new CommonGoods();
		item.setItemModelId(-4);
		item.setNum(num);
		item.setId(Config.getId());
		return item;
	}
	

	
	public static Item createBindGold(int num){
		Item item=new CommonGoods();
		item.setItemModelId(-5);
		item.setNum(num);
		item.setId(Config.getId());
		return item;
	}
	
	public static Item createFightSpirit(int num){
		Item item=new CommonGoods();
		item.setItemModelId(-6);
		item.setNum(num);
		item.setId(Config.getId());
		return item;
	}
	
	public static Item createRank(int num){
		Item item=new CommonGoods();
		item.setItemModelId(-7);
		item.setNum(num);
		item.setId(Config.getId());
		return item;
	}
	
	public int getItemModelId() {
		return itemModelId;
	}

	public void setItemModelId(int itemModelId) {
		this.itemModelId = itemModelId;
	}

	public int getNum() {
		return num;
	}
	public Q_itemBean acqItemModel() {
		return DataManager.getInstance().q_itemContainer.getMap().get(itemModelId);
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	public int getGridId() {
		return gridId;
	}

	public void setGridId(int gridId) {
		this.gridId = gridId;
	}
	
	public boolean isBind() {
		return bind;
	}

	public void setBind(boolean bind) {
		this.bind = bind;
	}

	public int getLosttime() {
		return losttime;
	}

	public void setLosttime(int losttime) {
		this.losttime = losttime;
	}
	
	
	@Override
	public int compareTo(Item o) {
		Q_itemBean q_itemBean = DataManager.getInstance().q_itemContainer.getMap().get(getItemModelId());
		Q_itemBean q_itemBean2 = DataManager.getInstance().q_itemContainer.getMap().get(o.getItemModelId());
		if(q_itemBean.getQ_type()!=q_itemBean2.getQ_type()){
			return q_itemBean.getQ_type()-q_itemBean2.getQ_type();
		}
//		if(q_itemBean.getQ_kind()!=q_itemBean2.getQ_kind()){
//			return q_itemBean.getQ_kind()-q_itemBean2.getQ_kind();
//		}
//		if (q_itemBean.getQ_sex() != q_itemBean2.getQ_sex()) {
//			return q_itemBean.getQ_sex()-q_itemBean2.getQ_sex();
//		}
//		if(q_itemBean.getQ_level()!=q_itemBean2.getQ_level()){
//			return q_itemBean.getQ_level()-q_itemBean.getQ_level();
//		}
		if(getItemModelId()!=o.getItemModelId()){
			return getItemModelId()-o.getItemModelId();
		}
		if (this instanceof Equip && o instanceof Equip) {
			Equip a = (Equip) this;
			Equip b = (Equip) o;
			if (a.getQuality() != b.getQuality()) {
				return b.getQuality() - a.getQuality();
			}
		}
		if(isBind()!=o.isBind()){
			return isBind()==true?-1:1;
		}
		if(getLosttime()!=o.getLosttime()){
			return (int) (getLosttime()-o.getLosttime());
		}
		if(getNum()!=o.getNum()){
			return o.getNum()-getNum();
		}
		return 0;
	}

	public Item clone() throws CloneNotSupportedException{
		return (Item)super.clone();
	}
	
	
	/**
	 * 获取物品信息
	 * @param item 物品
	 * @return
	 */
	public ItemInfo buildItemInfo(){
		ItemInfo info = new ItemInfo();
		info.setItemId(getId());
		info.setItemModelId(getItemModelId());
		info.setNum(getNum());
		info.setGridId(getGridId());
		info.setIsbind((byte) (isBind()?1:0));
		if(this instanceof Equip){
			Equip equip=(Equip) this;
			if(equip.getAttributes()!=null){
				info.setAttributs((byte) equip.getAttributes().size());
				for (Attribute attribute : equip.getAttributes()) {
					info.getGoodAttributes().add(attribute.buildGoodsAttribute());		
				}
			}else{
				info.setAttributs((byte) 0);
			}
			info.setAddAttributLevel((byte) equip.getAddAttributeLevel());
			info.setIntensify((byte) equip.getGradeNum());
			info.setFightPower(equip.getFightPower());
			info.setAttributeCount(equip.getAttributeCount());
			// info.setIsFullAppend((byte) (equip.isFullAppend()?1:0));
		}
		info.setLostTime(getLosttime());
		if (getParameters().size() > 0) {
			info.setParameters(JSONserializable.toString(getParameters()));
		}
		return info;
	}
	
	/**
	 * 获取物品日志展示用的信息
	 * @param item 物品
	 * @return
	 */
	public ItemLogData buildItemLogData(){
		ItemLogData info = new ItemLogData();
		info.setItemId(getId());
		info.setItemModelId(getItemModelId());
		info.setNum(getNum());
		info.setGridId(getGridId());
		info.setIsbind((byte) (isBind()?1:0));
		if(this instanceof Equip){
			Equip equip=(Equip) this;
			if(equip.getAttributes()!=null){
				info.setAttributs((byte) equip.getAttributes().size());
				for (Attribute attribute : equip.getAttributes()) {
					ItemAttributeLogData itemattribut = new ItemAttributeLogData();
					itemattribut.setType(attribute.getType());
					itemattribut.setValue(attribute.getValue());
					info.getItemAttributeLogData().add(itemattribut);		
				}
			}else{
				info.setAttributs((byte) 0);
			}
			info.setIntensify((byte) equip.getGradeNum());
			info.setIsFullAppend((byte) (equip.isFullAppend()?1:0));
		}
		info.setLostTime(getLosttime());
		return info;
	}
	
	public DropGoodsInfo buildDropInfo(Monster monster, Position ableDropPoint){
		DropGoodsInfo info=new DropGoodsInfo();
		info.setDropGoodsId(getId());
		info.setItemModelId(getItemModelId());
		info.setNum(getNum());
		if(this instanceof Equip){
			Equip equip=(Equip) this;
			if(equip.getAttributes()!=null){
				info.setAttributs((byte) equip.getAttributes().size());	
			}else{
				info.setAttributs((byte) 0);
			}
			info.setIntensify((byte) equip.getGradeNum());
			info.setAddition((byte) equip.getAddAttributeLevel());
			info.setIsFullAppend((byte) (equip.isFullAppend()?1:0));
		}
		info.setX(ableDropPoint.getX());
		info.setY(ableDropPoint.getY());
		return info;
	}

	public DropGoodsInfo buildDropInfo(Position ableDropPoint) {
		return buildDropInfo(null, ableDropPoint);
	}

	@Override
	public String toString() {
		return "[cellid="+getGridId()+"]";
	}
	/**
	 * 是否己过时
	 * @return
	 */
	public boolean isLost(){
		return losttime>0?System.currentTimeMillis()>losttime*1000l:false;
	}

	public boolean isTrade(){
		return status==1;
	}
	
	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public HashMap<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}

	
}
