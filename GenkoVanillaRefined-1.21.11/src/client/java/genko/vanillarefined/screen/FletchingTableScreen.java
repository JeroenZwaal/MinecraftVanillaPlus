package genko.vanillarefined.screen;

import genko.vanillarefined.screenhandler.FletchingTableScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import com.mojang.blaze3d.systems.RenderSystem;

public class FletchingTableScreen extends HandledScreen<FletchingTableScreenHandler> {

    private static final Identifier TEXTURE = Identifier.of("vanillarefined", "textures/gui/fletching_table.png");

    public FletchingTableScreen(FletchingTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 176;
        this.backgroundHeight = 166;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Draw the vanilla HUD first
        this.renderBackground(context, mouseX, mouseY, delta); // optional dark background behind GUI
        super.render(context, mouseX, mouseY, delta); // draws slots, tooltips, etc.

        // Draw tooltips on top
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        //send message in console to check if method is called
        System.out.println("Drawing Fletching Table Background");
    }
}
