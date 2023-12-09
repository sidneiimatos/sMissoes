package io.github.sidneiimatos.smissoes.commands;

import io.github.sidneiimatos.smissoes.SMissoes;
import io.github.sidneiimatos.smissoes.storage.Manager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MissoesCommand implements CommandExecutor {
    private SMissoes plugin;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Apenas usuarios in-game podem utilizar este comando!");
            return false;
        }
        Player p = (Player)sender;
        if (cmd.getName().equals("missoes")) {
            // Abrir o inventario
            int num = getPlugin().getManager().getDias(p.getName());
            p.sendMessage(""+num);
            return true;
        }
        return false;
    }

    public SMissoes getPlugin() {
        return plugin;

    }
}
