package mn.foreman.prospector.menu;

import org.apache.commons.lang3.Validate;

import java.util.function.Supplier;

/**
 * An {@link AbstractMenuItem} provides a common base class to all {@link
 * MenuItem MenuItems}.
 */
public abstract class AbstractMenuItem
        implements MenuItem {

    /** The caption. */
    private final Supplier<String> caption;

    /** The title. */
    private final String title;

    /**
     * Constructor.
     *
     * @param builder The reference builder.
     */
    AbstractMenuItem(
            final AbstractBuilder<?, ?> builder) {
        Validate.notNull(
                builder.title,
                "title cannot be null");
        Validate.notEmpty(
                builder.title,
                "title cannot be empty");
        Validate.notNull(
                builder.caption,
                "caption cannot be null");
        this.title = builder.title;
        this.caption = builder.caption;
    }

    @Override
    public String getCaption() {
        return this.caption.get();
    }

    /**
     * Returns the title.
     *
     * @return The title.
     */
    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public boolean hasCaption() {
        final String caption = this.caption.get();
        return caption != null && !caption.isEmpty();
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

        /** The caption. */
        private Supplier<String> caption = () -> "";

        /** The title. */
        private String title;

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
         * Sets the caption.
         *
         * @param caption The caption.
         *
         * @return This builder instance.
         */
        public T setCaption(final Supplier<String> caption) {
            if (caption != null) {
                this.caption = caption;
            }
            return getThis();
        }

        /**
         * Sets the caption.
         *
         * @param caption The caption.
         *
         * @return This builder instance.
         */
        public T setCaption(final String caption) {
            if (caption != null && !caption.isEmpty()) {
                this.caption = () -> caption;
            }
            return getThis();
        }

        /**
         * Sets the title.
         *
         * @param title The title.
         *
         * @return This builder instance.
         */
        public T setTitle(final String title) {
            if (title != null && !title.isEmpty()) {
                this.title = title;
            }
            return getThis();
        }
    }
}