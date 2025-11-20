package io.github.coderodde.compressor.app;

import static io.github.coderodde.compressor.app.Configuration.CODE_TABLE_CAPACITY;

/**
 * This class implements a Huffman code table over byte (8-bit) alphabet.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.0.0 (Nov 20, 2025)
 * @since 1.0.0 (Nov 20, 2025)
 */
public final class ByteHuffmanCodeTable {
    
    /**
     * The actual codeword table.
     */
    private final CodeWord[] table = new CodeWord[CODE_TABLE_CAPACITY];
    
    /**
     * The number of byte/codeword mappings in this code table.
    */
    private int size;
    
    /**
     * Associates the input byte {@code value} with the codeword 
     * {@code codeword}.
     * 
     * @param value    the byte key.
     * @param codeword the value codeword.
     */
    public void put(final byte value, final CodeWord codeword) {
        if (table[Byte.toUnsignedInt(value)] != null) {
            throw new IllegalStateException("yeah");
        }
        table[Byte.toUnsignedInt(value)] = codeword;
        ++size;
    }
    
    /**
     * Accesses the codeword associated with the byte value {@code value}.
     * 
     * @param value the byte key.
     * @return the associated codeword.
     */
    public CodeWord get(final byte value) {
        return table[Byte.toUnsignedInt(value)];
    }
    
    /**
     * Returns the number of byte/codeword mappings in this code table.
     * 
     * @return the size of this code table.
     */
    public int size() {
        return size;
    }
    
    /**
     * Returns {@code true} if and only if this code table is empty.
     * 
     * @return {@code true} if empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }
}
