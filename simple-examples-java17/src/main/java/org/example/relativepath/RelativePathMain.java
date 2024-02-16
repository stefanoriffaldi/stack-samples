package org.example.relativepath;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * StackOverflow question:
 * <a href="https://stackoverflow.com/q/77930342/11289119">Relative path in Java for program that run on other machines</a>
 */
public class RelativePathMain {
    public static void main(String[] args) throws IOException {
        // from command line argument
        String directoryPath = args[0];
        RelativePathService serviceWithCommandLineArgument = new RelativePathService(directoryPath);
        serviceWithCommandLineArgument.doSomethingWithDirectoryPath();
        // from configuration file
        String configurationFile = args[1]; // as command line argument
        Properties config = new Properties();
        try(InputStream input = new FileInputStream(configurationFile)) {
            config.load(input);
        }
        String directoryPathFromConfig = config.getProperty("directoryPath"); // from configuration file
        RelativePathService serviceWithConfigurationFile = new RelativePathService(directoryPathFromConfig);
        serviceWithConfigurationFile.doSomethingWithDirectoryPath();
    }
}
