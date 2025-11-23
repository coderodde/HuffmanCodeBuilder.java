package io.github.coderodde.compressor.app;

import java.util.Set;

/**
 * The instances of this class hold sets of symbols with the sum of their 
 * frequencies.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.1.3 (Nov 23, 2025)
 * @since 1.0.0 (Nov 14, 2025)
 */
final class WeightedByteSet implements Comparable<WeightedByteSet> {

    private final Set<Byte> set;
    private final long totalSetWeight;

    WeightedByteSet(final Set<Byte> set,
                      final long totalSetWeight) {
        this.set = set;
        this.totalSetWeight = totalSetWeight;
    }
    
    Set<Byte> getSet() {
        return set;
    }
    
    long getTotalWeight() {
        return totalSetWeight;
    }

    @Override
    public int compareTo(final WeightedByteSet o) {
        return Long.compare(totalSetWeight, o.totalSetWeight);
    }
}