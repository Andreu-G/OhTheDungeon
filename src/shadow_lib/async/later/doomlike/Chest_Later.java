/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shadow_lib.async.later.doomlike;

import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.jaredbgreat.dldungeons.builder.DBlock;
import forge_sandbox.jaredbgreat.dldungeons.pieces.chests.BasicChest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import otd.util.config.LootNode;
import otd.util.config.SimpleWorldConfig;
import otd.util.config.WorldConfig;
import shadow_lib.async.AsyncWorldEditor;
import shadow_lib.async.later.roguelike.Later;

/**
 *
 * @author
 */
public class Chest_Later extends Later {
    private Coord coords;
    private AsyncWorldEditor world;
    private Random random;
    private boolean enable;
    private BasicChest chest;
    
    private static void generate(AsyncWorldEditor world, Chunk chunk, final Random random, BasicChest chest, boolean enable, Coord pos) {
        SimpleWorldConfig swc = WorldConfig.wc.dict.get(world.getWorldName());
        int x = pos.getX() % 16;
        int y = pos.getY();
        int z = pos.getZ() % 16;
        if(x < 0) x = x + 16;
        if(z < 0) z = z + 16;
        //builtin loot
        if(enable) chest.placeChunk(chunk, x, y, z, random);
        List<ItemStack> loots = new ArrayList<>();
        for(LootNode ln : swc.doomlike.loots) {
            if(random.nextDouble() <= ln.chance) {
                int max = ln.max - ln.min;
                int amount = ln.min + random.nextInt(max + 1);
                ItemStack is = ln.getItem();
                is.setAmount(amount);
                loots.add(is);
            }
        }
        
        //custom loots
        if(!loots.isEmpty()) {
            Block block = chunk.getBlock(x, y, z);
            block.setType(DBlock.chest, true);
            if(!(block.getState() instanceof Chest)) return;
            Chest bih = (Chest) block.getState();
            Inventory inv = bih.getBlockInventory();
            for(ItemStack is : loots) {
                inv.addItem(is);
            }
        }
    }
    
    public static boolean generate_later(AsyncWorldEditor world, Random random, Coord coords, boolean enable, BasicChest chest) {
        Chest_Later later = new Chest_Later();
        later.coords = coords;
        later.random = random;
        later.world = world;
        later.enable = enable;
        later.chest = chest;
        world.addLater(later);
        
        return true;
    }

    @Override
    public Coord getPos() {
        return this.coords;
    }

    @Override
    public void doSomething() {
    }

    @Override
    public void doSomethingInChunk(Chunk c) {
        Chest_Later.generate(world, c, random, chest, enable, coords);
        this.world = null;
    }
}

