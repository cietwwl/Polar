package com.game.db.dao;

/**
 * @author ExcelUtil Auto Maker
 *
 * @version 1.0.0
 *
 * BlackList Dao
 */
import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.game.db.BaseDao;
import com.game.db.bean.BlackListBean;
import com.game.utils.LoggerProvider;
import com.game.utils.TimeUtil;

public class BlackListDao extends BaseDao {

	private Logger log = Logger.getLogger(BlackListDao.class);
	private SqlSessionFactory sqlMapper = getSqlMapper();

	@SuppressWarnings("unchecked")
	public List<BlackListBean> select() {
		SqlSession session = sqlMapper.openSession();
		try {
			long currentTimeMillis = System.currentTimeMillis();
			List<BlackListBean> list = (List<BlackListBean>) session.selectList("blacklist.select");
			LoggerProvider.getDbconsuminglog().info("blacklist.select "+TimeUtil.getDurationToNow(currentTimeMillis)+"ms");
			return list;
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<BlackListBean> selectbyTypeState(int type, int state) {
		SqlSession session = sqlMapper.openSession();
		try {
			long currentTimeMillis = System.currentTimeMillis();
			Map<String, Integer> paramap = new HashMap<String, Integer>();
			paramap.put("type", type);  paramap.put("state", state);
			List<BlackListBean> list = (List<BlackListBean>) session.selectList("blacklist.selectbytypestate", paramap);
			LoggerProvider.getDbconsuminglog().info("blacklist.selectbytypestate "+TimeUtil.getDurationToNow(currentTimeMillis)+"ms");
			return list;
		} finally {
			session.close();
		}
	}

	public Set<String> selectUserSetByTypeState(int type, int state){
		Set<String> usernames = new HashSet<String>();
		List<BlackListBean> list = selectbyTypeState(type, state);
		if(list!=null && list.size()>0){
			for(BlackListBean bean: list){
				usernames.add(bean.getUsername());
			}
		}
		return usernames;
	}
	
	public Map<String, BlackListBean> selectBlackMapByTypeState(int type, int state){
		Map<String, BlackListBean> map = new HashMap<String, BlackListBean>();
		List<BlackListBean> list = selectbyTypeState(type, state);
		if(list!=null && list.size()>0){
			for(BlackListBean bean: list){
				map.put(bean.getUsername(), bean);
			}
		}
		return map;
	}
	
	public int updateByUsernameType(BlackListBean blacklistbean){
		SqlSession session = sqlMapper.openSession();
		try {
			long currentTimeMillis = System.currentTimeMillis();
			int rows = session.update("blacklist.updatebyusernametype", blacklistbean);
			LoggerProvider.getDbconsuminglog().info("blacklist.updatebyusernametype "+TimeUtil.getDurationToNow(currentTimeMillis)+"ms");
			session.commit();
			return rows;
		} catch (Exception e) {
			log.error(e);
		} finally {
			session.close();
		}
		return 0;
	}
	
	public int insert(BlackListBean blacklistbean) {
		SqlSession session = sqlMapper.openSession();
		try {
			long currentTimeMillis = System.currentTimeMillis();
			int rows = session.insert("blacklist.insert", blacklistbean);
			LoggerProvider.getDbconsuminglog().info("blacklist.insert "+TimeUtil.getDurationToNow(currentTimeMillis)+"ms");
			session.commit();
			return rows;
		} catch (Exception e) {
			log.error(e, e);
		} finally {
			session.close();
		}
		return 0;
	}

    public int insertNewIp(BlackListBean blackListBean) {
        SqlSession session = sqlMapper.openSession();
        try {
            long currentTimeMillis = System.currentTimeMillis();
            int rows = session.insert("blacklist.insertNewIp", blackListBean);
            LoggerProvider.getDbconsuminglog().info("blacklist.insertNewIp "+TimeUtil.getDurationToNow(currentTimeMillis)+"ms");
            session.commit();
            return rows;
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            session.close();
        }
        return 0;
    }

    public int removeByUsernameAndType(BlackListBean blackListBean) {
        SqlSession session = sqlMapper.openSession();
        try {
            long currentTimeMillis = System.currentTimeMillis();
            int rows = session.delete("blacklist.deleteByUsernameAndType", blackListBean);
            LoggerProvider.getDbconsuminglog().info("blacklist.deleteByUsernameAndType "+TimeUtil.getDurationToNow(currentTimeMillis)+"ms");
            session.commit();
            return rows;
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            session.close();
        }
        return 0;
    }

    public int removeIp(String ip) {
        BlackListBean blackListBean = new BlackListBean();
        blackListBean.setUsername(ip);
        blackListBean.setType(4);
        return removeByUsernameAndType(blackListBean);
    }

    public int removeAllIps() {
        SqlSession session = sqlMapper.openSession();
        try {
            long currentTimeMillis = System.currentTimeMillis();
            int rows = session.delete("blacklist.deleteAllIps");
            LoggerProvider.getDbconsuminglog().info("blacklist.deleteAllIps "+TimeUtil.getDurationToNow(currentTimeMillis)+"ms");
            session.commit();
            return rows;
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            session.close();
        }
        return 0;
    }

    public int addAllIps(List<String> ips) {
        int rows = 0;
        for (String ip : ips) {
            BlackListBean blackListBean = new BlackListBean();
            blackListBean.setUsername(ip);
            blackListBean.setType(4);
            rows += insertNewIp(blackListBean);
        }
        return rows;
    }

    public int removeIps(List<String> ips) {
        int rows = 0;
        for (String ip : ips) {
            rows += removeIp(ip);
        }
        return rows;
    }

    public void reloadAllIps(List<String> ips) {
        removeAllIps();
        addAllIps(ips);
    }

    public List<String> getAllIps() {
        List<String> ips = new ArrayList<>();
        List<BlackListBean> blackListBeans = selectbyTypeState(4, 0);
        for (BlackListBean bean : blackListBeans) {
            ips.add(bean.getUsername());
        }
        return ips;
    }

	public static void main(String[] args){
//		BlackListDao dao = new BlackListDao();
//		BlackListBean bean = new BlackListBean();
//		bean.setUsername("tangc36"); bean.setEndtime(System.currentTimeMillis()+3600000);
//		bean.setEndcount(0); bean.setNowcount(0); bean.setType(1); bean.setState(0);
//		System.out.println(dao.insert(bean));
//		
//		List<BlackListBean> list = dao.selectbyTypeState(1, 0);
//		System.out.println(list.size());
//		System.out.println(list.get(0).getUsername());
//		System.out.println(list.get(0).getEndtime());
	}
}