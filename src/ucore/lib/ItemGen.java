package ucore.lib;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ItemGen {
	
	public ItemStack itemDiamondChestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
	public ItemStack itemBow = new ItemStack(Material.BOW);
	public ItemStack itemIronSword = new ItemStack(Material.IRON_SWORD);
	public ItemStack itemDiamondSword = new ItemStack(Material.DIAMOND_SWORD);
	
	public ItemGen(){
		itemDiamondChestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL , 1);
		itemBow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE , 1);
		itemIronSword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
		itemDiamondSword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
		itemDiamondSword.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
	}
	
	
	public ItemStack getRandomItem(){
		return getRandomItem(0);
	}
	
	public ItemStack getRandomItem(int luck){
		Random r = new Random();
		int item = r.nextInt(7+luck);
		switch(item){
		case 0:
			return new ItemStack(Material.GRILLED_PORK, r.nextInt(16)+1);
		case 1:
			return new ItemStack(Material.ARROW,r.nextInt(45)+1);
		case 2:
			return new ItemStack(Material.IRON_CHESTPLATE);
		case 3:
			return new ItemStack(Material.IRON_INGOT,r.nextInt(9)+1);
		case 4:
			return new ItemStack(Material.MELON,r.nextInt(18)+1);
		case 5:
			return new ItemStack(Material.GOLD_INGOT,r.nextInt(14)+1);
		case 6:
			return new ItemStack(Material.BAKED_POTATO,r.nextInt(15)+1);
		case 7:
			return itemBow;
		case 8:
			return itemDiamondChestplate;
		case 9:
			return itemIronSword;
		case 10:
			return itemDiamondSword;
		}
		return null;
	}
	
	public ItemStack[] getRandomItemList(int luck, int size){
		ItemStack[] items = new ItemStack[size];
		for(int i = 0;i<size;i++){
			items[i] = getRandomItem(luck);
		}
		return items;
	}

}
