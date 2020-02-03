package com.github.lunatrius.schematica.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GuiHelper {
    private static final RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

    public static void drawItemStackWithSlot(final TextureManager textureManager, final ItemStack itemStack, final int x, final int y) {
        drawItemStackSlot(textureManager, x, y);

        if (itemStack != null && itemStack.getItem() != null) {
            drawItemStack(itemStack, x + 2, y + 2);
        }
    }

    public static void drawItemStackSlot(final TextureManager textureManager, final int x, final int y) {
        textureManager.bindTexture(Gui.STAT_ICONS);

        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuffer();
        final double uScale = 1.0 / 128.0;
        final double vScale = 1.0 / 128.0;

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        drawTexturedRectangle(buffer, x + 1, y + 1, x + 1 + 18, y + 1 + 18, 0, uScale * 0, vScale * 0, uScale * 18, vScale * 18);
        tessellator.draw();
    }

    public static void drawItemStack(final ItemStack itemStack, final int x, final int y) {
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        renderItem.renderItemIntoGUI(itemStack, x, y);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
    }

    public static void drawTexturedRectangle(final BufferBuilder buffer, final double x0, final double y0, final double x1, final double y1, final double z, final double u0, final double v0, final double u1, final double v1) {
        buffer.pos(x0, y0, z).tex(u0, v0).endVertex();
        buffer.pos(x0, y1, z).tex(u0, v1).endVertex();
        buffer.pos(x1, y1, z).tex(u1, v1).endVertex();
        buffer.pos(x1, y0, z).tex(u1, v0).endVertex();
    }

    public static void drawTexturedRectangle(final BufferBuilder buffer, final double x0, final double y0, final double x1, final double y1, final double z, final double textureWidth, final double textureHeight, final int argb) {
        final double u0 = x0 / textureWidth;
        final double v0 = y0 / textureHeight;
        final double u1 = x1 / textureWidth;
        final double v1 = y1 / textureHeight;

        drawTexturedRectangle(buffer, x0, y0, x1, y1, z, u0, v0, u1, v1, argb);
    }

    public static void drawTexturedRectangle(final BufferBuilder buffer, final double x0, final double y0, final double x1, final double y1, final double z, final double u0, final double v0, final double u1, final double v1, final int argb) {
        final int a = (argb >> 24) & 0xFF;
        final int r = (argb >> 16) & 0xFF;
        final int g = (argb >> 8) & 0xFF;
        final int b = argb & 0xFF;

        drawTexturedRectangle(buffer, x0, y0, x1, y1, z, u0, v0, u1, v1, r, g, b, a);
    }

    public static void drawTexturedRectangle(final BufferBuilder buffer, final double x0, final double y0, final double x1, final double y1, final double z, final double u0, final double v0, final double u1, final double v1, final int r, final int g, final int b, final int a) {
        buffer.pos(x0, y0, z).tex(u0, v0).color(r, g, b, a).endVertex();
        buffer.pos(x0, y1, z).tex(u0, v1).color(r, g, b, a).endVertex();
        buffer.pos(x1, y1, z).tex(u1, v1).color(r, g, b, a).endVertex();
        buffer.pos(x1, y0, z).tex(u1, v0).color(r, g, b, a).endVertex();
    }
}