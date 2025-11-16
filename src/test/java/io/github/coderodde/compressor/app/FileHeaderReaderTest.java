package io.github.coderodde.compressor.app;

import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public final class FileHeaderReaderTest {
    
    private static final int SEED = 13;
    private static final int MAXIMUM_BYTE_ARRAY_LENGTH = 10;
    private static final int STRESS_TEST_ITERATIONS = 50;
    private static final Random RANDOM = new Random(SEED);
    
    private static final byte[] COMPRESSED_DATA = new byte[1024];
    
    @Test
    public void smallTest() {
        final byte[] rawData = { 88, 40, 48 };
        final WeightDistribution<Byte> wd = 
                ByteWeightDistributionBuilder
                        .buildByteWeightDistribution(rawData);
        
        final HuffmanCodeTable<Byte> expectedCodeTable = 
                HuffmanCodeBuilder.buildCode(wd);
        
        final FileHeaderWriter writer = new FileHeaderWriter(rawData.length,
                                                             COMPRESSED_DATA,
                                                             expectedCodeTable);
        
        writer.write();
        
        final FileHeaderReader reader = new FileHeaderReader(COMPRESSED_DATA);
        
        final int resultRawDataLength = reader.getRawDataLength();
        final HuffmanCodeTable<Byte> resultCodeTable = reader.getCodeTable();
        
        assertEquals(rawData.length, resultRawDataLength);
        assertEquals(expectedCodeTable, resultCodeTable);
    }
    
//    @Test
    public void stressTest() {
        for (int i = 0; i < STRESS_TEST_ITERATIONS; ++i) {
            System.out.println("Stress test iteration: i = " + i);
            
            final byte[] rawData = getRawData();
            
            final WeightDistribution<Byte> weightDistribution =
                    ByteWeightDistributionBuilder
                            .buildByteWeightDistribution(rawData);
            
            final HuffmanCodeTable<Byte> expectedCodeTable = 
                    HuffmanCodeBuilder.buildCode(weightDistribution);
            
            final FileHeaderWriter writer = 
                    new FileHeaderWriter(rawData.length,
                                         COMPRESSED_DATA, 
                                         expectedCodeTable);
            
            writer.write();
            
            final FileHeaderReader reader = 
                    new FileHeaderReader(COMPRESSED_DATA);
            
            final int rawDataLength = reader.getRawDataLength();
            final HuffmanCodeTable<Byte> readCodeTable = reader.getCodeTable();
            
            assertEquals(rawData.length, rawDataLength);
            assertEquals(expectedCodeTable, readCodeTable);
        }
    }
    
    private static final byte[] getRawData() {
        final int length = 1 + RANDOM.nextInt(MAXIMUM_BYTE_ARRAY_LENGTH);
        final byte[] rawData = new byte[length];
        RANDOM.nextBytes(rawData);
        return rawData;
    }
}
