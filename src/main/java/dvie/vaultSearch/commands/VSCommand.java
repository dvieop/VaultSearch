package dvie.vaultSearch.commands;

import dev.splityosis.sysengine.commandlib.command.Command;
import dvie.vaultSearch.VaultSearch;
import dvie.vaultSearch.menu.VSCategoryMenu;
import dvie.vaultSearch.util.Util;

public class VSCommand extends Command {
    public VSCommand() {
        super("vaultsearch", "pvsearch", "vs");
        permission("vaultsearch.use");
        description("VaultSearch command");
        playerExecutes((player, commandContext) -> {
           new VSCategoryMenu(player).open(player);
        });

        addSubCommand(new Command("reload")
                .permission("vaultsearch.admin")
                .executes((commandSender, commandContext) -> {
                    VaultSearch.getInstance().initialiseConfigs();
                    Util.sendMessage(commandSender, "&aVaultSearch reloaded!");
                }));
    }
}
