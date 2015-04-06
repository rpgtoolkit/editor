package rpgtoolkit.editor.utilities;

import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;

/**
 * A field that consists of a JFormattedTextField with a default formatter that
 * ensures it contains integers. (-2, -1, 0, 1, 2, etc.) Use any time you want a
 * text field in which the user should enter only integers.
 *
 * @author Joel Moore
 */
public class IntegerField extends JFormattedTextField {

    /**
     * Creates a IntegerField with the specified value. This will create and
     * use a Formatter that requires values that are integers.
     *
     * @param value Initial value for the IntegerField
     */
    public IntegerField(Object value) {
        super(getIntegerFormatter());
        this.setValue(value);
    }

    public static NumberFormatter getIntegerFormatter() {
        NumberFormatter intFormatter = new NumberFormatter(
                NumberFormat.getIntegerInstance());
        intFormatter.setValueClass(Integer.class);
        return intFormatter;
    }
}
