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
            player.sendMessage(ChatColor.RED + "此物品無法使用！");
            return true;
        }

        // =========================
        // /sign
        // =========================

        if (command.getName().equalsIgnoreCase("sign")) {

            if (!player.hasPermission("itemsigner.sign")) {
                player.sendMessage(ChatColor.RED + "你沒有權限使用此指令！");
                return true;
            }

            List<String> lore = meta.hasLore()
                    ? new ArrayList<>(meta.getLore())
                    : new ArrayList<>();

            // 檢查是否已署名
            for (String line : lore) {

                String stripped = ChatColor.stripColor(line);

                if (stripped.startsWith("✦ 來自：")) {
                    player.sendMessage(ChatColor.RED + "這個物品已經署名過了！");
                    return true;
                }
            }

            lore.add("");

            lore.add(
                    ChatColor.GOLD + "✦ "
                            + ChatColor.GRAY + "來自："
                            + ChatColor.AQUA + player.getName()
                            + ChatColor.GOLD + " ✦"
            );

            meta.setLore(lore);
            item.setItemMeta(meta);

            player.sendMessage(ChatColor.GREEN + "成功署名物品！");

            return true;
        }

        // =========================
        // /unsign
        // =========================

        if (command.getName().equalsIgnoreCase("unsign")) {

            if (!player.hasPermission("itemsigner.unsign")) {
                player.sendMessage(ChatColor.RED + "你沒有權限使用此指令！");
                return true;
            }

            if (!meta.hasLore()) {
                player.sendMessage(ChatColor.RED + "此物品沒有署名！");
                return true;
            }

            List<String> lore = new ArrayList<>(meta.getLore());

            boolean removed = false;
            String owner = null;

            Iterator<String> iterator = lore.iterator();

            while (iterator.hasNext()) {

                String raw = iterator.next();
                String stripped = ChatColor.stripColor(raw);

                if (stripped.startsWith("✦ 來自：")) {

                    owner = stripped
                            .replace("✦ 來自：", "")
                            .replace(" ✦", "")
                            .trim();

                    // 不是自己的署名
                    if (!owner.equalsIgnoreCase(player.getName())) {

                        // 沒有管理權限
                        if (!player.hasPermission("itemsigner.unsign.other")) {

                            player.sendMessage(ChatColor.RED + "你不能解除其他人的署名！");
                            return true;
                        }
                    }

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
