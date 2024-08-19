package net.salju.jewelcraft.events;

import net.salju.jewelcraft.init.JewelryItems;
import net.salju.jewelcraft.init.JewelryEnchantments;
import net.salju.jewelcraft.init.JewelryConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class JewelcraftLoot {
	@SubscribeEvent
	public void onLoot(LootTableLoadEvent event) {
		if (JewelryConfig.UNIQUE.get()) {
			if (event.getName().equals(BuiltInLootTables.SHIPWRECK_TREASURE)) {
				LootTable table = event.getTable();
				LootPool unique = JewelcraftManager.setUnique("captain_loot", -2.0F, 1.0F,
						JewelcraftManager.getUnique(JewelryItems.IRON_AMULET_LAPIS.get(), "item.jewelcraft.captain_amulet", JewelryEnchantments.AQUAMARINE.get(), JewelryEnchantments.MAGNETIC.get()),
						JewelcraftManager.getUnique(JewelryItems.GOLDEN_RING_REDSTONE.get(), "item.jewelcraft.captain_ring", JewelryEnchantments.CRITICAL.get(), JewelryEnchantments.HELIODOR.get()));
				table.addPool(unique);
				event.setTable(table);
			} else if (event.getName().equals(BuiltInLootTables.JUNGLE_TEMPLE)) {
				LootTable table = event.getTable();
				LootPool unique = JewelcraftManager.setUnique("jungle_loot", -2.0F, 1.0F,
						JewelcraftManager.getUnique(JewelryItems.COPPER_RING_ROSE.get(), "item.jewelcraft.sacrifice_ring", JewelryEnchantments.BLOODY.get(), JewelryEnchantments.INFUSED.get()));
				table.addPool(unique);
				event.setTable(table);
			} else if (event.getName().equals(BuiltInLootTables.DESERT_PYRAMID)) {
				LootTable table = event.getTable();
				LootPool unique = JewelcraftManager.setUnique("desert_loot", -2.0F, 1.0F,
						JewelcraftManager.getUnique(JewelryItems.GOLDEN_AMULET_REDSTONE.get(), "item.jewelcraft.desert_amulet", JewelryEnchantments.SATISFICATION.get(), JewelryEnchantments.MAGNETIC.get()));
				table.addPool(unique);
				event.setTable(table);
			} else if (event.getName().equals(BuiltInLootTables.FISHING_TREASURE)) {
				LootTable table = event.getTable();
				LootPool unique = JewelcraftManager.setUnique("fish_loot", -12.0F, 1.0F,
						JewelcraftManager.getUnique(JewelryItems.GOLDEN_RING_ONYX.get(), "item.jewelcraft.fisher_ring", JewelryEnchantments.CRITICAL.get(), JewelryEnchantments.INFUSED.get()));
				table.addPool(unique);
				event.setTable(table);
			} else if (event.getName().equals(BuiltInLootTables.SPAWN_BONUS_CHEST)) {
				LootTable table = event.getTable();
				LootPool unique = JewelcraftManager.setUnique("spawnbonus_loot", 1.0F, 1.0F, JewelcraftManager.getUnique(JewelryItems.IRON_AMULET_EMERALD.get(), "item.jewelcraft.bonus_amulet", JewelryEnchantments.SATISFICATION.get(), null));
				table.addPool(unique);
				event.setTable(table);
			}
		}
	}
}