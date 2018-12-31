package com.xuchaoji.pyteleport;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import com.xuchaoji.pyteleport.commands.*;

public class PyTeleport extends JavaPlugin{
	private FileConfiguration cfg= getConfig();
	@Override
	public void onEnable() {
		cfg.addDefault("players", "");
		cfg.options().copyDefaults(true);
		saveConfig();
		this.getCommand("py").setExecutor(new Commands());
		System.out.println(ChatColor.GREEN+"[PyTeleport] 加载成功~");
	}
	
	@Override 
	public void onDisable() {
		System.out.println(ChatColor.RED+"[PyTeleport] 已停止~");
	}

}
