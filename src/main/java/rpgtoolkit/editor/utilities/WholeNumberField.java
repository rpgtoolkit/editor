package rpgtoolkit.editor.utilities;

import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;

/**
 * A field that consists of a JFormattedTextField with a default formatter that
 * ensures it contains whole numbers. (Non-negative integers. 0, 1, 2, etc.) Use
 * any time you want a text field in which the user should enter only integers 0
 * and above. May or may not enforce a maximum value, but enforces a minimum
 * value of 0.
 *
 * @author Joel
 */
public class WholeNumberField extends JFormattedTextField {

    /**
     * Creates a WholeNumberField with the specified value. This will create and
     * use a Formatter that requires values that are whole numbers: non-negative
     * integers.
     *
     * @param value Initial value for the WholeNumberField
     */
    public WholeNumberField(Object value) {
        super(getWholeNumFormatter());
        this.setValue(value);
    }

    public static NumberFormatter getWholeNumFormatter() {
        NumberFormatter wholeNumberFormatter = new NumberFormatter(
                NumberFormat.getIntegerInstance());
        wholeNumberFormatter.setValueClass(Integer.class);
        wholeNumberFormatter.setMinimum(0);
        return wholeNumberFormatter;
    }
}
