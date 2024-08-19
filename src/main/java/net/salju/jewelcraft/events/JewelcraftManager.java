package net.salju.jewelcraft.events;

import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;
import javax.annotation.Nullable;

public class JewelcraftManager {
	public static LootPoolSingletonContainer.Builder<?> getUnique(Item gem, @Nullable String name, Enchantment one, @Nullable Enchantment two) {
		LootPoolSingletonContainer.Builder<?> stack = LootItem.lootTableItem(gem);
		stack.setWeight(7);
		if (name != null) {
			stack.apply(SetNameFunction.setName(Component.translatable(name)));
		}
		stack.apply(enchantItem().withEnchantment(one, UniformGenerator.between(1.0F, 1.0F)));
		if (two != null) {
			stack.apply(enchantItem().withEnchantment(two, UniformGenerator.between(1.0F, 1.0F)));
		}
		return stack;
	}

	public static SetEnchantmentsFunction.Builder enchantItem() {
		return new SetEnchantmentsFunction.Builder();
	}

	public static boolean hasEnchantment(Enchantment ench, ItemStack stack) {
		return EnchantmentHelper.getItemEnchantmentLevel(ench, stack) != 0;
	}

	public static LootPool setUnique(String name, float low, float max, @Nullable LootPoolEntryContainer.Builder<?>... targets) {
		LootPool.Builder newbie = LootPool.lootPool();
		newbie.name(name);
		newbie.setRolls(UniformGenerator.between(low, max));
		for (LootPoolEntryContainer.Builder<?> target : targets) {
			if (target != null) {
				newbie.add(target);
			}
		}
		LootPool done = newbie.build();
		return done;
	}
}