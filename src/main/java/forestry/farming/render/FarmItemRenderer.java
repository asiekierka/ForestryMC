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
package forestry.farming.render;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import forestry.core.utils.StackUtils;
import forestry.farming.gadgets.BlockFarm;
import forestry.farming.gadgets.EnumFarmBlock;

public class FarmItemRenderer implements IItemRenderer {

	private void renderFarmBlock(RenderBlocks render, ItemStack item, float translateX, float translateY, float translateZ) {
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		Tessellator tessellator = Tessellator.instance;
		BlockFarm block = (BlockFarm) StackUtils.getBlock(item);

		block.setBlockBoundsForItemRender();
		render.setRenderBoundsFromBlock(block);

		EnumFarmBlock type = EnumFarmBlock.getFromCompound(item.getTagCompound());

		GL11.glTranslatef(translateX, translateY, translateZ);

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1F, 0.0F);
		render.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(type, 0, item.getItemDamage()));
		render.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getOverlayTextureForBlock(0, item.getItemDamage()));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		render.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(type, 1, item.getItemDamage()));
		render.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getOverlayTextureForBlock(1, item.getItemDamage()));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1F);
		render.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(type, 2, item.getItemDamage()));
		render.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getOverlayTextureForBlock(2, item.getItemDamage()));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		render.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(type, 3, item.getItemDamage()));
		render.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getOverlayTextureForBlock(3, item.getItemDamage()));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(-1F, 0.0F, 0.0F);
		render.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(type, 4, item.getItemDamage()));
		render.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getOverlayTextureForBlock(4, item.getItemDamage()));
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		render.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(type, 5, item.getItemDamage()));
		render.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getOverlayTextureForBlock(5, item.getItemDamage()));
		tessellator.draw();

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);

		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

		GL11.glPopAttrib();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch (type) {
			case ENTITY:
			case EQUIPPED_FIRST_PERSON:
			case EQUIPPED:
			case INVENTORY:
				return true;
			default:
				return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
			case ENTITY:
				renderFarmBlock((RenderBlocks) data[0], item, -0.5f, -0.5f, -0.5f);
				break;
			case EQUIPPED:
			case EQUIPPED_FIRST_PERSON:
				renderFarmBlock((RenderBlocks) data[0], item, 0f, 0f, 0f);
				break;
			case INVENTORY:
				renderFarmBlock((RenderBlocks) data[0], item, -0.5f, -0.5f, -0.5f);
				break;
			default:
		}
	}

}
