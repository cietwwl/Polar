package com.game.guild.manager;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.game.backpack.manager.BackpackManager;
import com.game.config.Config;
import com.game.country.manager.CountryAwardManager;
import com.game.country.manager.CountryManager;
import com.game.country.structs.KingData;
import com.game.data.bean.Q_globalBean;
import com.game.db.bean.GuildBean;
import com.game.guild.bean.FriendGuild;
import com.game.guild.bean.GuildInfo;
import com.game.guild.bean.MemberInfo;
import com.game.guild.dao.GuildDao;
import com.game.guild.message.ReqGuildAddDiplomaticToServerMessage;
import com.game.guild.message.ReqGuildAddMemberToServerMessage;
import com.game.guild.message.ReqGuildApplyAddToServerMessage;
import com.game.guild.message.ReqGuildAutoArgeeAddGuildToServerMessage;
import com.game.guild.message.ReqGuildAutoGuildArgeeAddToServerMessage;
import com.game.guild.message.ReqGuildBannerLevelUpToServerMessage;
import com.game.guild.message.ReqGuildChangeBannerIconToServerMessage;
import com.game.guild.message.ReqGuildChangeBannerNameToServerMessage;
import com.game.guild.message.ReqGuildChangeBulletinToServerMessage;
import com.game.guild.message.ReqGuildChangeNickNameToServerMessage;
import com.game.guild.message.ReqGuildChangePowerLevelToServerMessage;
import com.game.guild.message.ReqGuildCreateToServerMessage;
import com.game.guild.message.ReqGuildDeleteDiplomaticToServerMessage;
import com.game.guild.message.ReqGuildDeleteGuildToServerMessage;
import com.game.guild.message.ReqGuildDeleteMemberToServerMessage;
import com.game.guild.message.ReqGuildGetEventListToServerMessage;
import com.game.guild.message.ReqGuildGetGuildListToServerMessage;
import com.game.guild.message.ReqGuildGetMemberListToServerMessage;
import com.game.guild.message.ReqGuildInviteAddToServerMessage;
import com.game.guild.message.ReqGuildQuitToServerMessage;
import com.game.guild.message.ReqGuildSubmitItemToServerMessage;
import com.game.guild.message.ReqInnerGuildAddDiplomaticToWorldMessage;
import com.game.guild.message.ReqInnerGuildAddMemberToWorldMessage;
import com.game.guild.message.ReqInnerGuildApplyAddToWorldMessage;
import com.game.guild.message.ReqInnerGuildAutoArgeeAddGuildToWorldMessage;
import com.game.guild.message.ReqInnerGuildAutoGuildArgeeAddToWorldMessage;
import com.game.guild.message.ReqInnerGuildBannerLevelUpToWorldMessage;
import com.game.guild.message.ReqInnerGuildChangeBannerIconToWorldMessage;
import com.game.guild.message.ReqInnerGuildChangeBannerNameToWorldMessage;
import com.game.guild.message.ReqInnerGuildChangeBulletinToWorldMessage;
import com.game.guild.message.ReqInnerGuildChangeNickNameToWorldMessage;
import com.game.guild.message.ReqInnerGuildChangePowerLevelToWorldMessage;
import com.game.guild.message.ReqInnerGuildCreateToWorldMessage;
import com.game.guild.message.ReqInnerGuildDeleteDiplomaticToWorldMessage;
import com.game.guild.message.ReqInnerGuildDeleteGuildToWorldMessage;
import com.game.guild.message.ReqInnerGuildDeleteMemberToWorldMessage;
import com.game.guild.message.ReqInnerGuildGetEventListToWorldMessage;
import com.game.guild.message.ReqInnerGuildGetGuildListToWorldMessage;
import com.game.guild.message.ReqInnerGuildGetMemberListToWorldMessage;
import com.game.guild.message.ReqInnerGuildInviteAddToWorldMessage;
import com.game.guild.message.ReqInnerGuildNotifyToWorldMessage;
import com.game.guild.message.ReqInnerGuildQuitToWorldMessage;
import com.game.guild.message.ReqInnerGuildSubmitItemToWorldMessage;
import com.game.guild.message.ReqInnerKingCityEventToWorldMessage;
import com.game.guild.message.ResGuildAloneGuildInfoToClientMessage;
import com.game.guild.message.ResGuildAloneMemberInfoToClientMessage;
import com.game.guild.message.ResGuildChangeBroadcastMessage;
import com.game.guild.message.ResGuildDeleteInfoRoundToClientMessage;
import com.game.guild.message.ResInnerGuildAloneGuildInfoToServerMessage;
import com.game.guild.message.ResInnerGuildAloneMemberInfoToServerMessage;
import com.game.guild.structs.GuildData;
import com.game.guild.structs.GuildTmpInfo;
import com.game.guildflag.manager.GuildFlagManager;
import com.game.json.JSONserializable;
import com.game.languageres.manager.ResManager;
import com.game.manager.ManagerPool;
import com.game.map.manager.MapManager;
import com.game.player.manager.PlayerAttributeManager;
import com.game.player.manager.PlayerManager;
import com.game.player.structs.Player;
import com.game.player.structs.PlayerAttributeType;
import com.game.player.structs.PlayerState;
import com.game.prompt.structs.Notifys;
import com.game.server.impl.WServer;
import com.game.structs.Reasons;
import com.game.utils.CommonConfig;
import com.game.utils.MessageUtil;
import com.game.utils.ParseUtil;
import com.game.utils.WordFilter;

/**
 * @author xiaozhuoming 战盟管理类(游戏服务器)
 */
public class GuildServerManager {


	private Logger log = Logger.getLogger(GuildServerManager.class);
	private static Object obj = new Object();
	//战盟管理类实例
	private static GuildServerManager manager;

	private GuildServerManager() {
	}

	public static GuildServerManager getInstance() {
		synchronized (obj) {
			if (manager == null) {
				manager = new GuildServerManager();
			}
		}
		return manager;
	}
	
	private GuildDao guildDao = new GuildDao();
	
	/**战盟信息缓存
	 * 
	 */
	public static ConcurrentHashMap<Long,GuildTmpInfo> GuildTmpInfoMap = new ConcurrentHashMap<Long,GuildTmpInfo>();
	
	/**战盟友好信息缓存
	 * 
	 */
	public static ConcurrentHashMap<Long, FriendGuild> GuildFriendMap = new ConcurrentHashMap<Long,FriendGuild>();
	
	//战盟限制条件
	private int Create_Need_Gold = 1000000;	//创建战盟所需金币 1000000
	private short Create_Need_Level = 30;		//创建战盟所需等级
	private byte GuildName_MinLen = 2;		//战盟最小名字
	private byte GuildName_MaxLen = 6;		//战盟最大名字
	private byte GuildBanner_MinLen = 2;		//盟旗最小名字
	private byte GuildBanner_MaxLen = 6;		//盟旗最大名字
	private byte NickName_GroupName_Len = 12;	//昵称和分组长度
	private short Bulletin_MaxLen = 200;		//公告最大长度
	private int Token_MaxNum = 65535;		//盟贡令牌最大数目
	private long Gold_MaxNum = 4200000000L;	//盟贡金币最大数目
	public static byte Guild_MaxNum = 30;		//战盟最大人数
	//通知类型
	private byte Notify_Create = 0;		//创建
	private byte Notify_AddOrUpdate = 1;	//添加或更新
	private byte Notify_Delete = 2;		//删除
	//private byte Notity_DiplomaticChange = 3;//外交关系改变
	private byte Notity_AddMember = 4;	//添加战盟成员
	private byte Notity_KingCity = 5;	//王城盟派(领地争夺)
	private byte Notity_DeleteGuild = 6;	//解散盟派
	private byte Notity_ChangeBanner = 7;	//改变盟旗
	private byte Notity_BannerBuff = 8;	//盟旗BUFF

	public void reqGuildCreateToServer(Player player, ReqGuildCreateToServerMessage message) {
		if (!player.checkTempPlayer()) {
			if (player.getGuildId() == 0) {
				int createNeedGold = Create_Need_Gold;
				Q_globalBean golbalBean_num = ManagerPool.dataManager.q_globalContainer.getMap().get(CommonConfig.GUILD_CREATE_NEED_GOLD.getValue());
				if (golbalBean_num != null) {
					createNeedGold = golbalBean_num.getQ_int_value();
				}
				if(player.getMoney() >= createNeedGold) {
					if (player.getLevel() >= Create_Need_Level) {
						if (!"".equals(message.getGuildName())) {
							if (message.getGuildName().length() >= GuildName_MinLen && message.getGuildName().length() <= GuildName_MaxLen) {
								if (!WordFilter.getInstance().hashBadWords(message.getGuildName())) {
									if (!"".equals(message.getGuildBanner())) {
										if (message.getGuildBanner().length() >= GuildBanner_MinLen && message.getGuildBanner().length() <= GuildBanner_MaxLen) {
											if (!WordFilter.getInstance().hashBadWords(message.getGuildBanner())) {
												ReqInnerGuildCreateToWorldMessage sendMessage = new ReqInnerGuildCreateToWorldMessage();
												sendMessage.setPlayerId(player.getId());
												sendMessage.setGuildName(message.getGuildName());
												sendMessage.setGuildBanner(message.getGuildBanner());
												sendMessage.setGuildBannerIcon(message.getGuildBannerIcon());
												MessageUtil.send_to_world(sendMessage);
											} else {
												MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，盟旗名字中包含敏感非法字符"));
											}
										} else {
											MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，盟旗名字需要在2-6个汉字之间"));
										}
									} else {
										MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，盟旗名字不可为空"));
									}
								} else {
									MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，战盟名字中包含敏感非法字符"));
								}
							} else {
								MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，战盟名字需要在2-6个汉字之间"));
							}
						} else {
							MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，战盟名字不可为空"));
						}
					} else {
						MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，创建战盟所需人物等级不足"));
					}
				} else {
					MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，创建战盟所需游戏币不足"));
				}
			} else {
				MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("您已加入战盟，如需建盟则需先退出战盟"));
			}
		} else {
			MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("游客玩家不能使用战盟功能"));
		}
	}

	public void reqGuildAutoArgeeAddGuildToServer(Player player, ReqGuildAutoArgeeAddGuildToServerMessage message) {
		if (message.getAutoArgeeAddGuild() == 0) {
			player.setAutoArgeeAddGuild((byte) 0);//不同意
		} else {
			player.setAutoArgeeAddGuild((byte) 1);//同意
		}
		ReqInnerGuildAutoArgeeAddGuildToWorldMessage sendmessage = new ReqInnerGuildAutoArgeeAddGuildToWorldMessage();
		sendmessage.setPlayerId(player.getId());
		sendmessage.setAutoArgeeAddGuild(message.getAutoArgeeAddGuild());
		MessageUtil.send_to_world(sendmessage);
	}

	public void reqGuildGetGuildListToServer(Player player, ReqGuildGetGuildListToServerMessage message) {
		ReqInnerGuildGetGuildListToWorldMessage sendmessage = new ReqInnerGuildGetGuildListToWorldMessage();
		sendmessage.setPlayerId(player.getId());
		MessageUtil.send_to_world(sendmessage);
	}

	public void reqGuildApplyAddToServer(Player player, ReqGuildApplyAddToServerMessage message) {
		if (!player.checkTempPlayer()) {
			if (PlayerState.DIE.compare(player.getState())) {
				MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，您已经死亡，无法进行战盟申请"));
				return;
			}
			
			/*xiaozhuoming: 圣盟战期间可以加入战盟
			if (!CountryAwardManager.getInstance().isKingCityAwardOpen()) {
				MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("圣盟争夺战期间无法申请入盟！"));
				return;
			}*/
			
			if (ManagerPool.biWuDaoManager.getBiwudaostate() == 1) {
				/*xiaozhuoming: 暂时没有用到
				MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("比武岛活动期间无法申请入盟！"));
				*/
				return;
			}
			
			ReqInnerGuildApplyAddToWorldMessage sendmessage = new ReqInnerGuildApplyAddToWorldMessage();
			sendmessage.setPlayerId(player.getId());
			sendmessage.setGuildId(message.getGuildId());
			MessageUtil.send_to_world(sendmessage);
		} else {
			MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("游客玩家不能使用战盟功能"));
		}
	}

	public void reqGuildInviteAddToServer(Player player, ReqGuildInviteAddToServerMessage message) {
		if (!player.checkTempPlayer()) {
			if (PlayerState.DIE.compare(player.getState())) {
				MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，您已经死亡，无法进行战盟邀请"));
				return;
			}
			/*xiaozhuoming: 圣盟战期间可以加入战盟
			if (!CountryAwardManager.getInstance().isKingCityAwardOpen()) {
				MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("圣盟争夺战期间无法进行战盟邀请！"));
				return;
			}
			if (ManagerPool.biWuDaoManager.getBiwudaostate() == 1) {
				MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("比武岛活动期间无法战盟邀请！"));
				return;
			}*/
			if (message.getGuildId() == -1) {
				if (player.getGuildId() != 0) {
					ReqInnerGuildInviteAddToWorldMessage sendmessage = new ReqInnerGuildInviteAddToWorldMessage();
					sendmessage.setPlayerId(player.getId());
					sendmessage.setGuildId(message.getGuildId());
					sendmessage.setUserId(message.getUserId());
					MessageUtil.send_to_world(sendmessage);
				} else {
					Player destPlayer = PlayerManager.getInstance().getOnLinePlayer(message.getUserId());
					if (destPlayer != null) {
						if (destPlayer.getGuildId() != 0) {
							ReqInnerGuildApplyAddToWorldMessage sendmessage = new ReqInnerGuildApplyAddToWorldMessage();
							sendmessage.setPlayerId(player.getId());
							sendmessage.setGuildId(destPlayer.getGuildId());
							MessageUtil.send_to_world(sendmessage);
						} else {
							MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("您没有加入战盟无法使用盟邀功能"));
						}
					}
				}
			} else {
				ReqInnerGuildInviteAddToWorldMessage sendmessage = new ReqInnerGuildInviteAddToWorldMessage();
				sendmessage.setPlayerId(player.getId());
				sendmessage.setGuildId(message.getGuildId());
				sendmessage.setUserId(message.getUserId());
				MessageUtil.send_to_world(sendmessage);
			}
		} else {
			MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("游客玩家不能使用战盟功能"));
		}
	}

	public void reqGuildGetMemberListToServer(Player player, ReqGuildGetMemberListToServerMessage message) {
		ReqInnerGuildGetMemberListToWorldMessage sendmessage = new ReqInnerGuildGetMemberListToWorldMessage();
		sendmessage.setPlayerId(player.getId());
		sendmessage.setGuildId(message.getGuildId());
		MessageUtil.send_to_world(sendmessage);
	}

	public void reqGuildAddMemberToServer(Player player, ReqGuildAddMemberToServerMessage message) {
		if (!player.checkTempPlayer()) {
			/*xiaozhuoming: 暂时没有用到
			if (!CountryAwardManager.getInstance().isKingCityAwardOpen()) {
				MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("圣盟争夺战期间无法添加盟派成员！"));
				return;
			}
			if (ManagerPool.biWuDaoManager.getBiwudaostate() == 1) {
				MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("比武岛活动期间无法添加盟派成员！"));
				return;
			}*/
			ReqInnerGuildAddMemberToWorldMessage sendmessage = new ReqInnerGuildAddMemberToWorldMessage();
			sendmessage.setPlayerId(player.getId());
			sendmessage.setGuildId(message.getGuildId());
			sendmessage.setUserId(message.getUserId());
			sendmessage.setArgee(message.getArgee());
			MessageUtil.send_to_world(sendmessage);
		} else {
			MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("游客玩家不能使用战盟功能"));
		}
	}

	public void reqGuildQuitToServer(Player player, ReqGuildQuitToServerMessage message) {
		if (!CountryAwardManager.getInstance().isKingCityAwardOpen()) {
			MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("攻城战期间无法进行相关操作"));
			return;
		}
		if (ManagerPool.biWuDaoManager.getBiwudaostate() == 1) {
			/*xiaozhuoming: 暂时没有用到
			MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("比武岛活动期间无法退出盟派！"));
			*/
			return;
		}
		
		ReqInnerGuildQuitToWorldMessage sendmessage = new ReqInnerGuildQuitToWorldMessage();
		sendmessage.setPlayerId(player.getId());
		sendmessage.setGuildId(message.getGuildId());
		MessageUtil.send_to_world(sendmessage);
	}

	public void reqGuildChangeNickNameToServer(Player player, ReqGuildChangeNickNameToServerMessage message) {
		if (message.getNickName() == null) {
			message.setNickName("");
		}
		if (message.getGroupName() == null) {
			message.setGroupName("");
		}
		if (message.getNickName().length() <= NickName_GroupName_Len) {
			if (message.getGroupName().length() <= NickName_GroupName_Len) {
				if (!WordFilter.getInstance().hashBadWords(message.getNickName())) {
					if (!WordFilter.getInstance().hashBadWords(message.getGroupName())) {
						ReqInnerGuildChangeNickNameToWorldMessage sendmessage = new ReqInnerGuildChangeNickNameToWorldMessage();
						sendmessage.setPlayerId(player.getId());
						sendmessage.setGuildId(message.getGuildId());
						sendmessage.setGroupName(message.getGroupName());
						sendmessage.setNickName(message.getNickName());
						sendmessage.setUserId(message.getUserId());
						MessageUtil.send_to_world(sendmessage);
					} else {
						MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，盟中分组名中包含敏感或非法字符"));
					}
				} else {
					MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，盟中昵称中包含敏感或非法字符"));
				}
			} else {
				MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，盟中分组名长度不得超过6个汉字"));
			}
		} else {
			MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，盟中昵称长度不得超过6个汉字"));
		}
	}

	public void reqGuildChangePowerLevelToServer(Player player, ReqGuildChangePowerLevelToServerMessage message) {
		if (!CountryAwardManager.getInstance().isKingCityAwardOpen() && message.getNewPowerLevel() == 1) {
			MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("攻城战期间无法进行相关操作"));
			return;
		}
		/* xuliang
		if (!player.checkCountry() && message.getNewPowerLevel() == 1) {
			MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("您不在本国，无法更换盟主！"));
			return;
		}
		*/
		ReqInnerGuildChangePowerLevelToWorldMessage sendmessage = new ReqInnerGuildChangePowerLevelToWorldMessage();
		sendmessage.setPlayerId(player.getId());
		sendmessage.setGuildId(message.getGuildId());
		sendmessage.setNewPowerLevel(message.getNewPowerLevel());
		sendmessage.setUserId(message.getUserId());
		MessageUtil.send_to_world(sendmessage);
	}

	public void reqGuildDeleteMemberToServer(Player player, ReqGuildDeleteMemberToServerMessage message) {
		if (!CountryAwardManager.getInstance().isKingCityAwardOpen()) {
			MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("攻城战期间无法进行相关操作"));
			return;
		}
		
		ReqInnerGuildDeleteMemberToWorldMessage sendmessage = new ReqInnerGuildDeleteMemberToWorldMessage();
		sendmessage.setPlayerId(player.getId());
		sendmessage.setGuildId(message.getGuildId());
		sendmessage.setUserId(message.getUserId());
		MessageUtil.send_to_world(sendmessage);
	}

	public void reqGuildAutoGuildArgeeAddToServer(Player player, ReqGuildAutoGuildArgeeAddToServerMessage message) {
		ReqInnerGuildAutoGuildArgeeAddToWorldMessage sendmessage = new ReqInnerGuildAutoGuildArgeeAddToWorldMessage();
		sendmessage.setPlayerId(player.getId());
		sendmessage.setGuildId(message.getGuildId());
		sendmessage.setAutoGuildAgreeAdd(message.getAutoGuildAgreeAdd());
		MessageUtil.send_to_world(sendmessage);
	}

	public void reqGuildChangeBulletinToServer(Player player, ReqGuildChangeBulletinToServerMessage message) {
		if (message.getGuildBulletin() == null) {
			message.setGuildBulletin("");
		}
		if (message.getGuildBulletin().length() <= Bulletin_MaxLen) {
			if (!WordFilter.getInstance().hashBadWords(message.getGuildBulletin())) {
				ReqInnerGuildChangeBulletinToWorldMessage sendmessage = new ReqInnerGuildChangeBulletinToWorldMessage();
				sendmessage.setPlayerId(player.getId());
				sendmessage.setGuildId(message.getGuildId());
				sendmessage.setGuildBulletin(message.getGuildBulletin());
				MessageUtil.send_to_world(sendmessage);
			} else {
				MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("战盟公告中包含敏感非法字符，修改失败"));
			}
		} else {
			MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("战盟公告内容过长，最多200个汉字"));
		}
	}

	public boolean CheckBackPackByGuildItem(Player player, byte type, int num) {
		long action = Config.getId();
		switch (type) {
			case 1: {//忠诚徽章
				if (BackpackManager.getInstance().getItemNum(player, 700037) >= num) {
					BackpackManager.getInstance().removeItem(player, 700037, num, Reasons.GUILD_DRAGON, action);
					return true;
				} else {
					MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，您背包中的忠诚徽章数量不足"));
					return false;
				}
			}
			case 2: {//荣誉徽章
				if (BackpackManager.getInstance().getItemNum(player, 700038) >= num) {
					BackpackManager.getInstance().removeItem(player, 700038, num, Reasons.GUILD_WHITETIGER, action);
					return true;
				} else {
					MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，您背包中的荣誉徽章数量不足"));
					return false;
				}
			}
			case 3: {//守护徽章
				if (BackpackManager.getInstance().getItemNum(player, 700039) >= num) {
					BackpackManager.getInstance().removeItem(player, 700039, num, Reasons.GUILD_SUZAKU, action);
					return true;
				} else {
					MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，您背包中的守护徽章数量不足"));
					return false;
				}
			}
			case 4: {//勇敢徽章
				if (BackpackManager.getInstance().getItemNum(player, 700040) >= num) {
					BackpackManager.getInstance().removeItem(player, 700040, num, Reasons.GUILD_BASALTIC, action);
					return true;
				} else {
					MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，您背包中的勇敢徽章数量不足"));
					return false;
				}
			}
			case 5: {//金币
				if (player.getMoney() >= num) {
					BackpackManager.getInstance().changeMoney(player, -num, Reasons.GUILD_STOCKGOLD, Config.getId());
					return true;
				} else {
					MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，您背包中的金币数量不足"));
					return false;
				}
			}
			default: {
				return false;
			}
		}
	}

	public boolean ChecnkGuildItemMax(Player player, byte type, int num) {
		switch (type) {
			case 1: {//忠诚徽章
				if (player.getGuildInfo().getDragon() + num <= Token_MaxNum) {
					return true;
				} else {
					MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，盟贡仓库中的忠诚徽章存量已达上限"));
					return false;
				}
			}
			case 2: {//荣誉徽章
				if (player.getGuildInfo().getWhiteTiger() + num <= Token_MaxNum) {
					return true;
				} else {
					MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，盟贡仓库中的荣誉徽章存量已达上限"));
					return false;
				}
			}
			case 3: {//守护徽章
				if (player.getGuildInfo().getSuzaku() + num <= Token_MaxNum) {
					return true;
				} else {
					MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，盟贡仓库中的守护徽章存量已达上限"));
					return false;
				}
			}
			case 4: {//勇敢徽章
				if (player.getGuildInfo().getBasaltic() + num <= Token_MaxNum) {
					return true;
				} else {
					MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，盟贡仓库中的勇敢徽章存量已达上限"));
					return false;
				}
			}
			case 5: {//金币
				if (player.getGuildInfo().getStockGold() + num <= Gold_MaxNum) {
					return true;
				} else {
					MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，盟贡仓库中的金币存量已达上限"));
					return false;
				}
			}
			default: {
				return false;
			}
		}
	}

	public void reqGuildSubmitItemToServer(Player player, ReqGuildSubmitItemToServerMessage message) {
		if (WServer.getInstance().isConnectWorld()) {
			if (message.getItemNum() > 0) {
				if (ChecnkGuildItemMax(player, message.getItemType(), message.getItemNum())) {
					if (CheckBackPackByGuildItem(player, message.getItemType(), message.getItemNum())) {
						ReqInnerGuildSubmitItemToWorldMessage sendmessage = new ReqInnerGuildSubmitItemToWorldMessage();
						sendmessage.setPlayerId(player.getId());
						sendmessage.setGuildId(message.getGuildId());
						sendmessage.setItemType(message.getItemType());
						sendmessage.setItemNum(message.getItemNum());
						MessageUtil.send_to_world(sendmessage);
					}
				}
			}
		} else {
			MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("服务器连接错误，不能提交盟贡物品！"));
		}
	}

	public void reqGuildChangeBannerIconToServer(Player player, ReqGuildChangeBannerIconToServerMessage message) {
		if (ManagerPool.guildFlagManager.getFlagwarstatus() == 1) {
			MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("领地争夺战期间不能更换盟旗"));
			return;
		}
		ReqInnerGuildChangeBannerIconToWorldMessage sendmessage = new ReqInnerGuildChangeBannerIconToWorldMessage();
		sendmessage.setPlayerId(player.getId());
		sendmessage.setGuildId(message.getGuildId());
		sendmessage.setBannerIcon(message.getBannerIcon());
		MessageUtil.send_to_world(sendmessage);
	}

	public void reqGuildChangeBannerNameToServer(Player player, ReqGuildChangeBannerNameToServerMessage message) {
		if (!"".equals(message.getBannerName())) {
			if (message.getBannerName().length() >= GuildBanner_MinLen && message.getBannerName().length() <= GuildBanner_MaxLen) {
				if (!WordFilter.getInstance().hashBadWords(message.getBannerName())) {
					ReqInnerGuildChangeBannerNameToWorldMessage sendmessage = new ReqInnerGuildChangeBannerNameToWorldMessage();
					sendmessage.setPlayerId(player.getId());
					sendmessage.setGuildId(message.getGuildId());
					sendmessage.setBannerName(message.getBannerName());
					MessageUtil.send_to_world(sendmessage);
				} else {
					MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，盟旗名字中包含敏感非法字符"));
				}
			} else {
				MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，盟旗名字需要在2-6个汉字之间"));
			}
		} else {
			MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("很抱歉，盟旗名字不可为空"));
		}
	}

	public void reqGuildBannerLevelUpToServer(Player player, ReqGuildBannerLevelUpToServerMessage message) {
		if (ManagerPool.guildFlagManager.getFlagwarstatus() == 1) {
			MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("领地争夺战期间不能升级盟旗"));
			return;
		}
		ReqInnerGuildBannerLevelUpToWorldMessage sendmessage = new ReqInnerGuildBannerLevelUpToWorldMessage();
		sendmessage.setPlayerId(player.getId());
		sendmessage.setGuildId(message.getGuildId());
		MessageUtil.send_to_world(sendmessage);
	}

	public void reqGuildAddDiplomaticToServer(Player player, ReqGuildAddDiplomaticToServerMessage message) {
		ReqInnerGuildAddDiplomaticToWorldMessage sendmessage = new ReqInnerGuildAddDiplomaticToWorldMessage();
		sendmessage.setPlayerId(player.getId());
		sendmessage.setDiplomaticType(message.getDiplomaticType());
		sendmessage.setGuildId(message.getGuildId());
		sendmessage.setOtherGuildId(message.getOtherGuildId());
		sendmessage.setArgee(message.getArgee());
		MessageUtil.send_to_world(sendmessage);
	}

	public void reqGuildDeleteDiplomaticToServer(Player player, ReqGuildDeleteDiplomaticToServerMessage message) {
		ReqInnerGuildDeleteDiplomaticToWorldMessage sendmessage = new ReqInnerGuildDeleteDiplomaticToWorldMessage();
		sendmessage.setPlayerId(player.getId());
		sendmessage.setDiplomaticType(message.getDiplomaticType());
		sendmessage.setGuildId(message.getGuildId());
		sendmessage.setOtherGuildId(message.getOtherGuildId());
		MessageUtil.send_to_world(sendmessage);
	}

	public void reqGuildDeleteGuildToServer(Player player, ReqGuildDeleteGuildToServerMessage message) {
		if (!CountryAwardManager.getInstance().isKingCityAwardOpen()) {
			MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("攻城战期间无法进行相关操作"));
			return;
		}
		
		if(player != null && player.getGuildId() != 0 && player.getGuildId() == CountryManager.getInstance().getKingcity().getGuildid()) {
			MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("操作失败，无法进行相关操作"));
			return;
		}
		
		if (!player.checkCountry()) {
			/*xiaozhuoing: 暂时没有用到
			MessageUtil.notify_player(player, Notifys.ERROR, ResManager.getInstance().getString("您不在本国，无法解散盟派！"));
			*/
			return;
		}
		ReqInnerGuildDeleteGuildToWorldMessage sendmessage = new ReqInnerGuildDeleteGuildToWorldMessage();
		sendmessage.setPlayerId(player.getId());
		sendmessage.setGuildId(message.getGuildId());
		MessageUtil.send_to_world(sendmessage);
	}

	public void reqGuildGetEventListToServer(Player player, ReqGuildGetEventListToServerMessage message) {
		ReqInnerGuildGetEventListToWorldMessage sendmessage = new ReqInnerGuildGetEventListToWorldMessage();
		sendmessage.setPlayerId(player.getId());
		sendmessage.setGuildId(message.getGuildId());
		MessageUtil.send_to_world(sendmessage);
	}

	public void ResInnerGuildAloneGuildInfoToServer(Player player, ResInnerGuildAloneGuildInfoToServerMessage message) {
		if (player == null) {
			return;
		}
		ResGuildAloneGuildInfoToClientMessage sendmessage = new ResGuildAloneGuildInfoToClientMessage();
		if (message.getGuildInfo().getGuildId() != 0) {
			if (player.getGuildId() == 0) {
				ResGuildDeleteInfoRoundToClientMessage roundToClientMessage = new ResGuildDeleteInfoRoundToClientMessage();
				roundToClientMessage.setPlayerid(player.getId());
				roundToClientMessage.setGuildid(message.getGuildInfo().getGuildId());
				MessageUtil.tell_round_message(player, roundToClientMessage);
			}
			player.setGuildId(message.getGuildInfo().getGuildId());
		}
		player.setGuildInfo(message.getGuildInfo());
		if (message.getNotifyType() == Notify_Create) {
			long action = Config.getId();
			if (!BackpackManager.getInstance().changeMoney(player, -Create_Need_Gold, Reasons.GUILD_CREATE, action)) {
				BackpackManager.getInstance().changeMoney(player, -player.getMoney(), Reasons.GUILD_CREATE, action);
			}
			guildChangeBroadcast(player);
			PlayerAttributeManager.getInstance().countPlayerAttribute(player, PlayerAttributeType.GUILDBANNER);
			GuildFlagManager.getInstance().synGuildTmpInfo(player.getGuildInfo(), true);
		} else if (message.getNotifyType() == Notify_AddOrUpdate || message.getNotifyType() == Notity_ChangeBanner) {
			PlayerAttributeManager.getInstance().countPlayerAttribute(player, PlayerAttributeType.GUILDBANNER);
			if (message.getNotifyType() == Notity_ChangeBanner) {
				GuildFlagManager.getInstance().updateFlag(player.getGuildInfo().getGuildId(), player.getGuildInfo().getBannerIcon(), player.getGuildInfo().getBannerLevel());
				GuildFlagManager.getInstance().updateFlaghead(player.getGuildInfo().getGuildId(), player.getGuildInfo().getBangZhuName(), player.getGuildInfo().getBangZhuid());
				GuildFlagManager.getInstance().synGuildTmpInfo(player.getGuildInfo(), true);
			}
			message.setNotifyType(Notify_AddOrUpdate);
		} else if (message.getNotifyType() == Notity_AddMember) {
			PlayerAttributeManager.getInstance().countPlayerAttribute(player, PlayerAttributeType.GUILDBANNER);
			if (player.getPkState() == 2) {
				player.setPkState(1);
			}
		} else if (message.getNotifyType() == Notity_BannerBuff){
			GuildFlagManager.getInstance().checkAndAddGuildFlagBUFF(player, MapManager.getInstance().getMap(player));
			GuildFlagManager.getInstance().synGuildTmpInfo(player.getGuildInfo(), false);
			return;
		} else if (message.getNotifyType() == Notity_KingCity) {
			log.error(String.format("接收到世界服务器王城信息=playername=%s=playerid=%s=guildid=%s", player.getName(), String.valueOf(player.getId()), String.valueOf(player.getGuildId())));
			CountryAwardManager.getInstance().removeKingCityBuff(player);
			if (CountryAwardManager.getInstance().isKingCityAwardOpen()) {
				log.error("接收到世界服务器王城信息isKingCityAwardOpen");
				if (player.checkCountry()) {
					log.error("接收到世界服务器王城信息checkCountry");
					if (player.checkKingCity()) {
						log.error("接收到世界服务器王城信息checkKingCity=" + CountryManager.getInstance().getKingcity().getGuildid());
						KingData kingData = CountryAwardManager.getInstance().getCurKingData();
						if (kingData != null) {
							log.error(String.format("接收到世界服务器王城信息getCurKingData=[%s]", JSON.toJSONString(kingData)));
							if (kingData.getPlayerid() != player.getGuildInfo().getBangZhuid()) {
								CountryManager.getInstance().addking(player.getGuildInfo().getBangZhuid(), player.getGuildInfo().getBangZhuName());
								CountryManager.getInstance().savekingcity(CountryManager.getInstance().getKingcity());
								log.error(String.format("接收到世界服务器王城信息addking=[%d][%s]", player.getGuildInfo().getBangZhuid(), player.getGuildInfo().getBangZhuName()));
							} else {
								log.error("接收到世界服务器王城信息kingData.getPlayerid() == player.getGuildInfo().getBangZhuid()");
							}
						}
						CountryAwardManager.getInstance().setKingCityBuff(player);
					}
				} else {
					if (CountryManager.kingcitymap.containsKey(player.getCountry())) {
						long otherKingGuildId = CountryManager.kingcitymap.get(player.getCountry());
						if (otherKingGuildId != 0) {
							if (player.getGuildInfo().getGuildId() == otherKingGuildId) {
								CountryAwardManager.getInstance().setOtherKingCityBuff(player);
							}
						}
					}
				}
			}
			return;
		} else if (message.getNotifyType() == Notify_Delete || message.getNotifyType() == Notity_DeleteGuild) {
			if (player.getGuildId() == CountryAwardManager.getInstance().getKingCity().getGuildid()) {
				CountryAwardManager.getInstance().removeKingCityBuff(player);
				if (message.getNotifyType() == Notity_DeleteGuild) {
					KingData kingData = CountryManager.getInstance().getKingcity().gCurKingData();
					if (kingData != null) {
						kingData.setAbdicatetime(System.currentTimeMillis() / 1000);
					}
					CountryAwardManager.getInstance().getKingCity().setGuildid(0);
					CountryAwardManager.getInstance().getKingCity().setGuildname("");
					CountryManager.getInstance().savekingcity(CountryManager.getInstance().getKingcity());
				}
			}
			GuildFlagManager.getInstance().synGuildTmpInfo(player.getGuildInfo(), true);
			if (message.getNotifyType() == Notity_DeleteGuild) {
				message.setNotifyType(Notify_Delete);
				GuildFlagManager.getInstance().removeguildflag(player.getGuildId());
				GuildTmpInfoMap.remove(player.getGuildId());
			}
			player.setGuildInfo(new GuildInfo());
			player.setGuildId(0);
			GuildFlagManager.getInstance().checkAndAddGuildFlagBUFF(player, MapManager.getInstance().getMap(player));
			PlayerAttributeManager.getInstance().countPlayerAttribute(player, PlayerAttributeType.GUILDBANNER);
			if (player.getPkState() == 2) {
				player.setPkState(3);
			}
		}
		sendmessage.setNotifyType(message.getNotifyType());
		sendmessage.setGuildInfo(message.getGuildInfo());
		MessageUtil.tell_player_message(player, sendmessage);
	}

	public void ResInnerGuildAloneMemberInfoToServer(Player player, ResInnerGuildAloneMemberInfoToServerMessage message) {
		if (player == null) {
			return;
		}
		ResGuildAloneMemberInfoToClientMessage sendmessage = new ResGuildAloneMemberInfoToClientMessage();
		if (message.getMemberInfo().getUserId() == player.getId()) {
			if (message.getNotifyType() == Notify_Delete || message.getNotifyType() == Notity_DeleteGuild) {
				ResGuildDeleteInfoRoundToClientMessage roundToClientMessage = new ResGuildDeleteInfoRoundToClientMessage();
				roundToClientMessage.setPlayerid(player.getId());
				roundToClientMessage.setGuildid(0);
				MessageUtil.tell_round_message(player, roundToClientMessage);

				player.setMemberInfo(new MemberInfo());
				player.setGuildId(0);
				if (message.getNotifyType() == Notity_DeleteGuild) {
					message.setNotifyType(Notify_Delete);
				}
				guildChangeBroadcast(player);
				CountryAwardManager.getInstance().removeKingCityBuff(player);
			} else {
				MemberInfo oldMemberInfo = player.getMemberInfo();
				MemberInfo newMemberInfo = message.getMemberInfo();
				if(oldMemberInfo.getGuildPowerLevel() != newMemberInfo.getGuildPowerLevel()) {
					player.setLastChangePowerLevelTime(System.currentTimeMillis());
					
					if(oldMemberInfo.getGuildPowerLevel() == 1 || newMemberInfo.getGuildPowerLevel() == 1) {
						CountryAwardManager.getInstance().removeKingCityBuff(player);
						CountryAwardManager.getInstance().setKingCityBuff(player);
					}
				}
				player.setMemberInfo(newMemberInfo);
				player.setGuildId(message.getMemberInfo().getGuildId());
				player.setContributionPoint(player.getMemberInfo().getContributionPoint());
				if(message.getNotifyType() == Notity_AddMember) guildChangeBroadcast(player);		
			}
		}
		if (message.getNotifyType() == Notity_ChangeBanner || message.getNotifyType() == Notity_BannerBuff) {
			message.setNotifyType(Notify_AddOrUpdate);
		}
		sendmessage.setNotifyType(message.getNotifyType());
		sendmessage.setMemberInfo(message.getMemberInfo());
		player.setContributionPoint(message.getMemberInfo().getContributionPointHistory());
		MessageUtil.tell_player_message(player, sendmessage);
	}
	
	private void guildChangeBroadcast(Player player) {
		if(player == null) return;
		ResGuildChangeBroadcastMessage resGuildChangeBroadcastMessage = new ResGuildChangeBroadcastMessage();
		resGuildChangeBroadcastMessage.setPersonId(player.getId());
		if(player.getGuildInfo() != null) {
			resGuildChangeBroadcastMessage.setGuildId(player.getGuildInfo().getGuildId());
			resGuildChangeBroadcastMessage.setGuildName(player.getGuildInfo().getGuildName());
		}
		MessageUtil.tell_round_message(player, resGuildChangeBroadcastMessage);
	}
	
	public void reqInnerGuildNotifyToWorld(Player player, int notifytype, String notifyString) {
		if (player != null && player.getGuildId() != 0) {
			ReqInnerGuildNotifyToWorldMessage sendMessage = new ReqInnerGuildNotifyToWorldMessage();
			sendMessage.setPlayerId(player.getId());
			sendMessage.setNotifytype(notifytype);
			sendMessage.setGuildNotify(notifyString);
			sendMessage.setNotify((byte)0);
			sendMessage.setSubType(0);
			MessageUtil.send_to_world(sendMessage);
		}
	}

	public void reqInnerGuildNotifyToWorld(Player player, int notifytype, String notifyString, Notifys type, int subType) {
		if (player != null && player.getGuildId() != 0) {
			ReqInnerGuildNotifyToWorldMessage sendMessage = new ReqInnerGuildNotifyToWorldMessage();
			sendMessage.setPlayerId(player.getId());
			sendMessage.setNotifytype(notifytype);
			sendMessage.setGuildNotify(notifyString);
			sendMessage.setNotify(type.getValue());
			sendMessage.setSubType(subType);
			MessageUtil.send_to_world(sendMessage);
		}
	}

	public static String getEventString(String eventType, Object... values) {
		try {
			if (eventType.equals(ResManager.getInstance().getString("成员被其他玩家击败"))) {
				return String.format(ResManager.getInstance().getString("本盟成员{@}玩家名在地图【{@}】被玩家【{@}】击败"), values);
			} else if (eventType.equals(ResManager.getInstance().getString("成员击败其他玩家"))) {
				return String.format(ResManager.getInstance().getString("本盟成员{@}玩家名在地图【{@}】击败了玩家【{@}】"), values);
			} else if (eventType.equals(ResManager.getInstance().getString("成员击败BOSS"))) {
				return String.format(ResManager.getInstance().getString("本盟成员{@}玩家名在地图【{@}】击败BOSS【%s】"), values);
			} else if (eventType.equals(ResManager.getInstance().getString("成员被BOSS击败"))) {
				return String.format(ResManager.getInstance().getString("本盟成员{@}玩家名在地图【{@}】被BOSS【%s】击败"), values);
			}
		} catch (Exception e) {
			GuildServerManager.getInstance().log.error(String.format("得到事件字符串出错 类型[%s]", eventType));
		}
		return "";
	}

	public static String getParserString(String parseString, ParseUtil.Parm... parmObjects) {
		ParseUtil parseUtil = new ParseUtil();
		parseUtil.setValue(parseString, parmObjects);
		return parseUtil.toString();
	}

	public void reqInnerKingCityEventToWorld(Player player, int eventType, String guildevent) {
		ReqInnerKingCityEventToWorldMessage sendMessage = new ReqInnerKingCityEventToWorldMessage();
		sendMessage.setPlayerId((player == null) ? 0 : player.getId());
		sendMessage.setEventtype(eventType);
		sendMessage.setGuildevent(guildevent);
		MessageUtil.send_to_world(sendMessage);
	}
//	/**
//	 * 上线清除盟旗BUFF
//	 *
//	 * @param player 被清除玩家
//	 * @return
//	 */
//	public void loginClearBannerBuff(Player player) {
//		for (Map.Entry<Integer, Q_guildbannerBean> entry : DataManager.getInstance().q_guildbannerContainer.getMap().entrySet()) {
//			Q_guildbannerBean q_guildbannerBean = entry.getValue();
//			if (q_guildbannerBean != null) {
//				BuffManager.getInstance().removeByBuffId(player, q_guildbannerBean.getBuffid());
//			}
//		}
//	}
	
	/**获取盟派临时数据
	 * 
	 * @param gid
	 * @return
	 */
	public GuildTmpInfo getGuildTmpInfo(long gid){
		if (gid > 0) {
			if(GuildTmpInfoMap.containsKey(gid)){
				return GuildTmpInfoMap.get(gid);
			} else {
				GuildBean guildBean = this.guildDao.selectById(gid);
				if(guildBean != null) {
					GuildData guildData = (GuildData) JSONserializable.toObject(guildBean.getGuilddata(), GuildData.class);
					ManagerPool.guildFlagManager.synGuildTmpInfo(guildData.toInfo(), true);
					return GuildTmpInfoMap.get(gid);
				}
			}
		}
		return null;
	}
	
	/**
	 * 同步友好战盟
	 * @param guilds
	 */
	public void syncGuildFriend(List<FriendGuild> guilds){
		if(guilds==null) return;
		int size = guilds.size();
		for (int i = 0; i < size; i++) {
			FriendGuild friendGuild = guilds.get(i);
			GuildFriendMap.put(friendGuild.getGuildId(), friendGuild);
		}
	}
	
	/**
	 * 同步友好战盟
	 * @param guilds
	 */
	public void syncGuildFriend(FriendGuild friendGuild){
		if(friendGuild==null) return;
		GuildFriendMap.put(friendGuild.getGuildId(), friendGuild);
	}
	
	/**
	 * 是否同盟战盟
	 * @param guild1
	 * @param guild2
	 * @return
	 */
	public boolean isFriendGuild(long guild1, long guild2){
		if(GuildFriendMap.containsKey(guild1)){
			FriendGuild friendGuild = GuildFriendMap.get(guild1);
			return friendGuild.getFriendGuilds().contains(guild2);
		}
		
		return false;
	}
	
	public String getGuildPower(int powerLevel){
		if (powerLevel == 1){
			return ResManager.getInstance().getString("盟主");
		}else if(powerLevel == 2){
			return ResManager.getInstance().getString("副盟主");
		}else if(powerLevel == 3){
			return ResManager.getInstance().getString("战盟精英");
		}else if(powerLevel == 4){
			return ResManager.getInstance().getString("战盟成员");
		}
		
		return "";
	}
}
