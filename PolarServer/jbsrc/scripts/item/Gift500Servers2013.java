package scripts.item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.game.backpack.script.IItemScript;
import com.game.backpack.structs.Item;
import com.game.config.Config;
//import com.game.languageres.manager.ResManager;
import com.game.manager.ManagerPool;
import com.game.player.structs.Player;
//import com.game.prompt.structs.Notifys;
import com.game.structs.Reasons;
//import com.game.utils.MessageUtil;
import com.game.utils.TimeUtil;

/**
 * 500服大礼包
 * @author Administrator
 *
 */
public class Gift500Servers2013 implements IItemScript {
	@Override
	public int getId() {
		return 1009192;
	}

	//开始时间
	private String actstart = "2013-3-22 00:00:01";
	//礼包内道具
	private String itemstr = "-5,888,0,1;" 
							+ "1007,500,0,1;"
							+ "1011,40,0,1,259200;"
							+ "3019,30,0,1,259200;"
							+ "18138,30,0,1,259200";
	@Override
	public boolean use(Item item, Player player, String... parameters) {
		Date startdate = TimeUtil.getDateByString(actstart);
		if (startdate.getTime() > System.currentTimeMillis()) {
			/*xiaozhuoming: 暂时没有用到
			MessageUtil.notify_player(player, Notifys.MOUSEPOS, ResManager.getInstance().getString("3月22号以后才能打开 {1}"), item.acqItemModel().getQ_name());
			*/
			return false;
		}
		
		if (player.getLevel() < 65) {
			/*xiaozhuoming: 暂时没有用到
			MessageUtil.notify_player(player, Notifys.MOUSEPOS, ResManager.getInstance().getString("您的等级小于65,无法开启"));
			*/
			return false;
		}
		
		if (ManagerPool.backpackManager.removeItem(player, item, 1, Reasons.def27, Config.getId())) {
			List<Item> itemmakes = new ArrayList<Item>();
			ManagerPool.backpackManager.createItems(player, itemstr, itemmakes);
			ManagerPool.backpackManager.addItems(player, itemmakes, Config.getId());
			ManagerPool.backpackManager.notifyitemname(player, itemstr);
			return true;
		}
		return false;
	}

}
