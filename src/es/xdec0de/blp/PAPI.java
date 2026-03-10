package es.xdec0de.blp;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import es.xdec0de.blp.utils.files.BLPMessage;
import es.xdec0de.blp.utils.files.MessageUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.robin.battlelevels.api.BattleLevelsAPI;

class PAPI extends PlaceholderExpansion {

	protected PAPI(Plugin papi) {
		MessageUtils.logCol("&e- &bPlaceHolderAPI &7detected (&av" + papi.getDescription().getVersion() + "&7)");
		MessageUtils.log(" ");
		register();
	}

	public boolean persist() {
		return true;
	}

	public boolean canRegister() {
		return true;
	}

	public String getAuthor() {
		return BLP.getPlugin(BLP.class).getDescription().getAuthors().toString();
	}

	public String getIdentifier() {
		return BLP.getPlugin(BLP.class).getDescription().getName();
	}

	public String getVersion() {
		return BLP.getPlugin(BLP.class).getDescription().getVersion();
	}

	public String onPlaceholderRequest(Player player, String identifier) {
		if (player == null)
			return ""; 
		String str = identifier.toLowerCase();
		int pos = -1;
		try {
			pos = Integer.parseInt(str.substring(str.lastIndexOf("#") + 1));
		} catch (NumberFormatException ex) {}
		if(pos == -1) {
			switch (str) {
			case "neededfornext":
				return String.valueOf(BattleLevelsAPI.getNeededForNext(player.getUniqueId()));
			case "globalboosterenabled":
				return BattleLevelsAPI.isGlobalBoosterEnabled() ? BLPMessage.TRUE.asString() : BLPMessage.FALSE.asString();
			case "deaths":
				return String.valueOf(BattleLevelsAPI.getDeaths(player.getUniqueId()));
			case "killstreak":
				return String.valueOf(BattleLevelsAPI.getKillstreak(player.getUniqueId()));
			case "bar":
				return BattleLevelsAPI.getProgressBar(player.getUniqueId());
			case "kdr":
				return String.valueOf(BattleLevelsAPI.getKdr(player.getUniqueId()));
			case "booster":
				return String.valueOf(BattleLevelsAPI.getBoosterInMinutes(player.getUniqueId()));
			case "kills":
				return String.valueOf(BattleLevelsAPI.getKills(player.getUniqueId()));
			case "level":
				return String.valueOf(BattleLevelsAPI.getLevel(player.getUniqueId()));
			case "level_formatted":
				int currentLevel = BattleLevelsAPI.getLevel(player.getUniqueId());
				java.util.Map.Entry<Integer, String> entry = es.xdec0de.blp.utils.files.BLPCfg.formattedLevels.floorEntry(currentLevel);
				String format = (entry != null) ? entry.getValue() : es.xdec0de.blp.utils.files.BLPCfg.defaultFormattedLevel;
				if (format == null) format = String.valueOf(currentLevel);
				return format.replace("%blp_level%", String.valueOf(currentLevel)).replace("%level%", String.valueOf(currentLevel));
			case "score":
				return String.valueOf(BattleLevelsAPI.getScore(player.getUniqueId()));
			case "topstreak":
				return String.valueOf(BattleLevelsAPI.getTopKillstreak(player.getUniqueId()));
			case "boosterenabled":
				return BattleLevelsAPI.hasBooster(player.getUniqueId()) ? BLPMessage.TRUE.asString() : BLPMessage.FALSE.asString();
			case "neededfornextremaining":
				return String.valueOf(BattleLevelsAPI.getNeededForNextRemaining(player.getUniqueId()));
			case "globalbooster":
				return String.valueOf(BattleLevelsAPI.getGlobalBoosterInMinutes());
			}
		} else {
			pos--; // Top 1 would actually be 0 on the top array.
			str = str.substring(0, str.lastIndexOf("#"));
			try {
				switch (str) {
				case "top_levels_name":
					return BattleLevelsAPI.getTopLevels().get(pos).getKey();
				case "top_levels_amount":
					return String.valueOf(BattleLevelsAPI.getTopLevels().get(pos).getValue());
				case "top_deaths_name":
					return BattleLevelsAPI.getTopDeaths().get(pos).getKey();
				case "top_deaths_amount":
					return String.valueOf(BattleLevelsAPI.getTopDeaths().get(pos).getValue());
				case "top_killstreak_name":
					return BattleLevelsAPI.getTopHighestKillstreaks().get(pos).getKey();
				case "top_killstreak_amount":
					return String.valueOf(BattleLevelsAPI.getTopHighestKillstreaks().get(pos).getValue());
				case "top_kills_name":
					return BattleLevelsAPI.getTopKills().get(pos).getKey();
				case "top_kills_amount":
					return String.valueOf(BattleLevelsAPI.getTopKills().get(pos).getValue());
				case "top_score_name":
					return BattleLevelsAPI.getTopScores().get(pos).getKey();
				case "top_score_amount":
					return String.valueOf(BattleLevelsAPI.getTopScores().get(pos).getValue());
				}
			} catch(IndexOutOfBoundsException ex) {
				return BLPMessage.INVALID_TOP.asString();
			}
		}
		return null;
	}
}
