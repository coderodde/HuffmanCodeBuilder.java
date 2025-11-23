package io.github.coderodde.compressor.app;

/**
 * The instances of this class are thrown when dealing with empty code tables.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.1.3 (Nov 23, 2025)
 * @since 1.0.0 (Nov 16, 2025)
 */
public final class EmptyCodeTableException extends RuntimeException {

    public EmptyCodeTableException() {
        super("The code table is empty");
    }
}
