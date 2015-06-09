/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.common;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public interface Selectable
{
    public boolean isSelected();
    
    public void setSelected(boolean state);
}
