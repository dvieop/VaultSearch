package dvie.vaultSearch.config.components;

import dev.splityosis.sysengine.configlib.bukkit.ConfigurationSection;
import dev.splityosis.sysengine.configlib.configuration.ConfigMapper;
import dev.splityosis.sysengine.configlib.manager.ConfigManager;
import dvie.vaultSearch.objects.Category;
import dvie.vaultSearch.objects.Flags;
import org.bukkit.inventory.ItemStack;

public class CategoryMapper implements ConfigMapper<Category> {

    @Field public ItemStack displayItem;
    @Field public int displaySlot;
    @Field public Flags flag;

    @Override
    public Category compile(ConfigManager configManager, ConfigurationSection configurationSection, String s) {
        return new Category(displayItem, displaySlot, flag);
    }

    @Override
    public void decompile(ConfigManager configManager, Category category, ConfigurationSection configurationSection, String s) {
        displayItem = category.displayItem();
        displaySlot = category.displaySlot();
        flag = category.flag();
    }
}
