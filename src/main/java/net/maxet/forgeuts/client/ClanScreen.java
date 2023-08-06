package net.maxet.forgeuts.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.maxet.forgeuts.common.Leyline;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ClanScreen extends Screen {
    
    private static final Minecraft MC = Minecraft.getInstance();
    
    private static ClanScreen instance;
    
    private static final ResourceLocation BACKGROUND_TEXTURE_FILE =
            new ResourceLocation(Leyline.MODID, "textures/gui/clan_gui_background_smol.png");
    
    private ClanScreen(Component title) {
        super(title);
    }
    
    public static ClanScreen get() {
        if (instance == null)
            instance = new ClanScreen(Component.literal("Clan GUI"));
        return instance;
    }
    
    @Override
    protected void init() {
        addRenderableWidget(
                Button.builder(Component.literal("Yeeees"), button1 -> {
                    if (MC.player == null) return;
                    MC.player.sendSystemMessage(Component.literal("Succ ma dicc"));
                })
                        .tooltip(Tooltip.create(Component.literal("Gonna cry?")))
                        .build());
        addRenderableWidget(
                Button.builder(Component.literal("Yeeees2"), button1 -> {
                    if (MC.player == null) return;
                    MC.player.sendSystemMessage(Component.literal("Succ ma dicc 2: Electric Boogaloo"));
                })
                        .pos(0, 150)
                        .tooltip(Tooltip.create(Component.literal("Maybe pee a little?")))
                        .build());
    }
    
    @Override
    public void tick() {
    }
    
    @Override
    public void removed() {
    }
    
    @Override
    public void added() {
    }
    
    @Override
    public boolean keyPressed(int key, int scancode, int modifiers) {
        if (KeyBinding.OPEN_GUI.isActiveAndMatches(InputConstants.getKey(key, scancode)) || key == InputConstants.KEY_ESCAPE) {
            MC.setScreen(null);
            return true;
        }
        return super.keyPressed(key, scancode, modifiers);
    }
    
    @Override
    public void render(@NotNull PoseStack batch, int x, int y, float partialTick) {
        //renderRect(batch, 0, 0, 100, 100, 0x80000000);
        renderBackground(batch);
        super.render(batch, x, y, partialTick);
    }
    
    @Override
    public void renderBackground(@NotNull PoseStack batch) {
        setShaderColor(1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE_FILE);
        renderTexture(batch, 0, 0, 0, 0, 128, 128, 128, 128);
    }
    
    private static void setShaderColor(float red, float green, float blue) {
        setShaderColor(red, green, blue, 1f);
    }
    
    private static void setShaderColor(float red, float green, float blue, float alpha) {
        RenderSystem.setShaderColor(alpha, green, blue, red);
    }
    
    /**
     * Renders a texture using the set shader colour and shader texture. The coordinate origin is in the top left
     * corner for all frames of reference. Imagine two rectangular frames, a render frame and a texture frame. All
     * units are in pixels relative to their surroundings, which are screen pixels for the render frame and texture
     * pixels for the texture frame.
     * <br\>
     * The render frame is offset using {@code x}, {@code y} and scaled using
     * {@code width}, {@code height}. Analogously, the texture frame is moved using {@code u}, {@code v} and scaled
     * using {@code texWidth}, {@code texHeight}.
     * <br\>
     * The top left corner of the texture is then stitched to the top left corner of the render frame. Any overflow
     * is clipped, and any underflow filled with an infinitely repeated non-mirrored tiling of the texture area.
     */
    private void renderTexture(PoseStack batch, int x, int y, float u, float v,
                               int width, int height, int texWidth, int texHeight) {
        blit(batch, x, y, u, v, width, height, texWidth, texHeight);
    }
    
    private void renderRect(PoseStack batch, int x, int y, int width, int height, int colour) {
        GuiComponent.fill(batch, x, y, x + width, y + height, colour);
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }
    
}
