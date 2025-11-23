package io.github.coderodde.compressor.app;

import static io.github.coderodde.compressor.app.Configuration.CODE_TABLE_CAPACITY;

/**
 * This class models the byte frequency distributions.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.1.3 (Nov 23, 2025)
 * @since 1.0.0 (Nov 13, 2025)
 */
public final class ByteFrequencyDistribution {

    /**
     * The actual table mapping bytes to their frequencies.
     */
    private final long[] frequencies = new long[CODE_TABLE_CAPACITY]; 
    
    /**
     * Returns the number of symbol/weight mappings.
     * 
     * @return the size of this distribution. 
     */
    public int size() {
        int sz = 0;
        
        for (final long b : frequencies) {
            if (b != 0) {
                ++sz;
            }
        }
        
        return sz;
    }
    
    /**
     * Returns {@code true} if and only if this distribution is empty.
     * 
     * @return a Boolean flag indicating whether this distribution is empty.
     */
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public void incrementFrequency(final byte value) {
        ++frequencies[Byte.toUnsignedInt(value)];
    }
    
    public long getFrequency(final byte value) {
        return frequencies[Byte.toUnsignedInt(value)];
    }
}
