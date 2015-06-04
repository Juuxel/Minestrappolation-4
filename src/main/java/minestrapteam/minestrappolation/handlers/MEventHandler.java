package minestrapteam.minestrappolation.handlers;

import java.util.Random;

import minestrapteam.minestrappolation.Config;
import minestrapteam.minestrappolation.block.BlockSoul;
import minestrapteam.minestrappolation.lib.MAchievements;
import minestrapteam.minestrappolation.lib.MBlocks;
import minestrapteam.minestrappolation.lib.MItems;
import minestrapteam.minestrappolation.lib.MReference;
import minestrapteam.minestrappolation.util.ChunkHelper;
import minestrapteam.minestrappolation.util.VersionChecker;
import minestrapteam.minestrappolation.world.MBiomeManager;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MEventHandler
{
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayerJoin(EntityJoinWorldEvent event)
	{
		VersionChecker check = new VersionChecker(MReference.VERSION, "https://raw.githubusercontent.com/MinestrapTeam/Minestrappolation-4/master/version.txt", MReference.NAME);
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;
			if (event.world.isRemote == false)
			{
				check.run();
				event.entity.addChatMessage(VersionChecker.uptoDate);
				event.entity.addChatMessage(VersionChecker.motd);
			}
			player.addStat(MAchievements.minestrapp, 1);
		}
	}
	
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event)
	{
		Random rand = new Random();
		if (event.state.getBlock() instanceof BlockNetherWart)
		{
			BlockNetherWart wart = (BlockNetherWart) event.state.getBlock();
			IBlockState ground = event.world.getBlockState(event.pos.add(0, -1, 0));
			
			ItemStack item = new ItemStack(MItems.soul_gem);
			EntityItem eitem = new EntityItem(event.world, event.pos.getX(), event.pos.getY(), event.pos.getZ(), item);
			
			if (rand.nextInt(100) < Config.soulGemDropChance && (Integer) event.state.getValue(BlockNetherWart.AGE) == 3 && ground.getBlock() == MBlocks.soul_ore)
			{
				event.world.spawnEntityInWorld(eitem);
			}
			
		}
		
		if (event.state.getBlock() instanceof BlockSoul)
		{
			ItemStack stack = event.getPlayer().getHeldItem();
			if (stack.canHarvestBlock(event.state.getBlock()))
			{
				stack.damageItem(Config.soulBlockDamage, event.getPlayer());
			}
		}
		
		if (event.state.getBlock() == Blocks.bedrock)
		{
			ItemStack stack = event.getPlayer().getHeldItem();
			if (stack.canHarvestBlock(event.state.getBlock()))
			{
				stack.damageItem(2000, event.getPlayer());
			}
			EntityPlayer player = (EntityPlayer)event.getPlayer();
			player.addStat(MAchievements.bedrock, 1);
		}
	}
	
	@SubscribeEvent
	public void playerUpdate(LivingUpdateEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;
			ItemStack helmet = player.getCurrentArmor(3);
			ItemStack chest = player.getCurrentArmor(2);
			ItemStack pants = player.getCurrentArmor(1);
			ItemStack boots = player.getCurrentArmor(0);
			
			if (helmet != null && chest != null && pants != null && boots != null)
			{
				if (helmet.getItem() == MItems.meurodite_helmet && chest.getItem() == MItems.meurodite_chestplate && pants.getItem() == MItems.meurodite_leggings && boots.getItem() == MItems.meurodite_boots)
				{
					player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 2, 0, true, false));
				}
				else if (helmet.getItem() == MItems.torite_helmet && chest.getItem() == MItems.torite_chestplate && pants.getItem() == MItems.torite_leggings && boots.getItem() == MItems.torite_boots)
				{
					player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 2, 0, true, false));
				}
				else if (helmet.getItem() == MItems.titanium_helmet && chest.getItem() == MItems.titanium_chestplate && pants.getItem() == MItems.titanium_leggings && boots.getItem() == MItems.titanium_boots)
				{
					player.addPotionEffect(new PotionEffect(Potion.resistance.id, 2, 1, true, false));
				}
			}
			if(ChunkHelper.getChunkBiomeForEntity(player).equals(MBiomeManager.frost.biomeName) && Config.frostSpeedEffect && !player.capabilities.isCreativeMode)
			{
				player.addStat(MAchievements.frost, 1);
				if(helmet == null || chest == null || pants == null || boots == null)
				{
					player.motionX *= .75;
					player.motionZ *= .75;
				}
				
			}
		}
		else
		{
			if(ChunkHelper.getChunkBiomeForEntity(event.entity).equals(MBiomeManager.frost) && Config.frostSpeedEffect)
			{	
					event.entity.motionX *= .75;
					event.entity.motionZ *= .75;	
			}
		}
		
		
	}
	
}