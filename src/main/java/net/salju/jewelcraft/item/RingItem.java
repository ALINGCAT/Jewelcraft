package net.salju.jewelcraft.item;

import top.theillusivec4.curios.api.SlotContext;
import net.salju.jewelcraft.init.JewelryEnchantments;
import net.salju.jewelcraft.init.JewelryConfig;
import net.salju.jewelcraft.events.JewelcraftManager;
import net.minecraftforge.common.ForgeMod;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerPlayer;
import java.util.UUID;
import com.google.common.collect.Multimap;
import com.google.common.collect.HashMultimap;

public class RingItem extends JewelryItem {
	public RingItem(int value) {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON), value);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slot, UUID id, ItemStack stack) {
		LivingEntity target = slot.entity();
		Multimap<Attribute, AttributeModifier> stats = HashMultimap.create();
		if (isCopper(stack)) {
			stats.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.fromString("7bbafa58-fcf0-4878-a5c5-85c7904b8fa9"), "S-KB-R", JewelryConfig.COPPER.get(), AttributeModifier.Operation.ADDITION));
		}
		if (isIron(stack)) {
			stats.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(UUID.fromString("dff4f294-b96e-11ed-afa1-0242ac120002"), "S-Armor-R", JewelryConfig.IRON.get(), AttributeModifier.Operation.ADDITION));
		}
		if (JewelcraftManager.hasEnchantment(JewelryEnchantments.HELIODOR.get(), stack)) {
			stats.put(Attributes.LUCK, new AttributeModifier(UUID.fromString("36696d7a-ba0b-11ed-afa1-0242ac120002"), "S-Luck-R", JewelryConfig.LUCK.get(), AttributeModifier.Operation.ADDITION));
		}
		if (JewelcraftManager.hasEnchantment(JewelryEnchantments.INFUSED.get(), stack)) {
			stats.put(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("3dd24214-ba09-11ed-afa1-0242ac120002"), "S-AtkSpeed-R", ((float) JewelryConfig.ATKSPD.get() / 100), AttributeModifier.Operation.MULTIPLY_TOTAL));
		}
		if (JewelcraftManager.hasEnchantment(JewelryEnchantments.ALEXANDRITE.get(), stack)) {
			stats.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(UUID.fromString("159c3056-b96f-11ed-afa1-0242ac120002"), "S-Reach-R", JewelryConfig.REACH.get(), AttributeModifier.Operation.ADDITION));
			stats.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("0a2df15a-b96f-11ed-afa1-0242ac120002"), "S-Speed-R", ((float) JewelryConfig.MOVSPD.get() / 100), AttributeModifier.Operation.MULTIPLY_TOTAL));
		}
		return stats;
	}

	@Override
	public void curioTick(SlotContext slot, ItemStack stack) {
		if (slot.entity() instanceof ServerPlayer player) {
			if (JewelcraftManager.hasEnchantment(JewelryEnchantments.BLOODY.get(), stack)) {
				player.getAttributes().addTransientAttributeModifiers(createBloody(player));
			}
			if (JewelcraftManager.hasEnchantment(JewelryEnchantments.ZILLYAURA.get(), stack)) {
				for (LivingEntity aura : player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(7.0D))) {
					if (aura instanceof Animal || aura instanceof Villager) {
						if (!aura.hasEffect(MobEffects.REGENERATION) && aura.getHealth() < aura.getMaxHealth()) {
							aura.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 1));
						}
					}
				}
			}
		}
	}

	@Override
	public void onUnequip(SlotContext slot, ItemStack newbie, ItemStack stack) {
		LivingEntity target = slot.entity();
		if (target instanceof ServerPlayer player) {
			player.getAttributes().removeAttributeModifiers(createBloody(player));
		}
	}

	@Override
	public int getFortuneLevel(SlotContext slot, LootContext loot, ItemStack stack) {
		return (JewelcraftManager.hasEnchantment(JewelryEnchantments.HELIODOR.get(), stack) ? super.getFortuneLevel(slot, loot, stack) + JewelryConfig.FORT.get() : super.getFortuneLevel(slot, loot, stack));
	}

	private Multimap<Attribute, AttributeModifier> createBloody(Player target) {
		Multimap<Attribute, AttributeModifier> stats = HashMultimap.create();
		stats.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("2a041a44-fa84-499b-937e-e20474f6c623"), "S-DPS-R", getDamage(target), AttributeModifier.Operation.MULTIPLY_TOTAL));
		return stats;
	}

	private double getDamage(LivingEntity target) {
		if (target.getHealth() <= (target.getMaxHealth() * 0.05)) {
			return 1.0;
		} else if (target.getHealth() <= (target.getMaxHealth() * 0.1)) {
			return 0.8;
		} else if (target.getHealth() <= (target.getMaxHealth() * 0.2)) {
			return 0.6;
		} else if (target.getHealth() <= (target.getMaxHealth() * 0.4)) {
			return 0.4;
		} else if (target.getHealth() <= (target.getMaxHealth() * 0.6)) {
			return 0.2;
		} else if (target.getHealth() <= (target.getMaxHealth() * 0.8)) {
			return 0.1;
		} else {
			return 0.0;
		}
	}
}
