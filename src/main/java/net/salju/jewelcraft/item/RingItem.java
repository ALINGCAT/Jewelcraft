package net.salju.jewelcraft.item;

import top.theillusivec4.curios.api.SlotContext;

import net.salju.jewelcraft.init.JewelryTabs;
import net.salju.jewelcraft.init.JewelryEnchantments;

import net.minecraftforge.common.ForgeMod;

import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

import com.google.common.collect.Multimap;
import com.google.common.collect.HashMultimap;

public class RingItem extends JewelryItem {
	public RingItem() {
		super(new Item.Properties().tab(JewelryTabs.JEWELCRAFT_TAB).stacksTo(1).rarity(Rarity.COMMON));
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slot, UUID id, ItemStack stack) {
		LivingEntity target = slot.entity();
		Multimap<Attribute, AttributeModifier> stats = HashMultimap.create();
		if (stack.is(ItemTags.create(new ResourceLocation("jewelcraft:is_copper"))))
			stats.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.fromString("7bbafa58-fcf0-4878-a5c5-85c7904b8fa9"), "S-KB-R", 0.1, AttributeModifier.Operation.ADDITION));
		if (stack.is(ItemTags.create(new ResourceLocation("jewelcraft:is_iron"))))
			stats.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(UUID.fromString("dff4f294-b96e-11ed-afa1-0242ac120002"), "S-Armor-R", 1.0, AttributeModifier.Operation.ADDITION));
		if (EnchantmentHelper.getItemEnchantmentLevel(JewelryEnchantments.HELIODOR.get(), stack) != 0)
			stats.put(Attributes.LUCK, new AttributeModifier(UUID.fromString("36696d7a-ba0b-11ed-afa1-0242ac120002"), "S-Luck-R", 1.0, AttributeModifier.Operation.ADDITION));
		if (EnchantmentHelper.getItemEnchantmentLevel(JewelryEnchantments.INFUSED.get(), stack) != 0)
			stats.put(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("3dd24214-ba09-11ed-afa1-0242ac120002"), "S-AtkSpeed-R", 0.25F, AttributeModifier.Operation.MULTIPLY_TOTAL));
		if (EnchantmentHelper.getItemEnchantmentLevel(JewelryEnchantments.ALEXANDRITE.get(), stack) != 0) {
			stats.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(UUID.fromString("159c3056-b96f-11ed-afa1-0242ac120002"), "S-Reach-R", 1.5, AttributeModifier.Operation.ADDITION));
			stats.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("0a2df15a-b96f-11ed-afa1-0242ac120002"), "S-Speed-R", 0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL));
		}
		return stats;
	}

	@Override
	public void curioTick(SlotContext slot, ItemStack stack) {
		LivingEntity target = slot.entity();
		if (target instanceof ServerPlayer player) {
			if (EnchantmentHelper.getItemEnchantmentLevel(JewelryEnchantments.BLOODY.get(), stack) != 0) {
				player.getAttributes().addTransientAttributeModifiers(this.createBloody(player));
			}
		}
	}

	@Override
	public void onUnequip(SlotContext slot, ItemStack newbie, ItemStack stack) {
		LivingEntity target = slot.entity();
		if (target instanceof ServerPlayer player) {
			player.getAttributes().removeAttributeModifiers(this.createBloody(player));
		}
	}

	@Override
	public int getFortuneLevel(SlotContext slot, LootContext loot, ItemStack stack) {
		if (EnchantmentHelper.getItemEnchantmentLevel(JewelryEnchantments.HELIODOR.get(), stack) != 0)
			return super.getFortuneLevel(slot, loot, stack) + 1;
		return super.getFortuneLevel(slot, loot, stack);
	}

	public static double getDamage(LivingEntity target) {
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

	private Multimap<Attribute, AttributeModifier> createBloody(Player target) {
		Multimap<Attribute, AttributeModifier> stats = HashMultimap.create();
		stats.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("2a041a44-fa84-499b-937e-e20474f6c623"), "S-DPS-R", this.getDamage(target), AttributeModifier.Operation.MULTIPLY_TOTAL));
		return stats;
	}
}