package com.jun.plugin.security.util.idgen;

import java.security.SecureRandom;
import java.util.Random;

/**
 * A class for generating unique String IDs.
 * <p>
 * The implementations of the core logic in this class are based on NanoId, a JavaScript
 * library by Andrey Sitnik released under the MIT license. (https://github.com/ai/nanoid)
 * <p>
 *  Referenced from (https://github.com/dph5199278/jnanoid)
 * </p>
 *
 * @version 2022-05-21 9:53
 */
public enum NanoId {


    /**
     * NanoId instance.
     */
    INSTANCE;

    /**
     * The default random number generator used by this class.
     * Creates cryptographically strong NanoId Strings.
     */
    private final SecureRandom DEFAULT_NUMBER_GENERATOR = new SecureRandom();

    /**
     * The default alphabet used by this class.
     * Creates url-friendly NanoId Strings using 64 unique symbols.
     */
    private final char[] DEFAULT_ALPHABET =
            "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    /**
     * The default size used by this class.
     * Creates NanoId Strings with slightly more unique values than UUID v4.
     */
    private final int DEFAULT_SIZE = 21;

    /**
     * Static factory to retrieve an url-friendly, pseudo randomly generated, NanoId String.
     * <p>
     * The generated NanoId String will have 21 symbols.
     * <p>
     * The NanoId String is generated using a cryptographically strong pseudo random number
     * generator.
     *
     * @return A randomly generated NanoId String.
     */
    public String randomNanoId() {
        return randomNanoId(DEFAULT_NUMBER_GENERATOR, DEFAULT_ALPHABET, DEFAULT_SIZE);
    }

    /**
     * Static factory to retrieve an url-friendly, pseudo randomly generated, NanoId String.
     * <p>
     * The NanoId String is generated using a cryptographically strong pseudo random number
     * generator.
     *
     * @param size The number of symbols in the NanoId String.
     * @return A randomly generated NanoId String.
     */
    public String randomNanoId(final int size) {
        return randomNanoId(DEFAULT_NUMBER_GENERATOR, DEFAULT_ALPHABET, size);
    }

    /**
     * Static factory to retrieve an url-friendly, pseudo randomly generated, NanoId String.
     * <p>
     * The NanoId String is generated using a cryptographically strong pseudo random number
     * generator.
     *
     * @param alphabet The symbols used in the NanoId String.
     * @return A randomly generated NanoId String.
     */
    public String randomNanoId(final char[] alphabet) {
        return randomNanoId(DEFAULT_NUMBER_GENERATOR, alphabet, DEFAULT_SIZE);
    }

    /**
     * Static factory to retrieve an url-friendly, pseudo randomly generated, NanoId String.
     * <p>
     * The NanoId String is generated using a cryptographically strong pseudo random number
     * generator.
     *
     * @param alphabet The symbols used in the NanoId String.
     * @param size     The number of symbols in the NanoId String.
     * @return A randomly generated NanoId String.
     */
    public String randomNanoId(final char[] alphabet, final int size) {
        return randomNanoId(DEFAULT_NUMBER_GENERATOR, alphabet, size);
    }

    /**
     * Static factory to retrieve a NanoId String.
     * <p>
     * The string is generated using the given random number generator.
     *
     * @param random   The random number generator.
     * @param alphabet The symbols used in the NanoId String.
     * @param size     The number of symbols in the NanoId String.
     * @return A randomly generated NanoId String.
     */
    public String randomNanoId(final Random random, final char[] alphabet, final int size) {
        if (random == null) {
            throw new IllegalArgumentException("random cannot be null.");
        }
        if (alphabet == null) {
            throw new IllegalArgumentException("alphabet cannot be null.");
        }
        if (alphabet.length == 0 || alphabet.length >= 256) {
            throw new IllegalArgumentException("alphabet must contain between 1 and 255 symbols.");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("size must be greater than zero.");
        }

        final int mask = (2 << (int) Math.floor(Math.log(alphabet.length - 1) / Math.log(2))) - 1;
        final int step = (int) Math.ceil(1.6 * mask * size / alphabet.length);

        final StringBuilder idBuilder = new StringBuilder(size);
        final byte[] bytes = new byte[step];

        while (true) {

            random.nextBytes(bytes);

            for (int i = 0; i < step; i++) {

                final int alphabetIndex = bytes[i] & mask;

                if (alphabetIndex < alphabet.length) {
                    idBuilder.append(alphabet[alphabetIndex]);
                    if (idBuilder.length() == size) {
                        return idBuilder.toString();
                    }
                }
            }
        }
    }
}
