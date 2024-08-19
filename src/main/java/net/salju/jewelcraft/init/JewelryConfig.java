package net.salju.jewelcraft.init;

import net.minecraftforge.common.ForgeConfigSpec;

public class JewelryConfig {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec CONFIG;

	public static final ForgeConfigSpec.BooleanValue GOLDEN;
	public static final ForgeConfigSpec.DoubleValue IRON;
	public static final ForgeConfigSpec.DoubleValue COPPER;
	public static final ForgeConfigSpec.BooleanValue UNIQUE;

	public static final ForgeConfigSpec.DoubleValue LUCK;
	public static final ForgeConfigSpec.DoubleValue REACH;
	public static final ForgeConfigSpec.DoubleValue STEP;
	public static final ForgeConfigSpec.IntValue ATKSPD;
	public static final ForgeConfigSpec.IntValue MOVSPD;
	public static final ForgeConfigSpec.IntValue SWMSPD;
	public static final ForgeConfigSpec.IntValue FORT;
	public static final ForgeConfigSpec.IntValue LOOT;

	public static final ForgeConfigSpec.DoubleValue MAGN;
	public static final ForgeConfigSpec.IntValue FOOD;
	public static final ForgeConfigSpec.IntValue CRIT;
	public static final ForgeConfigSpec.IntValue CRITRNG;
	public static final ForgeConfigSpec.IntValue CRITCHAN;
	public static final ForgeConfigSpec.BooleanValue RNGCRIT;
	public static final ForgeConfigSpec.BooleanValue VANCRIT;
	
	static {
		BUILDER.push("Jewelry");
		GOLDEN = BUILDER.comment("Should golden jewelry work on Piglins?").define("Golden Blesses", true);
		IRON = BUILDER.comment("Armor Rating of Iron Jewelry").defineInRange("Iron Protects", 1.0, 0.0, Double.MAX_VALUE);
		COPPER = BUILDER.comment("Knockback Resistance of Copper Jewelry").defineInRange("Copper Resists", 0.1, 0.0, 1.0);
		UNIQUE = BUILDER.comment("Should unique jewelry pieces be added to loot tables?").define("Unique Jewelry", true);
		BUILDER.pop();
		BUILDER.push("Enchantment Bonuses");
		SWMSPD = BUILDER.comment("Swimming Speed bonus from the Aquamarine Enchantment as a percentage").defineInRange("Swimmer", 60, 0, Integer.MAX_VALUE);
		MOVSPD = BUILDER.comment("Movement Speed bonus from the Alexandrite Enchantment as a percentage").defineInRange("Alex's Speed", 15, 0, Integer.MAX_VALUE);
		REACH = BUILDER.comment("Reach bonus from the Alexandrite Enchantment").defineInRange("Alex's Reach", 1.5, 0.0, Double.MAX_VALUE);
		STEP = BUILDER.comment("Step-Up bonus from the Alexandrite Enchantment").defineInRange("Alex's Step", 0.5, 0.0, Double.MAX_VALUE);
		ATKSPD = BUILDER.comment("Attack Speed bonus from the Infused Enchantment as a percentage").defineInRange("Infusion", 25, 0, Integer.MAX_VALUE);
		FORT = BUILDER.comment("Fortune bonus from the Heliodor Enchantment").defineInRange("Heliodor's Fortune", 1, 0, Integer.MAX_VALUE);
		LOOT = BUILDER.comment("Looting bonus from the Heliodor Enchantment").defineInRange("Heliodor's Looting", 1, 0, Integer.MAX_VALUE);
		LUCK = BUILDER.comment("Luck bonus from the Heliodor Enchantment").defineInRange("Heliodor's Luck", 1.0, 0.0, Double.MAX_VALUE);
		BUILDER.pop();
		BUILDER.push("Enchantment Abilities");
		MAGN = BUILDER.comment("Magnetic's range for teleporting items to the player").defineInRange("Magnetic", 16.0, 0.0, Double.MAX_VALUE);
		FOOD = BUILDER.comment("Satisfication's minimum hunger amount for the player").defineInRange("Never Go Hungry", 12, 0, 16);
		CRIT = BUILDER.comment("Empowered's Vanilla Critical Damage Bonus as a percentage").defineInRange("Critical", 25, 0, Integer.MAX_VALUE);
		CRITRNG = BUILDER.comment("Empowered's Random Critical Damage Bonus as a percentage").defineInRange("Random Critical", 25, 0, Integer.MAX_VALUE);
		CRITCHAN = BUILDER.comment("Empowered's Random Critical Chance as a percentage").defineInRange("Random Chance", 15, 0, 100);
		RNGCRIT = BUILDER.comment("Should Empowered allow random critical hits?").define("Fair & Balanced", true);
		VANCRIT = BUILDER.comment("Should Empowered allow vanilla critical hits?").define("Bonk!", true);
		BUILDER.pop();
		CONFIG = BUILDER.build();
	}
}