package net.salju.jewelcraft.events;

import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.CuriosApi;
import net.salju.jewelcraft.item.RingItem;
import net.salju.jewelcraft.item.AmuletItem;
import net.salju.jewelcraft.init.JewelryEnchantments;
import net.salju.jewelcraft.init.JewelryConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.core.BlockPos;
import javax.annotation.Nullable;
import java.util.List;
import java.util.ArrayList;

@Mod.EventBusSubscriber
public class JewelcraftEvents {
	@SubscribeEvent
	public static void onDeath(LivingDeathEvent event) {
		if (event != null && event.getEntity() != null && event.getSource() != null) {
			if (event.getEntity() instanceof Player player && !event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD)) {
				if (player.getInventory().contains(new ItemStack(Items.TOTEM_OF_UNDYING))) {
					ItemStack totem = new ItemStack(Items.TOTEM_OF_UNDYING);
					ItemStack amulet = getAmulet(player);
					List<ItemStack> rings = getRings(player);
					if (amulet != null && JewelcraftManager.hasEnchantment(JewelryEnchantments.TOTEM.get(), amulet)) {
						player.getInventory().clearOrCountMatchingItems(p -> totem.getItem() == p.getItem(), 1, player.inventoryMenu.getCraftSlots());
						player.setHealth(1.0F);
						player.removeAllEffects();
						player.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 1));
						player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 800, 0));
						player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
						player.level().broadcastEntityEvent(player, (byte) 35);
						event.setCanceled(true);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEffect(MobEffectEvent.Applicable event) {
		if (event != null && event.getEntity() != null && event.getEffectInstance() != null) {
			MobEffectInstance potion = event.getEffectInstance();
			if (event.getEntity() instanceof Player player) {
				ItemStack amulet = getAmulet(player);
				List<ItemStack> rings = getRings(player);
				if (amulet != null) {
					if (JewelcraftManager.hasEnchantment(JewelryEnchantments.RAINBOW.get(), amulet)) {
						if (potion.getEffect() == MobEffects.POISON) {
							player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, potion.getDuration(), potion.getAmplifier()));
							event.setResult(Result.DENY);
						} else if (potion.getEffect() == MobEffects.MOVEMENT_SLOWDOWN) {
							player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, potion.getDuration(), potion.getAmplifier()));
							event.setResult(Result.DENY);
						} else if (potion.getEffect() == MobEffects.BLINDNESS) {
							player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, potion.getDuration(), potion.getAmplifier()));
							event.setResult(Result.DENY);
						}
					}
					if (JewelcraftManager.hasEnchantment(JewelryEnchantments.SATISFICATION.get(), amulet)) {
						if (potion.getEffect() == MobEffects.HUNGER) {
							event.setResult(Result.DENY);
						}
					}
				}
				if (rings.size() > 0) {
					for (ItemStack ring : rings) {
						if (JewelcraftManager.hasEnchantment(JewelryEnchantments.RAINBOW.get(), ring)) {
							if (potion.getEffect() == MobEffects.WEAKNESS) {
								player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, potion.getDuration(), potion.getAmplifier()));
								event.setResult(Result.DENY);
							} else if (potion.getEffect() == MobEffects.DIG_SLOWDOWN) {
								player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, potion.getDuration(), potion.getAmplifier()));
								event.setResult(Result.DENY);
							}
							break;
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onCrops(BlockEvent.CropGrowEvent.Pre event) {
		if (event != null && event.getState() != null) {
			BlockState state = event.getState();
			BlockPos pos = event.getPos();
			LevelAccessor world = event.getLevel();
			for (Player player : world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(pos.getX(), pos.getY(), pos.getZ()), 8, 2, 8))) {
				ItemStack amulet = getAmulet(player);
				List<ItemStack> rings = getRings(player);
				if (amulet != null) {
					if (JewelcraftManager.hasEnchantment(JewelryEnchantments.SODALITE.get(), amulet)) {
						if (state.getBlock() instanceof CactusBlock && world.isEmptyBlock(pos)) {
							world.setBlock(pos, state, 3);
							world.levelEvent(1505, pos, 0);
						} else if (state.getBlock() instanceof BonemealableBlock blok && blok.isValidBonemealTarget(world, pos, state, true)) {
							BoneMealItem.applyBonemeal(new ItemStack(Items.BONE_MEAL), player.level(), pos, player);
							world.levelEvent(1505, pos, 0);
						}
					}
				}
				if (rings.size() > 0) {
					for (ItemStack ring : rings) {
						if (JewelcraftManager.hasEnchantment(JewelryEnchantments.SODALITE.get(), ring)) {
							if (state.getBlock() instanceof SugarCaneBlock && world.isEmptyBlock(pos.above())) {
								world.setBlock(pos.above(), state, 3);
								world.levelEvent(1505, pos, 0);
							} else if (state.getBlock() instanceof BonemealableBlock blok && blok.isValidBonemealTarget(world, pos, state, true)) {
								BoneMealItem.applyBonemeal(new ItemStack(Items.BONE_MEAL), player.level(), pos, player);
								world.levelEvent(1505, pos, 0);
							}
							break;
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onTramply(BlockEvent.FarmlandTrampleEvent event) {
		if (event != null && event.getEntity() != null) {
			if (event.getEntity() instanceof Player player) {
				ItemStack amulet = getAmulet(player);
				List<ItemStack> rings = getRings(player);
				if (amulet != null && JewelcraftManager.hasEnchantment(JewelryEnchantments.SODALITE.get(), amulet)) {
					event.setCanceled(true);
				} else if (rings.size() > 0) {
					for (ItemStack ring : rings) {
						if (JewelcraftManager.hasEnchantment(JewelryEnchantments.SODALITE.get(), ring)) {
							event.setCanceled(true);
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onCritical(CriticalHitEvent event) {
		if (event != null && event.getEntity() != null) {
			Player player = event.getEntity();
			List<ItemStack> rings = getRings(player);
			if (rings.size() > 0) {
				for (ItemStack ring : rings) {
					if (JewelcraftManager.hasEnchantment(JewelryEnchantments.CRITICAL.get(), ring)) {
						if (Math.random() <= ((double) JewelryConfig.CRITCHAN.get() / 100) && !(event.isVanillaCritical()) && JewelryConfig.RNGCRIT.get()) {
							event.setResult(Result.ALLOW);
							event.setDamageModifier(event.getDamageModifier() + ((float) JewelryConfig.CRITRNG.get() / 100));
						} else if (event.isVanillaCritical()) {
							if (!JewelryConfig.VANCRIT.get()) {
								event.setResult(Result.DENY);
							} else {
								event.setDamageModifier(event.getDamageModifier() + ((float) JewelryConfig.CRIT.get() / 100));
							}
						}
						break;
					}
				}
			}
		}
	}

	@Nullable
	public static ItemStack getAmulet(final LivingEntity target) {
		List<ItemStack> stack = new ArrayList<ItemStack>();
		CuriosApi.getCuriosHelper().getCuriosHandler(target).ifPresent(handler -> {
			ICurioStacksHandler stacks = handler.getCurios().get("charm");
			if (stacks != null) {
				IDynamicStackHandler dyn = stacks.getStacks();
				if (dyn != null) {
					for (int i = 0; i < stacks.getSlots(); i++) {
						if (dyn.getStackInSlot(i) != null && dyn.getStackInSlot(i).getItem() instanceof AmuletItem) {
							stack.add(dyn.getStackInSlot(i));
							break;
						}
					}
				}
			}
		});
		return stack.isEmpty() ? null : stack.get(0);
	}

	@Nullable
	public static List<ItemStack> getRings(final LivingEntity target) {
		List<ItemStack> stack = new ArrayList<ItemStack>();
		CuriosApi.getCuriosHelper().getCuriosHandler(target).ifPresent(handler -> {
			ICurioStacksHandler stacks = handler.getCurios().get("ring");
			if (stacks != null) {
				IDynamicStackHandler dyn = stacks.getStacks();
				if (dyn != null) {
					for (int i = 0; i < stacks.getSlots(); i++) {
						if (dyn.getStackInSlot(i) != null && dyn.getStackInSlot(i).getItem() instanceof RingItem) {
							stack.add(dyn.getStackInSlot(i));
						}
					}
				}
			}
		});
		return stack;
	}
}