package io.github.coderodde.compressor.app;

/**
 * This class provides a method for building instances of
 * {@link io.github.coderodde.compressor.app.ByteFrequencyDistribution} over byte-wise
 * data.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.1.2 (Nov 21, 2025)
 * @since 1.0.0 (Nov 16, 2025)
 */
public final class ByteWeightDistributionBuilder {
    
    private ByteWeightDistributionBuilder() {
        
    }
    
    /**
     * Builds and returns the weight distribution of the input raw data.
     * 
     * @param rawData the byte array holding the data to compress.
     * 
     * @return the weight distribution.
     */
    public static ByteFrequencyDistribution 
        buildByteWeightDistribution(final byte[] rawData) {
        
        final ByteFrequencyDistribution frequencyDistribution =
                new ByteFrequencyDistribution();
        
        for (final byte value : rawData) {
            frequencyDistribution.incrementFrequency(value);
        }
        
        return frequencyDistribution;
    }
}
