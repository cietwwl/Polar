package com.game.server.loader;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.game.server.config.GameConfig;

/** 
 * 
 * 服务器启动配置加载
 */
public class GameConfigXmlLoader {
	
	private Logger log = Logger.getLogger(GameConfigXmlLoader.class);
	
	//初始化服务器配置信息
	public GameConfig load(String file){
        try
        {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputStream in = new FileInputStream(file);
            Document doc = builder.parse(in);
            NodeList list = doc.getElementsByTagName("servers");
            if(list.getLength() > 0)
            {
            	GameConfig config = new GameConfig();
                Node node = list.item(0);
                NodeList childs = node.getChildNodes();
                
                for (int i = 0; i < childs.getLength(); i++) {
                	if(("server").equals(childs.item(i).getNodeName())){
                		NodeList schilds = childs.item(i).getChildNodes();
                		ServerConfig sconfig = new ServerConfig();
	                    for (int j = 0; j < schilds.getLength(); j++) {
	    					if(("country").equals(schilds.item(j).getNodeName())){
	    						sconfig.setCountry(Integer.parseInt(schilds.item(j).getTextContent()));
	    					}else if(("server-id").equals(schilds.item(j).getNodeName())){
	    						sconfig.setServerId(Integer.parseInt(schilds.item(j).getTextContent()));
	    					}
	    				}
	                    if(!config.getCountrys().containsKey(sconfig.getCountry())) config.getCountrys().put(sconfig.getCountry(), sconfig.getServerId());
	                    config.getServers().put(sconfig.getServerId(), sconfig.getCountry());
                	}
				}
                
                
                in.close();
                return config;
            }
        }catch(Exception e){
        	log.error(e,e);
        }
        return null;
	}

	private class ServerConfig{
		
		private int country;
		
		private int serverId;

		public int getCountry() {
			return country;
		}

		public void setCountry(int country) {
			this.country = country;
		}

		public int getServerId() {
			return serverId;
		}

		public void setServerId(int serverId) {
			this.serverId = serverId;
		}
		
	}
}
