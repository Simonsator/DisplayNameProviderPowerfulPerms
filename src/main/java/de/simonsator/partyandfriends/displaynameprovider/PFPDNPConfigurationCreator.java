package de.simonsator.partyandfriends.displaynameprovider;

import de.simonsator.partyandfriends.api.PAFExtension;
import de.simonsator.partyandfriends.utilities.ConfigurationCreator;

import java.io.File;
import java.io.IOException;

/**
 * @author Simonsator
 * @version 03.01.2017
 */
public class PFPDNPConfigurationCreator extends ConfigurationCreator {
	public PFPDNPConfigurationCreator(File pFile, PAFExtension pPlugin) throws IOException {
		super(pFile, pPlugin);
		readFile();
		loadDefaults();
		saveFile();
		process(configuration);
	}

	private void loadDefaults() {
		set("General.DisplayNameFormat", "&e[PREFIX] &e[PLAYER]");
	}
}
