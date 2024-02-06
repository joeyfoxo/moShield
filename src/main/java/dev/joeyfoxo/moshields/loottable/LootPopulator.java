package dev.joeyfoxo.moshields.loottable;

import dev.joeyfoxo.moshields.util.UtilClass;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LootPopulator {

    public void populateLoot(Inventory inventory, ItemStack upgrade, double chance) {

        if (!UtilClass.percentChance(chance)) {
            return;
        }

        List<Integer> shuffleSlotList = getFreeSlotIndices(inventory);
        Collections.shuffle(shuffleSlotList);

        int slot = shuffleSlotList.get(0);
        inventory.setItem(slot, upgrade);

    }

    public List<Integer> getFreeSlotIndices(Inventory inventory) {
        List<Integer> freeSlotIndices = new ArrayList<>();
        ItemStack[] contents = inventory.getContents();
        for (int i = 0; i < contents.length; i++) {
            if (contents[i] == null) {
                freeSlotIndices.add(i);
            }
        }
        return freeSlotIndices;
    }

}
