package io.github.coderodde.compressor.app;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides a method for building instances of
 * {@link io.github.coderodde.compressor.app.WeightDistribution} over byte-wise
 * data.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.0.0 (Nov 16, 2025)
 * @since 1.0.0 (Nov 16, 2025)
 */
public final class ByteWeightDistributionBuilder {

    private static final int BYTE_ALPHABET_SIZE = 256;
    
    private ByteWeightDistributionBuilder() {
        
    }
    
    /**
     * Builds and returns the weight distribution of the input raw data.
     * 
     * @param rawData the byte array holding the data to compress.
     * 
     * @return the weight distribution.
     */
    public static WeightDistribution<Byte> 
        buildByteWeightDistribution(final byte[] rawData) {
        
        final WeightDistribution<Byte> weightDistribution =
                new WeightDistribution<>();
        
        final Map<Byte, Integer> frequencyMap = 
                new HashMap<>(BYTE_ALPHABET_SIZE);
        
        for (final byte b : rawData) {
            frequencyMap.put(b, frequencyMap.getOrDefault(b, 0) + 1);
        }
        
        for (final Map.Entry<Byte, Integer> entry : frequencyMap.entrySet()) {
            weightDistribution.associateSymbolWithWeight(entry.getKey(),
                                                         entry.getValue());
        }
        
        return weightDistribution;
    }
}
