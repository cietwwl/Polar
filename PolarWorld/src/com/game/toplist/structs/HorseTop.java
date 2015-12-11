package com.game.toplist.structs;

import com.game.toplist.manager.TopListManager;

/**
 * 坐骑排序数据
 *
 * @author  
 */
public class HorseTop extends TopData {

	private int horsejieshu;
	private int horselv;
	private int horseskilllv;
	private long horsetime;
	private int horseexp;

	public HorseTop(long topid, int horsejieshunum, int horselvnum, int horseskilllvnum, long horsetimenum, int horseexpnum) {
		super(topid);
		horsejieshu = horsejieshunum;
		horselv = horselvnum;
		horseskilllv = horseskilllvnum;
		horsetime = horsetimenum;
		horseexp = horseexpnum;
	}

	public int getHorsejieshu() {
		return horsejieshu;
	}

	public void setHorsejieshu(int horsejieshu) {
		this.horsejieshu = horsejieshu;
	}

	public int getHorselv() {
		return horselv;
	}

	public void setHorselv(int horselv) {
		this.horselv = horselv;
	}

	public int getHorseskilllv() {
		return horseskilllv;
	}

	public void setHorseskilllv(int horseskilllv) {
		this.horseskilllv = horseskilllv;
	}

	public long getHorsetime() {
		return horsetime;
	}

	public void setHorsetime(long horsetime) {
		this.horsetime = horsetime;
	}
	
	public int getHorseexp(){
		return horseexp;
	}
	
	public void setHorseexp(int horseexp){
		this.horseexp = horseexp;
	}

	@Override
	public boolean checkAddCondition() {
		return TopListManager.SYNC_HORSE_STAGE <= horsejieshu;
	}

	@Override
	public int compare(TopData otherTopData) {
		if (otherTopData instanceof HorseTop) {
			HorseTop othTop = (HorseTop) otherTopData;
			if (othTop != null) {
				if (othTop.getHorsejieshu() > this.getHorsejieshu()) {
					return 1;
				} else if (othTop.getHorsejieshu() == this.getHorsejieshu()) {
//					if (othTop.getHorseskilllv() > this.getHorseskilllv()) {
//						return 1;
//					} else if (othTop.getHorseskilllv() == this.getHorseskilllv()) {
//						if (othTop.getHorselv() > this.getHorselv()) {
//							return 1;
//						} else if (othTop.getHorselv() == this.getHorselv()) {
							if(othTop.getHorseexp() > this.getHorseexp()){
								return 1;
							} else if(othTop.getHorseexp() == this.getHorseexp()){
								if (this.getHorsetime() > othTop.getHorsetime()){
									return 1;
								}else if(othTop.getHorsetime() == this.getHorsetime()){
									return super.compare(otherTopData);
								}
							}
//						}
//					}
				}
			}
		}
		return -1;
	}
}
