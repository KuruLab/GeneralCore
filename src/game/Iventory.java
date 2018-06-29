/*
 * Copyright (C) 2018 Kurumin
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
package game;

import java.util.ArrayList;
import java.util.List;
import puzzle.Symbol;

/**
 *
 * @author Kurumin
 */
public class Iventory {
    
    private CorePlayer player;
    private List<Symbol> symbols;
    
    public Iventory(CorePlayer owner) {
        this.player = owner;
        this.symbols = new ArrayList<>();
    }

    public CorePlayer getOwner() {
        return player;
    }

    public void setOwner(CorePlayer owner) {
        this.player = owner;
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }
    
    
    
}
