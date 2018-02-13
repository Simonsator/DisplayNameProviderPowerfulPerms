package de.simonsator.partyandfriends.displaynameprovider;

import com.github.gustav9797.PowerfulPermsAPI.PermissionManager;
import com.github.gustav9797.PowerfulPermsAPI.PowerfulPermsPlugin;
import com.google.common.util.concurrent.ListenableFuture;
import de.simonsator.partyandfriends.api.PAFExtension;
import de.simonsator.partyandfriends.api.pafplayers.DisplayNameProvider;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerClass;
import de.simonsator.partyandfriends.utilities.ConfigurationCreator;
import de.simonsator.partyandfriends.utilities.PatterCollection;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author simonbrungs
 * @version 1.0.0 01.01.17
 */
public class PowerFulPermsDisplayNameProvider extends PAFExtension implements DisplayNameProvider {
	private PermissionManager permissionManager = ((PowerfulPermsPlugin) ProxyServer.getInstance().getPluginManager().getPlugin("PowerfulPerms")).getPermissionManager();
	private Matcher prefixMatcher;

	@Override
	public void onEnable() {
		try {
			ConfigurationCreator config = new PFPDNPConfigurationCreator(new File(getDataFolder(), "config.yml"), this);
			prefixMatcher = Pattern.compile("[PREFIX]", Pattern.LITERAL).matcher(config.getCreatedConfiguration().getString("General.DisplayNameFormat"));
			PAFPlayerClass.setDisplayNameProvider(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getDisplayName(PAFPlayer pPlayer) {
		if (permissionManager == null) {
			permissionManager = ((PowerfulPermsPlugin) ProxyServer.getInstance().getPluginManager().getPlugin("PowerfulPerms")).getPermissionManager();
		}
		ListenableFuture<String> prefixFuture = permissionManager.getPlayerPrefix(pPlayer.getUniqueId());
		try {
			String prefix = prefixFuture.get();
			if (prefix != null)
				return PatterCollection.PLAYER_PATTERN.matcher(prefixMatcher.replaceFirst(ChatColor.translateAlternateColorCodes('&', prefix))).replaceFirst(pPlayer.getName());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return pPlayer.getName();
	}

	public String getDisplayName(OnlinePAFPlayer pPlayer) {
		return getDisplayName((PAFPlayer) pPlayer);
	}
}
