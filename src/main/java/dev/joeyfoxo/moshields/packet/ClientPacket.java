package dev.joeyfoxo.moshields.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.items.shields.Shield;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class ClientPacket {

    public void customDurabilityRender(Shield shield) {

        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        manager.addPacketListener(new PacketAdapter(JavaPlugin.getPlugin(MoShields.class),
                PacketType.Play.Server.SET_SLOT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Player player = event.getPlayer();

                packet.getIntegers().write(0,  0);
                packet.getIntegers().write(1,  0);
                int slot = packet.getIntegers().read(2);

                player.getInventory().setItem(slot, shield.getItem());
                //TODO: MODIFY THE ITEM THEY PICKUP TO HAVE CUSTOM DURABILITY

            }
        });

    }

}
