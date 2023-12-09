package io.github.sidneiimatos.smissoes;

import io.github.sidneiimatos.smissoes.commands.MissoesCommand;
import io.github.sidneiimatos.smissoes.storage.Manager;
import io.github.sidneiimatos.smissoes.storage.MySQL;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class SMissoes extends JavaPlugin {
    private static SMissoes i;
    private MySQL mySQL;
    private Manager manager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommands();

    }

    public void getCommands() {
        saveDefaultConfig();
        setInstance(this);
        setMySQL(new MySQL(this));
        setManager(new Manager());
        getMySQL().open();
        getMySQL().createTable();
        getCommand("missoes").setExecutor((CommandExecutor)new MissoesCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SMissoes get() {
        return i;
    }

    public static void setInstance(SMissoes i) {
        i = i;
    }

    public MySQL getMySQL() {
        return this.mySQL;
    }

    public void setMySQL(MySQL mySQL) {
        this.mySQL = mySQL;
    }


    public Manager getManager() {
        return this.manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
