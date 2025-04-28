package dvie.vaultSearch.menu;

import dev.splityosis.menulib.Menu;
import dev.splityosis.menulib.MenuItem;
import dvie.vaultSearch.VaultSearch;
import dvie.vaultSearch.objects.Category;
import dvie.vaultSearch.objects.VaultItem;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class VSSearchMenu extends Menu {

    public VSSearchMenu(Player player, Category category) {
        super(VaultSearch.config.search_size);
        setTitle(VaultSearch.config.search_title.replace("%category%", category.toString()));
        setNextPageButton(VaultSearch.config.nextPageSlot, VaultSearch.config.nextPage);
        setPreviousPageButton(VaultSearch.config.previousPageSlot, VaultSearch.config.previousPage);

        for (int slot : VaultSearch.config.borderSlots) {
            setItem(slot, new MenuItem(VaultSearch.config.borderItem));
        }

        List<VaultItem> matchingItems = VaultSearch.findItems(player, category);

        for (VaultItem vaultItem : matchingItems) {
            ItemStack displayItem = vaultItem.itemStack();
            int vaultNumber = vaultItem.vaultNumber();

            MenuItem menuItem = new MenuItem(displayItem).executes((inventoryClickEvent, menu) -> {
               openVault(player, vaultNumber);
            });

            addListedItem(menuItem);
            setItem(VaultSearch.config.closeSlot, new MenuItem(VaultSearch.config.close).executes((inventoryClickEvent, menu) -> {
                player.closeInventory();
            }));
        }
    }

    private void openVault(Player player, int vaultNumber) {
        VaultSearch.getInstance().getServer().dispatchCommand(player, "pv " + vaultNumber);
    }
}
