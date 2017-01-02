package de.simonsator.partyandfriends.displaynameprovider;

import com.github.cheesesoftware.PowerfulPermsAPI.PermissionManager;
import com.github.cheesesoftware.PowerfulPermsAPI.PowerfulPermsPlugin;
import com.google.common.util.concurrent.ListenableFuture;
import de.simonsator.partyandfriends.api.pafplayers.DisplayNameProvider;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerClass;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.concurrent.ExecutionException;

/**
 * @author simonbrungs
 * @version 1.0.0 01.01.17
 */
public class PowerFulPermsDisplayNameProvider extends Plugin implements DisplayNameProvider {
	private final PermissionManager permissionManager = ((PowerfulPermsPlugin) ProxyServer.getInstance().getPluginManager().getPlugin("PowerfulPerms")).getPermissionManager();

	@Override
	public void onEnable() {
		PAFPlayerClass.setDisplayNameProvider(this);
	}

	public String getDisplayName(PAFPlayer pPlayer) {
		ListenableFuture<String> prefixFuture = permissionManager.getPlayerPrefix(pPlayer.getUniqueId());
		try {
			String prefix = prefixFuture.get();
			if (prefix != null)
				return ChatColor.translateAlternateColorCodes('&', prefix) + pPlayer.getName();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return pPlayer.getName();
	}

	public String getDisplayName(OnlinePAFPlayer pPlayer) {
		return pPlayer.getDisplayName();
	}
}
