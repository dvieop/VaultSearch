package dvie.vaultSearch.objects;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public enum Flags {

    IS_BLOCK(Material::isBlock),
    IS_TOOL(material -> {
        String name = material.name();
        return name.endsWith("_HOE") ||
                name.endsWith("_AXE") ||
                name.endsWith("_SHOVEL") ||
                name.endsWith("_PICKAXE") ||
                name.endsWith("_SWORD");
    }),
    IS_SPAWNER(material -> material == Material.SPAWNER),
    IS_GEAR(material -> {
        String name = material.name();
        return name.endsWith("_HELMET") ||
                name.endsWith("_CHESTPLATE") ||
                name.endsWith("_LEGGINGS") ||
                name.endsWith("_BOOTS");
    }),
    IS_ENCHANTMENT(material -> material == Material.ENCHANTED_BOOK || material == Material.BOOK);

    private final Flag flag;

    Flags(Flag flag) {
        this.flag = flag;
    }

    public boolean test(Material material) {
        return flag.test(material);
    }
}
