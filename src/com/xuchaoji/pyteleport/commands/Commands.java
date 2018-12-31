package com.xuchaoji.pyteleport.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.xuchaoji.pyteleport.PyTeleport;

public class Commands  implements CommandExecutor {
	private Plugin plugin=PyTeleport.getPlugin(PyTeleport.class);
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			if(args.length==0) {
				player.sendMessage(ChatColor.AQUA+"Py����������:\n"
						+ "/py set �����Լ���������(���ú�ɽ��ܴ���һ�Σ����ͺ��Զ�ʧЧ)\n"
						+ "/py to player ���͵�player���õ�����(��ʵʱ���������ô��ͺ��ٴ���)\n"
						+ "/py del ɾ���Լ�������");
			}else if(args.length==1){
				//set ��delete �Լ�������
				if(args[0].equalsIgnoreCase("set")) {
					saveLocation(player);
					plugin.saveConfig();
					player.sendMessage(ChatColor.AQUA+"�����õ�ǰλ��Ϊ���͵㣬֪ͨ����ʹ��/py to "+player.getName()+"���͹�����");
				}else if(args[0].equalsIgnoreCase("del")) {
					plugin.getConfig().set("players."+player.getName(),null);
					plugin.saveConfig();
					player.sendMessage(ChatColor.AQUA+"��ɾ�����͵�");
				}else {
					player.sendMessage(ChatColor.RED+"ָ���������");
					player.sendMessage(ChatColor.AQUA+"Py����������:\n"
							+ "/py set �����Լ���������(���ú�ɽ��ܴ���һ�Σ����ͺ��Զ�ʧЧ)\n"
							+ "/py to player ���͵�player���õ�����(��ʵʱ���������ô��ͺ��ٴ���)\n"
							+ "/py del ɾ���Լ�������");
				}
			}else if(args.length==2) {
				//���͵�ĳ��ҵ�����
				if(args[0].equalsIgnoreCase("to")) {
					for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()){
						if(args[1].equals(onlinePlayer.getName())) {
							player.sendMessage(ChatColor.AQUA+"���ҵ�����ң����ڲ��Ҵ��͵�...");
							if(plugin.getConfig().contains("players."+onlinePlayer.getName())) {
								Location loc = getTeleportPoint(onlinePlayer);
								player.teleport(loc);
								player.sendMessage(ChatColor.AQUA+"�Ѵ��͵����͵�!");
								plugin.getConfig().set("players."+onlinePlayer.getName(),null);
								plugin.saveConfig();
								onlinePlayer.sendMessage(ChatColor.AQUA+player.getName()+"�Ѵ��͵������õĴ��͵㣬���͵����Զ�ʧЧ�����贫�ͣ����������á�");
								break;
							}else {
								player.sendMessage(ChatColor.AQUA+"�����û���ô��͵㣬��֪ͨ�����ô��͵�����ԡ�");
								onlinePlayer.sendMessage(ChatColor.AQUA+player.getName()+"���Դ��͵������������δ���ô��͵㡣\n���ô��͵�ָ�/py set");
								break;
							}
							
						}
						player.sendMessage("��Ҳ����ڻ�����.");
					}
				}else {
					player.sendMessage(ChatColor.RED+"ָ���������");
					player.sendMessage(ChatColor.AQUA+"Py����������:\n"
							+ "/py set �����Լ���������(���ú�ɽ��ܴ���һ�Σ����ͺ��Զ�ʧЧ)\n"
							+ "/py to player ���͵�player���õ�����(��ʵʱ���������ô��ͺ��ٴ���)\n"
							+ "/py del ɾ���Լ�������");
				}
			}else {
				player.sendMessage(ChatColor.AQUA+"Py����������:\n"
						+ "/py set �����Լ���������(���ú�ɽ��ܴ���һ�Σ����ͺ��Զ�ʧЧ)\n"
						+ "/py to player ���͵�player���õ�����(��ʵʱ���������ô��ͺ��ٴ���)\n"
						+ "/py del ɾ���Լ�������");
			}
			
		}else {
			sender.sendMessage(ChatColor.RED+"This is a player command, you are not a player!");
		}
		return true;
	}
	
	private Location getTeleportPoint(Player player) {
		double x=plugin.getConfig().getDouble("players."+player.getName()+"."+".X");
		double y=plugin.getConfig().getDouble("players."+player.getName()+"."+".Y");
		double z=plugin.getConfig().getDouble("players."+player.getName()+"."+".Z");
		float yaw=(float)plugin.getConfig().getDouble("players."+player.getName()+"."+".Yaw");
		float pitch=(float)plugin.getConfig().getDouble("players."+player.getName()+"."+".Pitch");
		Location location=new Location(Bukkit.getWorld(plugin.getConfig().getString("players."+player.getName()+"."+".World")), x, y, z, yaw, pitch);
		return location;
		
	}

	private void saveLocation(Player player) {
		Location location = player.getLocation();
		plugin.getConfig().set("players."+player.getName()+"."+".World", location.getWorld().getName());
		plugin.getConfig().set("players."+player.getName()+"."+".Yaw", location.getYaw());
		plugin.getConfig().set("players."+player.getName()+"."+".Pitch", location.getPitch());
		plugin.getConfig().set("players."+player.getName()+"."+".X", location.getX());
		plugin.getConfig().set("players."+player.getName()+"."+".Y", location.getY());
		plugin.getConfig().set("players."+player.getName()+"."+".Z", location.getZ());
		plugin.saveConfig();
		
	}

}
