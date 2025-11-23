package io.github.coderodde.compressor.app;

/**
 * This exception class implements an exception that is thrown if 
 * {@link io.github.coderodde.compressor.app.WeightDistribution} does not contain a
 * requested symbol.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.1.3 (Nov 23, 2025)
 * @since 1.0.0 (Nov 13, 2025)
 */
public class SymbolNotFoundException extends RuntimeException {

    public SymbolNotFoundException(final String exceptionMessage) {
        super(exceptionMessage);
    }
}
