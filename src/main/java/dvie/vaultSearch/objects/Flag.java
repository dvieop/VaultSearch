package dvie.vaultSearch.objects;

import org.bukkit.Material;

import java.util.function.Predicate;

@FunctionalInterface
public interface Flag {
    boolean test(Material material);

    static Flag of(Flag flag) {
        return flag;
    }

    static Flag of(Predicate<Material> predicate) {
        return predicate::test;
    }
}
