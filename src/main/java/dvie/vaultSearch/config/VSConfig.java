package dvie.vaultSearch.config;

import dev.splityosis.sysengine.configlib.configuration.Configuration;
import dev.splityosis.sysengine.xseries.XMaterial;
import dvie.vaultSearch.objects.Category;
import dvie.vaultSearch.objects.Flags;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import static dvie.vaultSearch.util.Util.createItemStack;

public class VSConfig implements Configuration {

    @Section("categories")
    @Field public List<Category> categories = List.of(
            Category.builder()
                    .displayItem(createItemStack(XMaterial.STONE, 1, "&bBlocks", List.of("&7All types of blocks")))
                    .displaySlot(0)
                    .flag(Flags.IS_BLOCK)
                    .build(),
            Category.builder()
                    .displayItem(createItemStack(XMaterial.IRON_PICKAXE, 1, "&bTools", List.of("&7All tools, including pickaxes, axes, and swords")))
                    .displaySlot(1)
                    .flag(Flags.IS_TOOL)
                    .build(),
            Category.builder()
                    .displayItem(createItemStack(XMaterial.SPAWNER, 1, "&bSpawners", List.of("&7All mob spawners")))
                    .displaySlot(2)
                    .flag(Flags.IS_SPAWNER)
                    .build(),
            Category.builder()
                    .displayItem(createItemStack(XMaterial.IRON_HELMET, 1, "&bGear", List.of("&7Armor gear: helmets, chestplates, leggings, boots")))
                    .displaySlot(3)
                    .flag(Flags.IS_GEAR)
                    .build(),
            Category.builder()
                    .displayItem(createItemStack(XMaterial.ENCHANTED_BOOK, 1, "&bEnchantments", List.of("&7Enchanted books and regular books")))
                    .displaySlot(4)
                    .flag(Flags.IS_ENCHANTMENT)
                    .build()
    );

    @Section("general")
    @Field public List<String> foundItemLore = List.of("&bVault&7: &f%vault_number%", "", "&7Left click to open vault");

    @Section("menu-category")
    @Field public String category_title = "&bVault Search";

    @Section("menu-search")
    @Field public String search_title = "&b%category% items";
    @Field public int search_size = 54;
    @Field public ItemStack nextPage = createItemStack(XMaterial.ARROW, 1, "&bNext Page");
    @Field public int nextPageSlot = 53;
    @Field public ItemStack previousPage = createItemStack(XMaterial.ARROW, 1, "&bPrevious Page");
    @Field public int previousPageSlot = 45;
    @Field public ItemStack borderItem = createItemStack(XMaterial.GRAY_STAINED_GLASS_PANE, 1, "&7");
    @Field public List<Integer> borderSlots = List.of(46, 47, 48, 50, 51, 52);
    @Field public ItemStack close = createItemStack(XMaterial.BARRIER, 1, "&bClose");
    @Field public int closeSlot = 49;
}
