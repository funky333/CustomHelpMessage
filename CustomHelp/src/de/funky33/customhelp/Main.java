package de.funky33.customhelp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin
{
	@EventHandler
	public void onEnable()
	{
		loadConfig();
		
		getLogger().info("Plugin by funky33");
		getLogger().info("Plugin Enabled");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		//Custom Help
		String help = getConfig().getString("config.activated");
		
		if (help == "true")
		{
			if (cmd.getName().equalsIgnoreCase("help"))
			{
				Integer pages = getConfig().getInt("config.pages");
				if (args.length < 1)
				{
					String page1 = getConfig().getString("config.page1");
					page1 = page1.replace("[", "");
					page1 = page1.replace("]", "");
					page1 = page1.replaceAll(", ", "\n");
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "Help Page (1/" + pages + ChatColor.WHITE + ")"));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', page1));
				}
				
				if (args.length == 1)
				{
					
					getHelpPage(args[0], sender, pages);
					
					if (args[0].equalsIgnoreCase("reload"))
					{
						reloadConfig();
						sender.sendMessage(ChatColor.GREEN + "Config Reloaded!");
					}
				}
				else if (args.length > 1)
				{
					sender.sendMessage(ChatColor.RED + "Too many arguments Usage: /help");
				}
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "This Command is deactivated in config.yml. Contact an Server Admin if you think that this is an issue.");
			}
		}
		
		
		
		return false;
	}
	
	public void loadConfig()
	{
		FileConfiguration cfg = this.getConfig();
		cfg.options().copyDefaults(true);
		saveConfig();
	}
	
	public void getHelpPage(String pageNumber, CommandSender sender, int pages) {
		String page = getConfig().getString("config.page" + pageNumber);
		page = page.replace("[", "");
		page = page.replace("]", "");
		page = page.replaceAll(", ", "\n");
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "Help Page (" + pageNumber + "/" + pages + ChatColor.WHITE + ")"));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', page));
	}
}
