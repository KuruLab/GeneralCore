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
package map;

import puzzle.Condition;

/**
 *
 * @author Kurumin
 */
public class Door {
    
    private String id;
    private Room a, b;
    private Condition condition;
    private boolean open;

    public Door(String id) {
        this.id = id;
        this.condition = new Condition();
        this.open = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Room getA() {
        return a;
    }

    public void setA(Room a) {
        this.a = a;
    }

    public Room getB() {
        return b;
    }

    public void setB(Room b) {
        this.b = b;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
    
    
}
