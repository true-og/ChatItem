package me.dadus33.chatitem.hook.placeholders;

import org.bukkit.entity.Player;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;

public class LuckPermsPlaceholderHook implements IPlaceholders {

    private LuckPerms luckPerms = null;

    public LuckPerms getLuckPerms() {

        if (luckPerms == null) {

            try {

                luckPerms = LuckPermsProvider.get();

            } catch (IllegalStateException ignored) {

                // API not yet loaded.
            }

        }

        return luckPerms;

    }

    @Override
    public String replace(Player p, String text) {

        LuckPerms api = getLuckPerms();
        if (api == null)
            return text;

        User user = api.getUserManager().getUser(p.getUniqueId());
        if (user == null)
            return text;

        CachedMetaData meta = user.getCachedData().getMetaData();
        String prefix = meta.getPrefix();
        String suffix = meta.getSuffix();
        if (prefix == null)
            prefix = "";
        if (suffix == null)
            suffix = "";

        return text.replace("{prefix}", prefix).replace("{suffix}", suffix).replace("%prefix%", prefix)
                .replace("%suffix%", suffix);

    }

}
