package me.hawkeynl.survivalplus.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public class NameTagsEvents implements Listener {
    private Scoreboard scoreboard;

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        registerHealthBar();
        registerNameTag();
        scoreboard.getTeam("white").addPlayer(event.getPlayer());
    }

    public void registerHealthBar() {
        if(scoreboard.getObjective("health") != null) {
            scoreboard.getObjective("health").unregister();
        }

        Objective objective = scoreboard.registerNewObjective("health", "health");
        objective.setDisplayName(ChatColor.RED + "♥");
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
    }

    public void registerNameTag() {
        if(scoreboard.getTeam("white") != null) {
            scoreboard.getTeam("white").unregister();
        }

        Team team = scoreboard.registerNewTeam("white");
        team.setPrefix(ChatColor.WHITE + "");
    }
}
