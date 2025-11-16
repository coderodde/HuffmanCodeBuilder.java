package io.github.coderodde.compressor.app;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * This class implements the Huffman code builder over weight distributions.
 * 
 * @author Rodion "rodde" Efremov
 * @version 2.0.1 (Nov 14, 2025)
 * @since 1.0.0 (Nov 12, 2025)
 */
public final class HuffmanCodeBuilder {
    
    private HuffmanCodeBuilder() {
        // Hide constructor.
    }
    
    public static <S> HuffmanCodeTable<S>
        buildCode(final WeightDistribution<S> weightDistribution) {
            
        Objects.requireNonNull(weightDistribution,
                               "The input probability distribution is null");
        
        final HuffmanCodeTable<S> codeTable = new HuffmanCodeTable<>();
        
        if (weightDistribution.isEmpty()) {
            return codeTable;
        }
        
        final Queue<WeightedSymbolSet<S>> queue = new PriorityQueue<>();
       
        for (final Map.Entry<S, Double> entry : weightDistribution) {
            final double weight = entry.getValue();
            final S symbol      = entry.getKey();
            
            final Set<S> set = new HashSet<>();
            
            set.add(symbol);
            queue.add(new WeightedSymbolSet<>(set, weight));
        }
        
        for (final Map.Entry<S, Double> weightEntry : weightDistribution) {
            codeTable.linkSymbolToCodeword(weightEntry.getKey(), 
                                           new CodeWord(0));
        }
        
        while (queue.size() > 1) {
            final WeightedSymbolSet<S> entry1 = queue.remove();
            final WeightedSymbolSet<S> entry2 = queue.remove();
            
            for (final S symbol : entry1.getSet()) {
                codeTable.getCodeword(symbol).prependBit(true);
            }
            
            for (final S symbol : entry2.getSet()) {
                codeTable.getCodeword(symbol).prependBit(false);
            }
            
            entry1.getSet().addAll(entry2.getSet());
            
            queue.add(new WeightedSymbolSet<>(
                            entry1.getSet(),
                            entry1.getTotalWeight() + 
                            entry2.getTotalWeight()));
        }
        
        return codeTable;
    }
}
