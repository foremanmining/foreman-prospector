package mn.foreman.prospector.menu;

import org.apache.commons.lang3.Validate;

/**
 * An {@link AbstractMenuItem} provides a common base class to all {@link
 * MenuItem MenuItems}.
 */
public abstract class AbstractMenuItem
        implements MenuItem {

    /** The display text. */
    private final String displayText;

    /**
     * Constructor.
     *
     * @param builder The reference builder.
     */
    AbstractMenuItem(
            final AbstractBuilder builder) {
        Validate.notNull(
                builder.displayText,
                "displayText cannot be null");
        Validate.notEmpty(
                builder.displayText,
                "displayText cannot be empty");
        this.displayText = builder.displayText;
    }

    /**
     * Returns the display text.
     *
     * @return The display text.
     */
    @Override
    public String getDisplayText() {
        return this.displayText;
    }

    /**
     * An {@link AbstractBuilder} provides a common base class to {@link
     * MenuItem} builders.
     *
     * @param <T> The builder type.
     * @param <V> The menu type.
     */
    public static abstract class AbstractBuilder<
            T extends AbstractBuilder,
            V extends MenuItem> {

        /** The display text. */
        private String displayText;

        /**
         * Builds the {@link MenuItem}.
         *
         * @return The {@link MenuItem}.
         */
        public abstract V build();

        /**
         * Returns this builder instance.
         *
         * @return This builder instance.
         */
        public abstract T getThis();

        /**
         * Sets the display text.
         *
         * @param displayText The display text.
         *
         * @return This builder instance.
         */
        public T setDisplayText(final String displayText) {
            if (displayText != null && !displayText.isEmpty()) {
                this.displayText = displayText;
            }
            return getThis();
        }
    }
}