package dvie.vaultSearch;

import com.drtshock.playervaults.PlayerVaults;
import com.drtshock.playervaults.vaultmanagement.VaultManager;
import dev.splityosis.menulib.MenuLib;
import dev.splityosis.sysengine.commandlib.CommandLib;
import dev.splityosis.sysengine.commandlib.manager.CommandManager;
import dev.splityosis.sysengine.configlib.ConfigLib;
import dev.splityosis.sysengine.configlib.manager.ConfigManager;
import dev.splityosis.sysengine.configlib.manager.ConfigOptions;
import dvie.vaultSearch.commands.VSCommand;
import dvie.vaultSearch.config.VSConfig;
import dvie.vaultSearch.config.components.CategoryMapper;
import dvie.vaultSearch.objects.Category;
import dvie.vaultSearch.objects.VaultItem;
import dvie.vaultSearch.util.Util;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class VaultSearch extends JavaPlugin {

    @Getter private static VaultSearch instance;
    @Getter private ConfigManager configManager;
    @Getter private CommandManager commandManager;

    public static VSConfig config;

    @Override
    public void onEnable() {
        instance = this;
        MenuLib.setup(this);
        configManager = ConfigLib.createConfigManager(this).setConfigOptions(new ConfigOptions()
                .setSectionSpacing(1));
        commandManager = CommandLib.createCommandManager(this);

        commandManager.registerCommands(new VSCommand());
        initialiseConfigs();
    }

    @Override
    public void onDisable() {

    }

    @SneakyThrows
    public void initialiseConfigs() {
        configManager.getMapperRegistry().registerMappers(
                new CategoryMapper()
        );
        config = new VSConfig();
        configManager.registerConfig(config, new File(getDataFolder(), "config.yml"));
    }

    public List<VaultItem> getAllItems(Player player) {
        VaultManager vaultManager = VaultManager.getInstance();
        int defaultSize = PlayerVaults.getInstance().getDefaultVaultSize();

        Set<Integer> vaultNumbers = vaultManager.getPlayerVaultFile(player.getUniqueId().toString(), true)
                .getKeys(false)
                .stream()
                .map(s -> Integer.parseInt(s.replace("vault", "")))
                .collect(Collectors.toSet());

        List<VaultItem> allItems = new ArrayList<>();
        for (int vaultNumber : vaultNumbers) {
            Inventory vaultInventory = vaultManager.loadOwnVault(player, vaultNumber, defaultSize);
            if (vaultInventory == null) continue;

            for (ItemStack itemStack : vaultInventory.getContents()) {
                if (itemStack == null) continue;
                if (itemStack.getType() == Material.AIR) continue;
                allItems.add(new VaultItem(vaultNumber, itemStack));
            }
        }
        return allItems;
    }

    public static List<VaultItem> findItems(Player player, Category category) {
        return VaultSearch.getInstance().getAllItems(player).stream()
                .filter(vaultItem -> {
                    ItemStack itemStack = vaultItem.itemStack();
                    if (category.flag() != null) {
                        return category.flag().test(itemStack.getType());
                    }
                    return false;
                })
                .map(vaultItem -> {
                    ItemStack itemStack = vaultItem.itemStack().clone();
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    assert itemMeta != null;
                    List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
                    lore.addAll(Util.colorize(config.foundItemLore));
                    lore.replaceAll(s -> s.replace("%vault_number%", String.valueOf(vaultItem.vaultNumber())));
                    itemMeta.setLore(lore);
                    itemStack.setItemMeta(itemMeta);
                    return new VaultItem(vaultItem.vaultNumber(), itemStack);
                })
                .collect(Collectors.toList());
    }

    public static List<VaultItem> searchItems(Player player, String item) {
        return VaultSearch.getInstance().getAllItems(player).stream()
                .filter(vaultItem -> {
                    ItemStack itemStack = vaultItem.itemStack();
                    ItemMeta meta = itemStack.getItemMeta();
                    boolean hasMatchingName = meta != null && meta.hasDisplayName() && meta.getDisplayName().toLowerCase().contains(item.toLowerCase());
                    boolean hasMatchingLore = meta != null && meta.hasLore() &&
                            meta.getLore().stream().anyMatch(lore -> lore.toLowerCase().contains(item.toLowerCase()));
                    return hasMatchingName || hasMatchingLore;
                })
                .map(vaultItem -> {
                    ItemStack itemStack = vaultItem.itemStack().clone();
                    ItemMeta meta = itemStack.getItemMeta();
                    List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
                    lore.addAll(Util.colorize(config.foundItemLore));
                    lore.replaceAll(s -> s.replace("%vault_number%", String.valueOf(vaultItem.vaultNumber())));
                    meta.setLore(lore);
                    itemStack.setItemMeta(meta);
                    return new VaultItem(vaultItem.vaultNumber(), itemStack);
                })
                .collect(Collectors.toList());
    }
}
