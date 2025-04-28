package dvie.vaultSearch.objects;

import org.bukkit.inventory.ItemStack;

public record VaultItem(
        int vaultNumber,
        ItemStack itemStack
) {
}
