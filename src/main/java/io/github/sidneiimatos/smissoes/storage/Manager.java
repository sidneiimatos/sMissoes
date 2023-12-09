package io.github.sidneiimatos.smissoes.storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.github.sidneiimatos.smissoes.SMissoes;
import org.bukkit.scheduler.BukkitRunnable;


public class Manager {

    public Long getTempo(String player) {
        PreparedStatement stm = null;
        try {
            stm = SMissoes.get().getMySQL().con.prepareStatement("SELECT * FROM `TempoOnline` WHERE `nome` = ?");
            stm.setString(1, player);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return Long.valueOf(rs.getLong("Tempo"));
            }
            return Long.valueOf(0L);
        } catch (SQLException e) {
        }
        return Long.valueOf(0L);
    }

    public void addTempo(String player, long tempo) {
        PreparedStatement stm = null;
        try {
            stm = SMissoes.get().getMySQL().con.prepareStatement("UPDATE `TempoOnline` SET `Tempo` = ? WHERE `nome` = ?");
            stm.setLong(1, tempo + getTempo(player).longValue());
            stm.setString(2, player);
            stm.executeUpdate();
            stm.close();
        } catch (SQLException localSQLException) {
        }
    }

    public int getDias(String player) {
        PreparedStatement stm = null;
        try {
            stm = SMissoes.get().getMySQL().con.prepareStatement("SELECT * FROM `TempoOnline` WHERE `nome` = ?");
            stm.setString(1, player);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Dias");
            }
            return 0;
        } catch (SQLException e) {
        }
        return 0;
    }


    public void addDias(final String player, final int quantidade) {
        new BukkitRunnable() {
            public void run() {
                PreparedStatement stm = null;
                try {
                    stm = SMissoes.get().getMySQL().con.prepareStatement("UPDATE `TempoOnline` SET `Dias` = ? WHERE `nome` = ?");
                    stm.setInt(1, quantidade + Manager.this.getDias(player));
                    stm.setString(2, player);
                    stm.executeUpdate();
                    stm.close();
                } catch (SQLException localSQLException) {
                }
            }
        }.runTaskAsynchronously(SMissoes.get());
    }

    public List<String> getTops() {
        List<String> tops = new ArrayList<String>();
        try {
            if (SMissoes.get().getMySQL().con.isClosed()) {
                SMissoes.get().getMySQL().open();
            }
            PreparedStatement stm = SMissoes.get().getMySQL().con.prepareStatement("SELECT * FROM TempoOnline ORDER BY Tempo DESC");
            ResultSet rs = stm.executeQuery();
            int i = 1;
            while (rs.next()) {
                if (i <= 10) {
                    tops.add("�f " + i + "� " + rs.getString("nome") + ": �7(" + getTempo(rs.getLong("Tempo") + ")"));
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tops;
    }

    public String getTempo(long time) {
        String message = "";
        long diff = time;
        int seconds = (int) (diff / 1000L);
        if (seconds >= 86400) {
            int days = seconds / 86400;
            seconds %= 86400;
            message = message + "�f" + days + " �ed ";
        }
        if (seconds >= 3600) {
            int hours = seconds / 3600;
            seconds %= 3600;
            message = message + "�f" + hours + " �eh ";
        }
        if (seconds >= 60) {
            int min = seconds / 60;
            seconds %= 60;
            message = message + "�f" + min + " �em ";
        }
        if (seconds >= 0) {
            message = message + "�f" + seconds + " �es ";
        }
        return message;
    }
}
