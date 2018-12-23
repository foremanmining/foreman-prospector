package mn.foreman.prospector.menu;

import java.util.Scanner;

/** A {@link MenuItem} provides an entry in a {@link Menu}. */
public interface MenuItem {

    /**
     * Enters the {@link MenuItem} with the provided input.
     *
     * @param input   The input.
     * @param scanner The {@link Scanner} to read.
     */
    void entered(
            String input,
            Scanner scanner);

    /**
     * Enters the {@link MenuItem}.
     *
     * @param scanner The {@link Scanner} to read.
     */
    void entered(
            Scanner scanner);

    /**
     * Returns the display text.
     *
     * @return The display text.
     */
    String getDisplayText();
}
