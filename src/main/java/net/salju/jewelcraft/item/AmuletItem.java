package net.salju.jewelcraft.item;

import top.theillusivec4.curios.api.SlotContext;
import net.salju.jewelcraft.init.JewelryEnchantments;
import net.salju.jewelcraft.init.JewelryConfig;
import net.salju.jewelcraft.events.JewelcraftManager;
import net.minecraftforge.common.ForgeMod;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.FluidTags;
import java.util.UUID;
import java.util.List;
import com.google.common.collect.Multimap;
import com.google.common.collect.HashMultimap;

public class AmuletItem extends JewelryItem {
	public AmuletItem(int value) {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON), value);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slot, UUID id, ItemStack stack) {
		LivingEntity target = slot.entity();
		Multimap<Attribute, AttributeModifier> stats = HashMultimap.create();
		if (isCopper(stack)) {
			stats.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.fromString("929c9214-b9f9-11ed-afa1-0242ac120002"), "S-KB-A", JewelryConfig.COPPER.get(), AttributeModifier.Operation.ADDITION));
		}
		if (isIron(stack)) {
			stats.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(UUID.fromString("5a1035e8-b7f8-11ed-afa1-0242ac120002"), "S-Armor-A", JewelryConfig.IRON.get(), AttributeModifier.Operation.ADDITION));
		}
		if (JewelcraftManager.hasEnchantment(JewelryEnchantments.HELIODOR.get(), stack)) {
			stats.put(Attributes.LUCK, new AttributeModifier(UUID.fromString("1f7c25ec-b9f9-11ed-afa1-0242ac120002"), "S-Luck-A", JewelryConfig.LUCK.get(), AttributeModifier.Operation.ADDITION));
		}
		if (JewelcraftManager.hasEnchantment(JewelryEnchantments.AQUAMARINE.get(), stack)) {
			stats.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(UUID.fromString("f7be8916-b85c-11ed-afa1-0242ac120002"), "S-Swim-A", ((float) JewelryConfig.SWMSPD.get() / 100), AttributeModifier.Operation.MULTIPLY_TOTAL));
		}
		if (JewelcraftManager.hasEnchantment(JewelryEnchantments.ALEXANDRITE.get(), stack)) {
			stats.put(ForgeMod.STEP_HEIGHT_ADDITION.get(), new AttributeModifier(UUID.fromString("1fbdd7e8-ba09-11ed-afa1-0242ac120002"), "S-StepUp-A", JewelryConfig.STEP.get(), AttributeModifier.Operation.ADDITION));
			stats.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(UUID.fromString("19260e62-b85d-11ed-afa1-0242ac120002"), "S-Reach-A", JewelryConfig.REACH.get(), AttributeModifier.Operation.ADDITION));
		}
		return stats;
	}

	@Override
	public void curioTick(SlotContext slot, ItemStack stack) {
		double x = slot.entity().getX();
		double y = slot.entity().getY() + 0.75;
		double z = slot.entity().getZ();
		if (slot.entity() instanceof Player player) {
			if (JewelcraftManager.hasEnchantment(JewelryEnchantments.MAGNETIC.get(), stack)) {
				List<ItemEntity> items = player.level().getEntitiesOfClass(ItemEntity.class, player.getBoundingBox().inflate(JewelryConfig.MAGN.get()));
				for (ItemEntity item : items) {
					if (item.isAlive() && player.isShiftKeyDown() && check(player, item.getItem())) {
						item.setNoPickUpDelay();
						item.setNoGravity(true);
						Vec3 v = player.getEyePosition().subtract(item.position());
						if (item.level().isClientSide) {
							item.yOld = item.getY();
						}
						item.setDeltaMovement(item.getDeltaMovement().scale(0.95D).add(v.normalize().scale(0.05D)));
					} else {
						item.setNoGravity(false);
					}
				}
			}
			if (JewelcraftManager.hasEnchantment(JewelryEnchantments.SATISFICATION.get(), stack)) {
				if (player.getFoodData().getFoodLevel() < JewelryConfig.FOOD.get()) {
					player.getFoodData().setFoodLevel(JewelryConfig.FOOD.get());
				}
			}
			if (JewelcraftManager.hasEnchantment(JewelryEnchantments.AQUAMARINE.get(), stack)) {
				if (player.isEyeInFluid(FluidTags.WATER)) {
					if (player.getAirSupply() < 300) {
						player.setAirSupply(player.getAirSupply() + 1);
					} else {
						player.setAirSupply(300);
					}
				}
			}
		}
	}

	@Override
	public int getLootingLevel(SlotContext slot, DamageSource source, LivingEntity target, int loot, ItemStack stack) {
		return (JewelcraftManager.hasEnchantment(JewelryEnchantments.HELIODOR.get(), stack) ? super.getLootingLevel(slot, source, target, loot, stack) + JewelryConfig.LOOT.get() : super.getLootingLevel(slot, source, target, loot, stack));
	}

	private boolean check(Player player, ItemStack stack) {
		int i = player.getInventory().getSlotWithRemainingSpace(stack);
		if (player.isCreative()) {
			return true;
		} else if (player.isSpectator()) {
			return false;
		}
		return (i < 0 ? (player.getInventory().getFreeSlot() >= 0) : true);
	}
}