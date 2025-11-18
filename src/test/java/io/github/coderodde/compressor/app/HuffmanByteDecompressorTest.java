package io.github.coderodde.compressor.app;

import java.util.Arrays;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class HuffmanByteDecompressorTest {

    private static final int STRESS_TEST_ITERATIONS = 50;
    
    @Test
    public void smallTest() {
        final byte[] rawData = { 45, 46, 47, 47, 46, 47 };
        final byte[] compressedRawData =
                HuffmanByteCompressor.compress(rawData);
        
        final byte[] resultData = 
                HuffmanByteDecompressor.decompress(compressedRawData);
        
        assertTrue(Arrays.equals(rawData, resultData));
    }
    
    @Test
    public void decompressStressTest() {
        for (int i = 0; i < STRESS_TEST_ITERATIONS; ++i) {
            stressTest();
        }
    }
    
    private void stressTest() {
        final byte[] sourceData = TestUtils.getRawData();
        final byte[] compressedData = 
                HuffmanByteCompressor.compress(sourceData);
        
        final byte[] targetData = 
                HuffmanByteDecompressor.decompress(compressedData);
        
        assertTrue(Arrays.equals(sourceData, targetData));
    }
}
