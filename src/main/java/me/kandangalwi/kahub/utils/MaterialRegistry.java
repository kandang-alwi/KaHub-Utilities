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
            materialMap.put(material.name(), material);
        }
    }

    public static Material getMaterialByName(String name) {
        return materialMap.get(name);
    }

    public static ItemStack getItemStackById(String id) {
        if (id == null) {
            return null;
        }

        String[] parts = id.split(":");
        if (parts.length > 1) {
            String materialName = parts[0];
            short dataValue = Short.parseShort(parts[1]);

            Material material = getMaterialByName(materialName);
            if (material != null) {
                return new ItemStack(material, 1, dataValue);
            }
        }

        return null;
    }
}


