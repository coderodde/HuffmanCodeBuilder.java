package io.github.coderodde.compressor.app;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Objects;

/**
 * This class implements a method for compressing byte-wise files via Huffman-
 * coding.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.0.0 (Nov 14, 2025)
 * @since 1.0.0 (Nov 14, 2025)
 */
public final class HuffmanByteCompressor {

    /**
     * Specifies how many bytes to use in order to communicate the size of the
     * Huffman code.
     */
    static final int BYTES_PER_CODE_SIZE = 4;
    
    /**
     * Specifies how many bytes to use in order to communicate the actual length
     * (in bytes) of the input byte array.
     */
    static final int BYTES_PER_RAW_DATA_LENGTH = 4;
    
    /**
     * Specifies how many bytes to reserve for describing the byte being 
     * encoded. 
     */
    static final int BYTES_PER_BYTE_DESCRIPTOR = 1;
    
    /**
     * Specifies how many bytes to reserve for signalling the codeword length.
     */
    static final int BYTES_PER_CODEWORD_LENGTH = 1;
    
    /**
     * Specifies how many bytes to use for the codeword.
     */
    static final int BYTES_PER_CODEWORD_MAX = 4;
    
    /**
     * Compresses the {@code rawData} {@code byte}-array using the input
     * {@code weightDistribution}.
     * 
     * @param weightDistribution the weight distribution to use in compressing.
     * @param rawData            the raw data to compress.
     * 
     * @return the full binary {@code byte}-array containing all the data needed
     *         to decompress the compressed file.
     */
    public static byte[] 
        compress(final WeightDistribution<Byte> weightDistribution, 
                 final byte[] rawData) {
            
        Objects.requireNonNull(weightDistribution);
        Objects.requireNonNull(rawData);
        
        System.out.println("dist");
        System.out.println(weightDistribution);
        
        if (rawData.length == 0) {
            throw new IllegalArgumentException("The input byte array is empty");
        }
        
        final HuffmanCodeTable<Byte> code = 
                HuffmanCodeBuilder.buildCode(weightDistribution);
        
        System.out.println("code");
        System.out.println(code);
        
        final int countNumberOfBytesInRawData = 
                Utils.countBitsInRawData(code, 
                                         rawData);
        
        final int countNumberOfBytesInCodeHeader = 
                Utils.countBytesInCodeHeader(code.size());
        
        final byte[] outputData = new byte[(int)(countNumberOfBytesInCodeHeader + 
                                                 countNumberOfBytesInRawData)];
        
        fillHeader(code, 
                   outputData,
                   rawData.length);
        
        fillRawData(code, 
                    outputData,
                    rawData,
                    countNumberOfBytesInCodeHeader);
        
        return outputData;
    }
        
    private static void fillHeader(final HuffmanCodeTable<Byte> code,
                                   final byte[] outputData,
                                   final int rawDataLength) {
        
        // Fill the code size at the very first 32-bit integer:
        final byte[] codeSizeBytes = 
                ByteBuffer.allocate(BYTES_PER_CODE_SIZE)
                          .putInt(code.size())
                          .array();
        
        final byte[] rawDataBytes = 
                ByteBuffer.allocate(BYTES_PER_RAW_DATA_LENGTH)
                          .putInt(rawDataLength)
                          .array();
        
        System.arraycopy(codeSizeBytes, 
                         0,
                         outputData,
                         0, 
                         codeSizeBytes.length);
        
        System.arraycopy(rawDataBytes, 
                         0,
                         outputData,
                         codeSizeBytes.length, 
                         rawDataBytes.length);
        
        int currentByteIndex = BYTES_PER_CODE_SIZE;
        
        for (final Map.Entry<Byte, CodeWord> entry : code) {
            outputData[currentByteIndex++] = entry.getKey();
            outputData[currentByteIndex++] = (byte) entry.getValue().length();
            
            // TODO: Fix this shit.
            final byte[] codewordBytes = null;
//                    convertCodeWordToBytes(entry.getValue());
            
            System.arraycopy(outputData, 
                             currentByteIndex, 
                             codewordBytes, 
                             0, 
                             BYTES_PER_CODEWORD_MAX);
            
            currentByteIndex += BYTES_PER_CODEWORD_MAX;
        }
    }
    
    private static void fillRawData(final HuffmanCodeTable<Byte> code,
                                    final byte[] outputData,
                                    final byte[] rawData,
                                    final int headerSkipBytes) {
        int bitIndex = 0;
        int byteIndex = headerSkipBytes;
        
        for (final byte currentByte : rawData) {
            final CodeWord codeword = code.getCodeword(currentByte);
            final int codewordLength = codeword.length();
            
            for (int i = 0; i < codewordLength; ++i) {
                
                if (codeword.get(i)) {
                    setBit(outputData,
                           byteIndex,
                           bitIndex);
                }
                
                ++bitIndex;
                
                if (bitIndex == Byte.SIZE) {
                    bitIndex = 0;
                    ++byteIndex;
                }
            }
        }
    }
    
    private static void setBit(final byte[] outputData,
                               final int byteIndex,
                               final int bitIndex) {
        outputData[byteIndex] |= (byte)(1 << bitIndex);
    }
    
//    private static byte[] convertCodeWordToBytes(final CodeWord codeword) {
//        final byte[] codewordBytes = new byte[BYTES_PER_CODEWORD_MAX];
//        final byte[] codewordBits  = codeword.toByteArray();
//        
//        System.arraycopy(codewordBits,
//                         0,
//                         codewordBytes, 
//                         0, 
//                         codewordBits.length);
//        
//        return codewordBytes;
//    }
}
