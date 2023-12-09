package io.github.sidneiimatos.smissoes.storage;

import io.github.sidneiimatos.smissoes.SMissoes;
import org.bukkit.command.ConsoleCommandSender;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQL {
    private SMissoes plugin;
    public Connection con;
    public static ConsoleCommandSender sc;

    public MySQL(SMissoes plugin) {
        setPlugin(plugin);
    }

    public void open() {
        String url = "jdbc:mysql://" + getPlugin().getConfig().getString("MySQL.Host") + ":" + getPlugin().getConfig().getString("MySQL.Porta") + "/" + getPlugin().getConfig().getString("MySQL.Database");
        String user = getPlugin().getConfig().getString("MySQL.Usuario");
        String password = getPlugin().getConfig().getString("MySQL.Senha");
        try {
            this.con = DriverManager.getConnection(url, user, password);
        } catch (SQLException localSQLException) {
        }
    }

    public SMissoes getPlugin() {
        return plugin;
    }

    protected void close() {
        if (this.con != null) {
            try {
                this.con.close();
            } catch (SQLException localSQLException) {
            }
        }
    }

    public void createTable() {
        if (this.con != null) {
            PreparedStatement stm = null;
            try {
                stm = this.con.prepareStatement("CREATE TABLE IF NOT EXISTS `TempoOnline` (`nome` VARCHAR(48) NULL, `Tempo` VARCHAR(48) NULL, `Dias` int(48) NULL);");
                stm.executeUpdate();
            } catch (SQLException localSQLException) {
            }
        }
    }

    public void setPlugin(SMissoes plugin) {
        this.plugin = plugin;
    }
}
