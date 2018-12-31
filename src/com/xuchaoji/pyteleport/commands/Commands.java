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
				player.sendMessage(ChatColor.AQUA+"Py传送命令简介:\n"
						+ "/py set 更新自己所在坐标(设置后可接受传送一次，传送后自动失效)\n"
						+ "/py to player 传送到player设置的坐标(非实时，请商量好传送后再传送)\n"
						+ "/py del 删除自己的坐标");
			}else if(args.length==1){
				//set 或delete 自己的坐标
				if(args[0].equalsIgnoreCase("set")) {
					saveLocation(player);
					plugin.saveConfig();
					player.sendMessage(ChatColor.AQUA+"已设置当前位置为传送点，通知基友使用/py to "+player.getName()+"传送过来吧");
				}else if(args[0].equalsIgnoreCase("del")) {
					plugin.getConfig().set("players."+player.getName(),null);
					plugin.saveConfig();
					player.sendMessage(ChatColor.AQUA+"已删除传送点");
				}else {
					player.sendMessage(ChatColor.RED+"指令参数有误。");
					player.sendMessage(ChatColor.AQUA+"Py传送命令简介:\n"
							+ "/py set 更新自己所在坐标(设置后可接受传送一次，传送后自动失效)\n"
							+ "/py to player 传送到player设置的坐标(非实时，请商量好传送后再传送)\n"
							+ "/py del 删除自己的坐标");
				}
			}else if(args.length==2) {
				//传送到某玩家的坐标
				if(args[0].equalsIgnoreCase("to")) {
					for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()){
						if(args[1].equals(onlinePlayer.getName())) {
							player.sendMessage(ChatColor.AQUA+"已找到该玩家，正在查找传送点...");
							if(plugin.getConfig().contains("players."+onlinePlayer.getName())) {
								Location loc = getTeleportPoint(onlinePlayer);
								player.teleport(loc);
								player.sendMessage(ChatColor.AQUA+"已传送到传送点!");
								plugin.getConfig().set("players."+onlinePlayer.getName(),null);
								plugin.saveConfig();
								onlinePlayer.sendMessage(ChatColor.AQUA+player.getName()+"已传送到你设置的传送点，传送点已自动失效。如需传送，请重新设置。");
								break;
							}else {
								player.sendMessage(ChatColor.AQUA+"该玩家没设置传送点，请通知其设置传送点后再试。");
								onlinePlayer.sendMessage(ChatColor.AQUA+player.getName()+"尝试传送到你这里，但是你未设置传送点。\n设置传送点指令：/py set");
								break;
							}
							
						}
						player.sendMessage("玩家不存在或不在线.");
					}
				}else {
					player.sendMessage(ChatColor.RED+"指令参数有误。");
					player.sendMessage(ChatColor.AQUA+"Py传送命令简介:\n"
							+ "/py set 更新自己所在坐标(设置后可接受传送一次，传送后自动失效)\n"
							+ "/py to player 传送到player设置的坐标(非实时，请商量好传送后再传送)\n"
							+ "/py del 删除自己的坐标");
				}
			}else {
				player.sendMessage(ChatColor.AQUA+"Py传送命令简介:\n"
						+ "/py set 更新自己所在坐标(设置后可接受传送一次，传送后自动失效)\n"
						+ "/py to player 传送到player设置的坐标(非实时，请商量好传送后再传送)\n"
						+ "/py del 删除自己的坐标");
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
