package minestrapteam.minestrappolation.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGodstone extends MBlock
{
	public BlockGodstone(Material material, MapColor mapColor)
	{
		super(material, mapColor);
		this.setLightLevel(1F);
	}
	
	@SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		world.scheduleUpdate(pos, this, 5);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (!world.isRemote)
		{
			world.scheduleUpdate(pos, this, 5);
			AxisAlignedBB axisalignedbb = this.getCollisionBoundingBox(world, pos,state).expand(6, 6, 6);
			List<EntityMob> list = world.getEntitiesWithinAABB(EntityMob.class, axisalignedbb);
			for (EntityMob mob : list)
			{
				if (mob.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
				{
					mob.setFire(20);
				}
			}
		}
	}
	
	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon)
	{
		return true;
	}
}
