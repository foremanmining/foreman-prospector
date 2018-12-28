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
     * Returns the caption.
     *
     * @return The caption.
     */
    String getCaption();

    /**
     * Returns the display text.
     *
     * @return The display text.
     */
    String getTitle();

    /**
     * Checks to see if the {@link MenuItem} has a caption.
     *
     * @return Whether or not there's a caption.
     */
    boolean hasCaption();
}
