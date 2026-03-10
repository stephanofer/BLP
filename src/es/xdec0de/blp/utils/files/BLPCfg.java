package es.xdec0de.blp.utils.files;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import es.xdec0de.blp.BLP;

public class BLPCfg {

	public static FileConfiguration cfg;
	private static File file;
	public static java.util.TreeMap<Integer, String> formattedLevels = new java.util.TreeMap<>();
	public static String defaultFormattedLevel;

	public static void setup(boolean isByReload) {
		BLP blp = BLP.getPlugin(BLP.class);
		if (!blp.getDataFolder().exists())
			blp.getDataFolder().mkdir(); 
		if (!(file = new File(blp.getDataFolder(), "config.yml")).exists())
			blp.saveResource("config.yml", false); 
		reload(true, isByReload);
	}

	private static void reload(boolean update, boolean isByReload) {
		cfg = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
		if(update && FileUtils.updateFile(file, "config.yml", isByReload))
			reload(false, isByReload);

		formattedLevels.clear();
		defaultFormattedLevel = org.bukkit.ChatColor.translateAlternateColorCodes('&', cfg.getString("Formatted_Level.Default", "&8[%blp_level%] "));
		if (cfg.contains("Formatted_Level.Tiers")) {
			for (String key : cfg.getConfigurationSection("Formatted_Level.Tiers").getKeys(false)) {
				try {
					int tier = Integer.parseInt(key);
					String format = org.bukkit.ChatColor.translateAlternateColorCodes('&', cfg.getString("Formatted_Level.Tiers." + key));
					formattedLevels.put(tier, format);
				} catch (NumberFormatException ignored) {}
			}
		}
	}
}
