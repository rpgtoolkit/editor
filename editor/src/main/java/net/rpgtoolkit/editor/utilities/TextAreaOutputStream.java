/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.utilities;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JTextArea;

/**
 *
 * @author Joshua Michael Daly
 */
public class TextAreaOutputStream extends OutputStream
{
    private final JTextArea textArea;
    
    private final PrintStream stdOut;

    public TextAreaOutputStream(JTextArea textArea)
    {
        this.textArea = textArea;
        this.stdOut = System.out;
    }

    @Override
    public void write(int b) throws IOException
    {
        this.textArea.append(String.valueOf((char) b));
        this.textArea.setCaretPosition(this.textArea.getDocument().getLength());
        
        this.stdOut.append(String.valueOf((char) b));
    }
    
}
