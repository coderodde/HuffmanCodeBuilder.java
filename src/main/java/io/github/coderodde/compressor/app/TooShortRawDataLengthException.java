package io.github.coderodde.compressor.app;

/**
 * The instances of this class denote the situations where the compressed raw 
 * data is too short.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.1.2 (Nov 21, 2025)
 * @since 1.0.0 (Nov 16, 2025)
 */
public final class TooShortRawDataLengthException extends RuntimeException {

    public TooShortRawDataLengthException(final String exceptionMessage) {
        super(exceptionMessage);
    }
}
