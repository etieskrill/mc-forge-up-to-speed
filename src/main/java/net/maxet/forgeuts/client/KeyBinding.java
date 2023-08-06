package net.maxet.forgeuts.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

import static net.maxet.forgeuts.common.Leyline.MODID;

public class KeyBinding {
    
    public static final String KEY_CATEGORY_LEYLINE = "key.category." + MODID;
    public static final String KEY_OPEN_GUI = "key." + MODID + ".openClanGui";
    
    public static final KeyMapping OPEN_GUI = new KeyMapping(
            KEY_OPEN_GUI,
            KeyConflictContext.UNIVERSAL,
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_C,
            KEY_CATEGORY_LEYLINE);
    
}
