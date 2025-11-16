package io.github.coderodde.compressor.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements a Huffman compressor for binary (byte-wise) data.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.0.0 (Nov 14, 2025)
 * @since 1.0.0 (Nov 14, 2025))
 */
public final class App {
    
    private static final int EXIT_FAILURE = 1;
    
    /**
     * This extension is added to the compressed files.
     */
    private static final String COMPRESSED_FILE_EXTENSION = ".huf";

    public static void main(String[] args) throws IOException {
        
        if (args.length == 1) {
            compressFile(args[0]);
        } else if (args.length == 2) {
            decompressFile(args[0], args[1]);
        }
        
        final String inputFileName  = args[0];
        final File inputFile = new File(inputFileName);
        
        if (!inputFile.exists()) {
            System.err.printf(
                    "Input file '%s' does not exist.\n", 
                    inputFileName);
            
            System.exit(1);
        }
        
        if (inputFileName.endsWith(COMPRESSED_FILE_EXTENSION)) {
            System.err.printf(
                    "Input file '%s' already seems to  be compressed.\n", 
                    inputFileName);
            
            System.exit(1);
        }
        
        final String outputFileName = inputFileName + COMPRESSED_FILE_EXTENSION;
        final File outputFile = new File(outputFileName);
        final Path inputPath  = inputFile .toPath();
        final Path outputPath = outputFile.toPath();
        
        final byte[] rawData = Files.readAllBytes(inputPath);
        
        final WeightDistribution<Byte> weightDistribution = 
                getByteWeightDistribution(rawData);
        
        final byte[] outputData = 
                HuffmanByteCompressor.compress(weightDistribution,
                                               rawData);
        
        Files.write(outputPath, outputData);
    }
    
    private static WeightDistribution 
        getByteWeightDistribution(final byte[] rawData) {
        final WeightDistribution<Byte> distribution = 
                new WeightDistribution<>();
        
        final Map<Byte, Long> map = new HashMap<>();
        
        for (final byte b : rawData) {
            map.putIfAbsent(b, 0L);
            map.put(b, map.get(b) + 1L);
        }
        
        for (final Map.Entry<Byte, Long> entry : map.entrySet()) {
            distribution.associateSymbolWithWeight(entry.getKey(), 
                                                   entry.getValue());
        }
        
        return distribution;
    }
        
    private static void compressFile(final String inputFileName) throws IOException {
        final File inputFile = new File(inputFileName);
        
        if (!inputFile.exists()) {
            System.err.printf(
                    "[ERROR]: The input file '%s' does not exist.\n", 
                    inputFileName);
            
            System.exit(EXIT_FAILURE);
        }
        
        if (inputFileName.endsWith(COMPRESSED_FILE_EXTENSION)) {
            System.err.printf(
                "[ERROR}: Input file '%s' already seems to  be compressed.\n", 
                inputFileName);
            
            System.exit(EXIT_FAILURE);
        }
        
        final Path path = inputFile.toPath();
        final byte[] rawData = Files.readAllBytes(path);
        final WeightDistribution<Byte> weightDistribution = 
                getByteWeightDistribution(rawData);
        
        final byte[] compressedData = 
                HuffmanByteCompressor.compress(weightDistribution,
                                               rawData);
        
        final File outputFile = 
                new File(inputFileName + COMPRESSED_FILE_EXTENSION);
        
        Files.write(outputFile.toPath(), 
                    compressedData);
    }
    
    private static void decompressFile(final String compressedFileName,
                                       final String outputFileName) {
        
    }
}
