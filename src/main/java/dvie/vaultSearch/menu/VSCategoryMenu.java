package dvie.vaultSearch.menu;

import dev.splityosis.menulib.Menu;
import dev.splityosis.menulib.MenuItem;
import dvie.vaultSearch.VaultSearch;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class VSCategoryMenu extends Menu {
    public VSCategoryMenu(Player player) {
        super(InventoryType.HOPPER);
        setTitle(VaultSearch.config.category_title);
        VaultSearch.config.categories.forEach(category -> {
            addListedItem(new MenuItem(category.displayItem()).executes((inventoryClickEvent, menu) -> {
                new VSSearchMenu(player, category).open((Player) inventoryClickEvent.getWhoClicked());
            }));
        });
    }
}
