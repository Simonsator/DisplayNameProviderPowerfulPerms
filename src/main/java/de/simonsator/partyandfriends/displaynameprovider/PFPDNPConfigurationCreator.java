package de.simonsator.partyandfriends.displaynameprovider;

import de.simonsator.partyandfriends.utilities.ConfigurationCreator;

import java.io.File;
import java.io.IOException;

/**
 * @author Simonsator
 * @version 03.01.2017
 */
public class PFPDNPConfigurationCreator extends ConfigurationCreator {
	public PFPDNPConfigurationCreator(File pFile) throws IOException {
		super(pFile);
		readFile();
		loadDefaults();
		saveFile();
		process(configuration);
	}

	private void loadDefaults() {
		set("General.DisplayNameFormat", "&e[PREFIX] &e[PLAYER]");
	}

	public void reloadConfiguration() throws IOException {
		configuration = (new PFPDNPConfigurationCreator(FILE)).getCreatedConfiguration();
	}
}
