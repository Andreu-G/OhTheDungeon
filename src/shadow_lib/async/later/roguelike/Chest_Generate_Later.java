/* 
 * Copyright (C) 2021 shadow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package shadow_lib.async.later.roguelike;

import forge_sandbox.greymerk.roguelike.treasure.TreasureChest;
import forge_sandbox.greymerk.roguelike.worldgen.Coord;
import forge_sandbox.greymerk.roguelike.worldgen.IWorldEditor;
import org.bukkit.Chunk;

/**
 *
 * @author
 */
public class Chest_Generate_Later extends Later {
    
    private TreasureChest tc;
    private IWorldEditor editor;
    private Coord pos;
    
    @Override
    public Coord getPos() {
        return pos;
    }
    
    public Chest_Generate_Later(TreasureChest tc, IWorldEditor editor, Coord pos) {
        this.tc = tc;
        this.editor = editor;
        this.pos = pos;
    }
    
    @Override
    public void doSomething() {
        tc.generate_later(editor, pos);
    }
    
    @Override
    public void doSomethingInChunk(Chunk c) {
        tc.generate_later_chunk(c, pos);
    }
}
