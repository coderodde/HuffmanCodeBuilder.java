package io.github.coderodde.compressor.app;

import java.util.Set;

/**
 * The instances of this class hold sets of symbols with the sum of their 
 * frequencies.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.0.0 (Nov 14, 2025)
 * @since 1.0.0 (Nov 14, 2025)
 */
final class WeightedSymbolSet<S> implements Comparable<WeightedSymbolSet<S>> {

    private final Set<S> set;
    private final double totalSetWeight;

    WeightedSymbolSet(final Set<S> set,
                      final double totalSetWeight) {
        this.set = set;
        this.totalSetWeight = totalSetWeight;
    }
    
    Set<S> getSet() {
        return set;
    }
    
    double getTotalWeight() {
        return totalSetWeight;
    }

    @Override
    public int compareTo(final WeightedSymbolSet<S> o) {
        return Double.compare(totalSetWeight, o.totalSetWeight);
    }
}