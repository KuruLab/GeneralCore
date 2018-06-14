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

/**
 *
 * @author Kurumin
 */
public class Stair {
    
    private String id;
    private Level lower, upper;
    private Room from, to;

    public Stair(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Level getLower() {
        return lower;
    }

    public void setLower(Level lower) {
        this.lower = lower;
    }

    public Level getUpper() {
        return upper;
    }

    public void setUpper(Level upper) {
        this.upper = upper;
    }

    public Room getFrom() {
        return from;
    }

    public void setFrom(Room from) {
        this.from = from;
    }

    public Room getTo() {
        return to;
    }

    public void setTo(Room to) {
        this.to = to;
    }
    
    @Override
    public String toString(){
        return id;
    }
}
