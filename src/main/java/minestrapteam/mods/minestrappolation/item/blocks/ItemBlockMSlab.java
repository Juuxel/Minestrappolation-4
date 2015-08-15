package minestrapteam.mods.minestrappolation.item.blocks;

import minestrapteam.mods.minestrappolation.block.BlockMDoubleSlab;
import minestrapteam.mods.minestrappolation.block.BlockMSlab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockMSlab extends ItemBlock
{
    private final BlockSlab slab;
    private final BlockSlab double_slab;

    public ItemBlockMSlab(Block p_i45782_1_, BlockMSlab slab, BlockMDoubleSlab double_slab)
    {
        super(p_i45782_1_);
        this.slab = slab;
        this.double_slab = double_slab;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (stack.stackSize == 0)
        {
            return false;
        }
        else if (!playerIn.canPlayerEdit(pos.offset(side), side, stack))
        {
            return false;
        }
        else
        {
            Object object = this.slab.getVariant(stack);
            IBlockState iblockstate = worldIn.getBlockState(pos);

            if (iblockstate.getBlock() == this.slab)
            {
                BlockSlab.EnumBlockHalf enumblockhalf = (BlockSlab.EnumBlockHalf)iblockstate.getValue(BlockSlab.HALF);

                if ((side == EnumFacing.UP && enumblockhalf == BlockSlab.EnumBlockHalf.BOTTOM || side == EnumFacing.DOWN && enumblockhalf == BlockSlab.EnumBlockHalf.TOP))
                {
                    IBlockState iblockstate1 = this.double_slab.getDefaultState();

                    if (worldIn.checkNoEntityCollision(this.double_slab.getCollisionBoundingBox(worldIn, pos, iblockstate1)) && worldIn.setBlockState(pos, iblockstate1, 3))
                    {
                        worldIn.playSoundEffect((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), this.double_slab.stepSound.getPlaceSound(), (this.double_slab.stepSound.getVolume() + 1.0F) / 2.0F, this.double_slab.stepSound.getFrequency() * 0.8F);
                        --stack.stackSize;
                    }

                    return true;
                }
            }

            return this.func_180615_a(stack, worldIn, pos.offset(side), object) ? true : super.onItemUse(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos p_179222_2_, EnumFacing p_179222_3_, EntityPlayer p_179222_4_, ItemStack p_179222_5_)
    {
        BlockPos blockpos1 = p_179222_2_;
        Object object = this.slab.getVariant(p_179222_5_);
        IBlockState iblockstate = worldIn.getBlockState(p_179222_2_);

        if (iblockstate.getBlock() == this.slab)
        {
            boolean flag = iblockstate.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP;

            if ((p_179222_3_ == EnumFacing.UP && !flag || p_179222_3_ == EnumFacing.DOWN && flag))
            {
                return true;
            }
        }

        p_179222_2_ = p_179222_2_.offset(p_179222_3_);
        IBlockState iblockstate1 = worldIn.getBlockState(p_179222_2_);
        return iblockstate1.getBlock() == this.slab ? true : super.canPlaceBlockOnSide(worldIn, blockpos1, p_179222_3_, p_179222_4_, p_179222_5_);
    }

    private boolean func_180615_a(ItemStack p_180615_1_, World worldIn, BlockPos p_180615_3_, Object p_180615_4_)
    {
        IBlockState iblockstate = worldIn.getBlockState(p_180615_3_);

        if (iblockstate.getBlock() == this.slab)
        {
                IBlockState iblockstate1 = this.double_slab.getDefaultState();

                if (worldIn.checkNoEntityCollision(this.double_slab.getCollisionBoundingBox(worldIn, p_180615_3_, iblockstate1)) && worldIn.setBlockState(p_180615_3_, iblockstate1, 3))
                {
                    worldIn.playSoundEffect((double)((float)p_180615_3_.getX() + 0.5F), (double)((float)p_180615_3_.getY() + 0.5F), (double)((float)p_180615_3_.getZ() + 0.5F), this.double_slab.stepSound.getPlaceSound(), (this.double_slab.stepSound.getVolume() + 1.0F) / 2.0F, this.double_slab.stepSound.getFrequency() * 0.8F);
                    --p_180615_1_.stackSize;
                }

                return true;
        }

        return false;
    }
}