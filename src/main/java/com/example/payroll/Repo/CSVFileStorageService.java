package com.example.payroll.Repo;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CSVFileStorageService {
    private static final Logger log = LoggerFactory.getLogger(CSVFileStorageService.class);
    private final Path root = Paths.get("uploads");

    public void init() throws RuntimeException {

        if( Files.isDirectory(root) )
            return;

        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public void save(MultipartFile file) throws RuntimeException {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public Resource load(String filename) throws RuntimeException {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    public void delete(String filename) {

        try{
            Path file = root.resolve(filename);
            Files.deleteIfExists(file);

        } catch (IOException e) {
            throw new RuntimeException("Could not delete the file. Error: " + e.getMessage());
        }
    }

    public int isValidCSV(String filename) throws RuntimeException {

        try {
            File file = load(filename).getFile();
            CSVReader reader = new CSVReader(new FileReader(file));

            reader.skip(1); // skip first line for titles
            String[] nextLine;
            boolean validCSV = true;
            int lines=0;

            while ((nextLine = reader.readNext()) != null) {

                // check number of columns and if name is empty
                if (nextLine.length != 2 || nextLine[0].trim().equals("")) {
                    validCSV = false;
                    break;
                }else{
                    // check if salary is a float
                    try {
                        Float.parseFloat(nextLine[1].trim());
                    } catch (NumberFormatException e) {
                        validCSV = false;
                        break;
                    }
                }
                lines++;
                log.info(nextLine[0] + "," + nextLine[1]);
            }

            reader.close();

            if(!validCSV)
                throw new RuntimeException("Invalid values found in CSV. Error: ");

            return lines;

        }catch (IOException e){
            throw new RuntimeException("Could not read the file. Error: " + e.getMessage());

        }catch(CsvValidationException e){
            throw new RuntimeException("Invalid CSV format. Error: " + e.getMessage());
        }
    }
}
