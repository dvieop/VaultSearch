package dvie.vaultSearch.objects;

import lombok.Builder;
import org.bukkit.inventory.ItemStack;

public record Category(
        ItemStack displayItem,
        int displaySlot,
        Flags flag
) {
    @Builder
    public Category {}
}
