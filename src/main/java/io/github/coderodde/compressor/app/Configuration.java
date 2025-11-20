package io.github.coderodde.compressor.app;

/**
 * This class contains configuration constants.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.0.0 (Nov 19, 2025)
 * @since 1.0.0 (Nov 19, 2025)
 */
final class Configuration {

    /**
     * This capacity stands for {@code 2^8 = 256}, the number of distinct byte 
     * values.
     */
    public static final int CODE_TABLE_CAPACITY = 256;
    
    private Configuration() {
        
    }
}
