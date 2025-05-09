/*
 * Copyright (c) 2019-2022 GeyserMC. http://geysermc.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/Geyser
 */

package org.geysermc.geyser.platform.spigot.world.manager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.geyser.adapters.WorldAdapter;
import org.geysermc.geyser.adapters.paper.PaperAdapters;
import org.geysermc.geyser.adapters.spigot.SpigotAdapters;
import org.geysermc.geyser.level.block.type.Block;
import org.geysermc.geyser.session.GeyserSession;

public class GeyserSpigotNativeWorldManager extends GeyserSpigotWorldManager {
    protected final WorldAdapter<World> adapter;

    public GeyserSpigotNativeWorldManager(Plugin plugin, boolean isPaper) {
        super(plugin);
        if (isPaper) {
            adapter = PaperAdapters.getWorldAdapter();
        } else {
            adapter = SpigotAdapters.getWorldAdapter();
        }
    }

    @Override
    public int getBlockAt(GeyserSession session, int x, int y, int z) {
        Player player = Bukkit.getPlayer(session.getPlayerEntity().getUsername());
        if (player == null) {
            return Block.JAVA_AIR_ID;
        }
        return adapter.getBlockAt(player.getWorld(), x, y, z);
    }

    @Nullable
    @Override
    public String[] getBiomeIdentifiers(boolean withTags) {
        // Biome identifiers will basically always be the same for one server, since you have to re-send the
        // ClientboundLoginPacket to change the registry. Therefore, don't bother caching for each player.
        return adapter.getBiomeSuggestions(withTags);
    }
}
