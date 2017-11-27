package kdr;

import java.io.File;
import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import kdrEvent.Event;

public class Main extends PluginBase implements Listener{
	public static HashMap<String, Integer> map = new HashMap<>();
	public static Config KDR;
	public static Main instance;
	@Override
	public void onEnable(){
		this.getDataFolder().mkdir();
		KDR = new Config(this.getDataFolder()+File.separator+"KDR.yaml",Config.YAML);
		instance=this;
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getServer().getPluginManager().registerEvents(new Event(),this);
		this.getLogger().info("KDR Enabled!-");
	}
	@Override
	public void onDisable(){
		KDR.save();
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent ev){
		if(!KDR.exists(ev.getPlayer().getName().toLowerCase())){
			map.put("D",0);
			map.put("K",0);
			
			KDR.set(ev.getPlayer().getName().toLowerCase(),map);
			KDR.save();
		}
	}
	@EventHandler
	public void onDeath(PlayerDeathEvent ev){
		if(ev.getEntity().getKiller() instanceof Player){
			Player player=ev.getEntity();
			int i = Main.map.get("D");
			Main.map.put("D",i+1);
			Main.KDR.set(ev.getEntity().getName().toLowerCase(),Main.map);
			Main.KDR.save();
			Main.instance.getServer().broadcastMessage
			("["+player.getName()+"]  Kill:"+Main.map.get("K")+"  Death:"+Main.map.get("D"));
	}
	}
	@EventHandler
	public void onKill(PlayerDeathEvent ev){
		if(ev.getEntity().getKiller() instanceof Player){
			Player player = (Player)ev.getEntity().getKiller();
			int i=Main.map.get("K");
			Main.map.put("K",i+1);
			Main.KDR.set(player.getName(),Main.map);
			Main.KDR.save();
			Main.instance.getServer().broadcastMessage
			("["+player.getName()+"]  Kill:"+Main.map.get("K")+"  Death:"+Main.map.get("D"));
		}
		
	}
	}

