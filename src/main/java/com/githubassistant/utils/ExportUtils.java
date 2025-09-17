package com.githubassistant.utils;

import com.githubassistant.models.Repository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportUtils {

    public static void exportReposCSV(List<Repository> repos, String filename) throws IOException {
        try (CSVPrinter printer = new CSVPrinter(new FileWriter(filename), CSVFormat.DEFAULT
                .withHeader("Name","Full Name","Stars","Forks","URL","Description"))) {
            for (Repository r : repos) {
                printer.printRecord(r.toString());
            }
        }
    }

    public static void exportReposJSON(List<Repository> repos, String filename) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(repos, writer);
        }
    }
}
