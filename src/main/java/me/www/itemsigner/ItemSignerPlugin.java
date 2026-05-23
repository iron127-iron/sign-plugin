package me.www.itemsigner;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemSignerPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("ItemSigner 已啟用！");
    }

    @Override
    public void onDisable() {
        getLogger().info("ItemSigner 已關閉！");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("只有玩家可以使用此指令！");
            return true;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "你手上沒有物品！");
            return true;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            player.sendMessage(ChatColor.RED + "此物品無法署名！");
            return true;
        }

        // /sign
        if (command.getName().equalsIgnoreCase("sign")) {

            List<String> lore = meta.hasLore()
                    ? new ArrayList<>(meta.getLore())
                    : new ArrayList<>();

            for (String line : lore) {
                if (ChatColor.stripColor(line).startsWith("✦ 來自：")) {
                    player.sendMessage(ChatColor.RED + "這個物品已經署名過了！");
                    return true;
                }
            }

            lore.add("");
            lore.add(ChatColor.GOLD + "✦ "
                    + ChatColor.GRAY + "來自："
                    + ChatColor.AQUA + player.getName()
                    + ChatColor.GOLD + " ✦");

            meta.setLore(lore);
            item.setItemMeta(meta);

            player.sendMessage(ChatColor.GREEN + "成功署名物品！");
            return true;
        }

        // /unsign
        if (command.getName().equalsIgnoreCase("unsign")) {

            if (!meta.hasLore()) {
                player.sendMessage(ChatColor.RED + "此物品沒有署名！");
                return true;
            }

            List<String> lore = new ArrayList<>(meta.getLore());

            boolean removed = false;

            Iterator<String> iterator = lore.iterator();

            while (iterator.hasNext()) {
                String line = ChatColor.stripColor(iterator.next());

                if (line.startsWith("✦ 來自：")) {
                    iterator.remove();
                    removed = true;
                }
            }

            // 移除空白行
            lore.removeIf(String::isEmpty);

            meta.setLore(lore);
            item.setItemMeta(meta);

            if (removed) {
                player.sendMessage(ChatColor.GREEN + "成功移除署名！");
            } else {
                player.sendMessage(ChatColor.RED + "此物品沒有署名！");
            }

            return true;
        }

        return false;
    }
}