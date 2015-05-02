package minestrapteam.minestrappolation.block;

import java.util.List;

import minestrapteam.minestrappolation.Minestrappolation;
import minestrapteam.minestrappolation.lib.MBlocks;
import minestrapteam.minestrappolation.lib.MReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBiomeBricks extends Block
{
	private static final PropertyEnum	VARIANT	= PropertyEnum.create("type", MStoneType.class);
	
	public BlockBiomeBricks()
	{
		super(Material.rock);
		this.setCreativeTab(Minestrappolation.tabMinistrappolation);
		this.setUnlocalizedName("biome_bricks");
	}
	
	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { VARIANT });
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return ((MStoneType) state.getValue(VARIANT)).getMetadata();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		MStoneType[] aenumtype = MStoneType.values();
		int i = aenumtype.length;
		
		for (int j = 0; j < i; ++j)
		{
			MStoneType enumtype = aenumtype[j];
			list.add(new ItemStack(itemIn, 1, enumtype.getMetadata()));
		}
		
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VARIANT, MStoneType.byMetadata(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((MStoneType) state.getValue(VARIANT)).getMetadata();
	}
	
	public static void addRecipes()
	{
		MStoneType[] aenumtype = MStoneType.values();
		int i = aenumtype.length;
		
		for (int j = 0; j < i; ++j)
		{
			MStoneType enumtype = aenumtype[j];
			GameRegistry.addRecipe(new ItemStack(MBlocks.biome_bricks, 1, enumtype.getMetadata()), new Object[] { "II", "II", 'I', new ItemStack(MBlocks.biome_stones, 1, enumtype.getMetadata()) });
		}
	}
	
	public static void preinventoryRender()
	{
		Item itemBlockBrickVariants = GameRegistry.findItem(MReference.MODID, "biome_bricks");
		
		ModelBakery.addVariantName(itemBlockBrickVariants, "ministrapp:deepstone_bricks");
		ModelBakery.addVariantName(itemBlockBrickVariants, "ministrapp:coldstone_bricks");
		ModelBakery.addVariantName(itemBlockBrickVariants, "ministrapp:icestone_bricks");
		ModelBakery.addVariantName(itemBlockBrickVariants, "ministrapp:glacierrock_bricks");
		ModelBakery.addVariantName(itemBlockBrickVariants, "ministrapp:deep_coldstone_bricks");
		ModelBakery.addVariantName(itemBlockBrickVariants, "ministrapp:red_rock_bricks");
		ModelBakery.addVariantName(itemBlockBrickVariants, "ministrapp:deep_redrock_bricks");
		ModelBakery.addVariantName(itemBlockBrickVariants, "ministrapp:oceanstone_bricks");
		ModelBakery.addVariantName(itemBlockBrickVariants, "ministrapp:pressurized_oceanstone_bricks");
	}
	
	public static void inventoryRender()
	{
		Item itemBlockVariants = GameRegistry.findItem(MReference.MODID, "biome_bricks");
		MStoneType[] aenumtype = MStoneType.values();
		int i = aenumtype.length;
		
		for (int j = 0; j < i; ++j)
		{
			MStoneType enumtype = aenumtype[j];
			ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(MReference.MODID + ":"+enumtype.getUnlocalizedName()+"_bricks", "inventory");
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlockVariants, enumtype.getMetadata(), itemModelResourceLocation);
		}
	}
	
}