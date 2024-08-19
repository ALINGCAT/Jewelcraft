package net.salju.jewelcraft.enchantment;

import net.salju.jewelcraft.item.JewelryItem;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EquipmentSlot;

public class JewelryEnchantment extends Enchantment {
	private final int min;
	
	public JewelryEnchantment(Enchantment.Rarity rare, int xp) {
		super(rare, EnchantmentCategory.WEARABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
		this.min = xp;
	}

	@Override
	protected boolean checkCompatibility(Enchantment ench) {
		return (ench instanceof JewelryEnchantment ? false : super.checkCompatibility(ench));
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return (stack.getItem() instanceof JewelryItem);
	}

	@Override
	public int getMinCost(int i) {
		return min + (i - 1) * 10;
	}

	@Override
	public int getMaxCost(int i) {
		return this.getMinCost(i) + 76;
	}

	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}
}