package me.kandangalwi.kahub.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MaterialRegistry {
    private static final Map<String, Material> materialMap = new HashMap<>();

    static {
        registerMaterials();
    }

    public static void registerMaterials() {
        for (Material material : Material.values()) {
            materialMap.put(material.name().toLowerCase(), material);
        }
    }

    public static Material getMaterialByName(String name) {
        return materialMap.get(name.toLowerCase());
    }

    public static ItemStack getItemStackByName(String name) {
        Material material = getMaterialByName(name);
        if (material != null) {
            return new ItemStack(material);
        }
        return null;
    }
}

