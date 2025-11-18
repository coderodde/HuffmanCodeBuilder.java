package io.github.coderodde.compressor.app;

import static io.github.coderodde.compressor.app.HuffmanByteCompressor.BYTES_PER_BYTE_DESCRIPTOR;
import static io.github.coderodde.compressor.app.HuffmanByteCompressor.BYTES_PER_CODEWORD_LENGTH;
import static io.github.coderodde.compressor.app.HuffmanByteCompressor.BYTES_PER_CODEWORD_MAX;
import static io.github.coderodde.compressor.app.HuffmanByteCompressor.BYTES_PER_CODE_SIZE;
import static io.github.coderodde.compressor.app.HuffmanByteCompressor.BYTES_PER_RAW_DATA_LENGTH;
import java.nio.ByteBuffer;

/**
 * This class implements a method for <b>decompressing</b> byte-wise files via 
 * Huffman-coding.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.0.0 (Nov 14, 2025)
 * @since 1.0.0 (Nov 14, 2025)
 */
public final class HuffmanByteDecompressor {

    public static byte[] decompress(final byte[] rawData) {
        final HuffmanCodeTable<Byte> codeTable = inferCodeTable(rawData);
        
        final HuffmanDecoderTree<Byte> decodingTree = 
                new HuffmanDecoderTree<>(codeTable);
        
        final int headerSize = getHeaderSize(rawData);
        
        return decompressImpl(decodingTree, 
                              rawData,
                              headerSize);
    }
    
    private static byte[] 
        decompressImpl(final HuffmanDecoderTree<Byte> decodingTree,
                       final byte[] rawData,
                       final int headerSize) {
            
        final byte[] output = new byte[rawData.length - headerSize];
        
        int bitIndex = getHeaderSize(rawData) * Byte.SIZE;
        
        return output;
    }
    
    private static int getCodeSize(final byte[] rawData) {
        final byte[] sizeBytes = new byte[BYTES_PER_CODE_SIZE];
        
        System.arraycopy(rawData,
                         0,
                         sizeBytes, 
                         0,
                         sizeBytes.length);
        
        final ByteBuffer byteBuffer = ByteBuffer.wrap(sizeBytes);
        final int codeSize = byteBuffer.getInt();
        
        System.out.println("code size: " + codeSize);
        
        return codeSize;
    }
    
    /**
     * Returns the size of the header in bytes.
     * 
     * @param rawData the raw data to decode.
     * @return the size of the header in bytes.
     */
    private static int getHeaderSize(final byte[] rawData) {
        final int codeSize = getCodeSize(rawData);
        final int codeEntrySize = BYTES_PER_BYTE_DESCRIPTOR + 
                                  BYTES_PER_CODEWORD_LENGTH +
                                  BYTES_PER_CODEWORD_MAX;
        
        return BYTES_PER_CODE_SIZE + 
               BYTES_PER_RAW_DATA_LENGTH +
              (codeSize * codeEntrySize);
    }
    
    private static HuffmanCodeTable<Byte> inferCodeTable(final byte[] rawData) {
        final int codeSize = getCodeSize(rawData);
        final int codeEntrySize = BYTES_PER_BYTE_DESCRIPTOR + 
                                  BYTES_PER_CODEWORD_LENGTH +
                                  BYTES_PER_CODEWORD_MAX;
        
        
        int byteCursor = codeSize;
        final HuffmanCodeTable<Byte> codeTable = new HuffmanCodeTable<>();
        
        for (int i = 0; i < codeSize; ++i) {
            loadCode(codeTable,
                     rawData, 
                     byteCursor);
            
            byteCursor += codeEntrySize;
        }
        
        return codeTable;
    }
    
    private static void loadCode(final HuffmanCodeTable<Byte> codeTable,
                                 final byte[] rawData,
                                 final int byteCursor) {
        final byte value  = rawData[byteCursor];
        final byte length = rawData[byteCursor + 1];
        
        final byte[] codeData = 
                ByteBuffer.wrap(rawData, 
                                byteCursor + 2,
                                BYTES_PER_CODEWORD_MAX)
                          .array();
        
        final CodeWord codeword = inferCodeword(length, codeData);
        
        codeTable.linkSymbolToCodeword(value, codeword);
    }
    
    private static CodeWord inferCodeword(final int length, 
                                          final byte[] codeData) {
        
        final int bits = ByteBuffer.wrap(codeData).getInt();
        final CodeWord codeword = new CodeWord(length);
        
        int mask = 1;
        
        for (int i = 0; i < length; ++i) {
            if ((bits & mask) != 0) {
                codeword.set(i);
            }
            
            mask <<= 1;
        }
        
        return codeword;
    }
    
//    private static HuffmanDecoderTree<Byte> 
//        inferDecodingTree(final HuffmanCodeTable<Byte> codeTable) {
//        return new HuffmanDecoderTree<>(codeTable);
//        return HuffmanDecoderTree.
//    }
}
