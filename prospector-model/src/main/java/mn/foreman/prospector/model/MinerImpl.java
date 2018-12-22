package mn.foreman.prospector.model;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * A {@link MinerImpl} provides a {@link Miner} implementation representing a
 * miner in Foreman.
 */
public class MinerImpl
        implements Miner {

    /** The API port. */
    private final int apiPort;

    /** The IP address. */
    private final String ipAddress;

    /** The type. */
    private final MinerType minerType;

    /** The miner name. */
    private final String name;

    /**
     * Constructor.
     *
     * @param builder The builder to reference.
     */
    private MinerImpl(final Builder builder) {
        Validate.notNull(
                builder.ipAddress,
                "ipAddress cannot be null");
        Validate.notEmpty(
                builder.ipAddress,
                "ipAddress cannot be empty");
        Validate.isTrue(
                builder.apiPort > 0,
                "apiPort must be > 0");
        Validate.notNull(
                builder.name,
                "name cannot be null");
        Validate.notEmpty(
                builder.name,
                "name cannot be empty");
        this.apiPort = builder.apiPort;
        this.ipAddress = builder.ipAddress;
        this.minerType = builder.minerType;
        this.name = builder.name;
    }

    @Override
    public boolean equals(final Object other) {
        boolean isEqual = false;
        if (other == this) {
            isEqual = true;
        } else if (other != null && other.getClass() == getClass()) {
            final MinerImpl otherImpl = (MinerImpl) other;
            isEqual =
                    new EqualsBuilder()
                            .append(this.apiPort, otherImpl.apiPort)
                            .append(this.ipAddress, otherImpl.ipAddress)
                            .append(this.minerType, otherImpl.minerType)
                            .append(this.name, otherImpl.name)
                            .isEquals();
        }
        return isEqual;
    }

    @Override
    public int getApiPort() {
        return this.apiPort;
    }

    @Override
    public String getIpAddress() {
        return this.ipAddress;
    }

    @Override
    public MinerType getMinerType() {
        return this.minerType;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.apiPort)
                .append(this.ipAddress)
                .append(this.minerType)
                .append(this.name)
                .hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "%s [ " +
                        "apiPort=%d, " +
                        "ipAddress=%s, " +
                        "minerType=%s, " +
                        "name=%s " +
                        " ]",
                getClass().getSimpleName(),
                this.apiPort,
                this.ipAddress,
                this.minerType,
                this.name);
    }

    /** A builder for creating new {@link MinerImpl miners}. */
    public static class Builder {

        /** The API port. */
        private int apiPort;

        /** The miner IP address. */
        private String ipAddress;

        /** The miner type. */
        private MinerType minerType;

        /** The miner name. */
        private String name;

        /**
         * Creates a new {@link Miner}.
         *
         * @return The new {@link Miner}.
         */
        public Miner build() {
            if (this.name == null || this.name.isEmpty()) {
                // No name was set, so generate one
                this.name =
                        String.format(
                                "%s - %s",
                                this.minerType.getName(),
                                this.ipAddress);
            }
            return new MinerImpl(this);
        }

        /**
         * Sets the API port.
         *
         * @param apiPort The API port.
         *
         * @return This builder instance.
         */
        public Builder setApiPort(final int apiPort) {
            this.apiPort = apiPort;
            return this;
        }

        /**
         * Sets the IP address.
         *
         * @param ipAddress The IP address.
         *
         * @return This builder instance.
         */
        public Builder setIpAddress(final String ipAddress) {
            if (ipAddress != null && !ipAddress.isEmpty()) {
                this.ipAddress = ipAddress;
            }
            return this;
        }

        /**
         * Sets the name.
         *
         * @param name The name.
         *
         * @return This builder instance.
         */
        public Builder setName(final String name) {
            if (name != null && !name.isEmpty()) {
                this.name = name;
            }
            return this;
        }

        /**
         * Sets the type.
         *
         * @param minerType The type.
         *
         * @return This builder instance.
         */
        public Builder setType(final MinerType minerType) {
            this.minerType = minerType;
            return this;
        }
    }
}