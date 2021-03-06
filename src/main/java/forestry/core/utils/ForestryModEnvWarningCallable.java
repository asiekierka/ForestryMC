/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Various Contributors including, but not limited to:
 * SirSengir (original work), CovertJaguar, Player, Binnie, MysteriousAges
 ******************************************************************************/
package forestry.core.utils;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ICrashCallable;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;

import forestry.core.config.Defaults;
import forestry.plugins.PluginManager;

public class ForestryModEnvWarningCallable implements ICrashCallable {

	private final List<String> modIDs;
	private final List<String> disabledModules;

	public ForestryModEnvWarningCallable() {
		this.modIDs = new ArrayList<String>();

		if (FMLCommonHandler.instance().getSide() == Side.CLIENT && FMLClientHandler.instance().hasOptifine()) {
			modIDs.add("Optifine");
		}

		if (Loader.isModLoaded("gregtech_addon")) {
			modIDs.add("GregTech");
		}

		try {
			@SuppressWarnings("unused")
			Class<?> c = Class.forName("org.bukkit.Bukkit");
			modIDs.add("Bukkit, Cauldron, or other Bukkit replacement");
		} catch (Throwable ignored) {
		} // No need to do anything.


		this.disabledModules = new ArrayList<String>();
		for (PluginManager.Module module : PluginManager.configDisabledModules) {
			disabledModules.add(module.configName());
		}

		if (modIDs.size() > 0 || disabledModules.size() > 0) {
			FMLCommonHandler.instance().registerCrashCallable(this);
		}
	}

	@Override
	public String call() throws Exception {
		StringBuilder message = new StringBuilder();
		if (modIDs.size() > 0) {
			message.append("Warning: You have mods that change the behavior of Minecraft, ForgeModLoader, and/or Minecraft Forge to your client: \r\n");
			message.append(modIDs.get(0));
			for (int i = 1; i < modIDs.size(); ++i) {
				message.append(", ").append(modIDs.get(i));
			}
			message.append("\r\nThese may have caused this error, and may not be supported. Try reproducing the crash WITHOUT these mods, and report it then.");
		}

		if (disabledModules.size() > 0) {
			if (message.length() > 0) {
				message.append("\r\n");
			}
			message.append("Info: The following plugins have been disabled in the config: ");
			message.append(disabledModules.get(0));
			for (int i = 1; i < disabledModules.size(); ++i) {
				message.append(", ").append(disabledModules.get(i));
			}
		}

		return message.toString();
	}

	@Override
	public String getLabel() {
		return Defaults.MOD + " ";
	}

}
