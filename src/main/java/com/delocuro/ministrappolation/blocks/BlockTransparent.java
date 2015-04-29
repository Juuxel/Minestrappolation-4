package com.delocuro.ministrappolation.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.delocuro.ministrappolation.init.MBlocks;

public class BlockTransparent extends MBlock{
	
	private final MapColor mapColor;
	private boolean ignoreSimilarity;
	
	public BlockTransparent(Material materialIn, MapColor mapColorIn, boolean ignoreSimilarityIn) {
		super(materialIn, mapColorIn);
		this.mapColor = mapColorIn;
		this.ignoreSimilarity = ignoreSimilarityIn;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.CUTOUT;
    }
	
	 @SideOnly(Side.CLIENT)
	    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	    {
	        IBlockState iblockstate = worldIn.getBlockState(pos);
	        Block block = iblockstate.getBlock();

	        if (this == MBlocks.glass_bricks || this == MBlocks.glass_refined || this == MBlocks.glass_tiles || this == MBlocks.glass_window)
	        {
	            if (worldIn.getBlockState(pos.offset(side.getOpposite())) != iblockstate)
	            {
	                return true;
	            }

	            if (block == this)
	            {
	                return false;
	            }
	        }

	        return !this.ignoreSimilarity && block == this ? false : super.shouldSideBeRendered(worldIn, pos, side);
	    }

    public boolean isFullCube()
    {
        return false;
    }
    
    public int quantityDropped(Random random)
    {
        if(this == MBlocks.glass_window)
        {
        	return 1;
        }
        else
        	return 0;
    }

    protected boolean canSilkHarvest()
    {
        return true;
    }
}
