package net.salju.jewelcraft.init;

import net.salju.jewelcraft.enchantment.*;
import net.salju.jewelcraft.JewelcraftMod;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.world.item.enchantment.Enchantment;

public class JewelryEnchantments {
	public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, JewelcraftMod.MODID);
	public static final RegistryObject<Enchantment> MAGNETIC = REGISTRY.register("magnetic", () -> new AmuletEnchantment(Enchantment.Rarity.UNCOMMON, 12));
	public static final RegistryObject<Enchantment> SATISFICATION = REGISTRY.register("satisfication", () -> new AmuletEnchantment(Enchantment.Rarity.UNCOMMON, 11));
	public static final RegistryObject<Enchantment> AQUAMARINE = REGISTRY.register("aquamarine", () -> new AmuletEnchantment(Enchantment.Rarity.RARE, 14));
	public static final RegistryObject<Enchantment> TOTEM = REGISTRY.register("totem", () -> new AmuletEnchantment(Enchantment.Rarity.RARE, 8));
	
	public static final RegistryObject<Enchantment> BLOODY = REGISTRY.register("bloody", () -> new RingEnchantment(Enchantment.Rarity.UNCOMMON, 11));
	public static final RegistryObject<Enchantment> INFUSED = REGISTRY.register("infused", () -> new RingEnchantment(Enchantment.Rarity.RARE, 14));
	public static final RegistryObject<Enchantment> CRITICAL = REGISTRY.register("critical", () -> new RingEnchantment(Enchantment.Rarity.RARE, 14));
	public static final RegistryObject<Enchantment> ZILLYAURA = REGISTRY.register("healing_aura", () -> new RingEnchantment(Enchantment.Rarity.RARE, 16));
	
	public static final RegistryObject<Enchantment> SODALITE = REGISTRY.register("sodalite", () -> new JewelryEnchantment(Enchantment.Rarity.COMMON, 5));
	public static final RegistryObject<Enchantment> HELIODOR = REGISTRY.register("heliodor", () -> new JewelryEnchantment(Enchantment.Rarity.UNCOMMON, 7));
	public static final RegistryObject<Enchantment> ALEXANDRITE = REGISTRY.register("alexandrite", () -> new JewelryEnchantment(Enchantment.Rarity.COMMON, 6));
	public static final RegistryObject<Enchantment> RAINBOW = REGISTRY.register("rainbow", () -> new JewelryEnchantment(Enchantment.Rarity.COMMON, 6));
}