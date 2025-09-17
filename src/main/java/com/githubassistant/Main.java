package com.githubassistant;

import com.githubassistant.api.GitHubClient;
import com.githubassistant.models.Repository;
import com.githubassistant.utils.ConsoleUtils;
import com.githubassistant.utils.ExportUtils;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            GitHubClient client = new GitHubClient();
            System.out.println("=== GitHub Assistant CLI ===");

            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Get Repository Info");
                System.out.println("2. Get Trending Repos");
                System.out.println("3. Get Issues");
                System.out.println("4. Get Pull Requests");
                System.out.println("5. Export Trending Repos CSV/JSON");
                System.out.println("0. Exit");

                String choice = ConsoleUtils.prompt("Choose option");
                switch (choice) {
                    case "1":
                        String repoInput = ConsoleUtils.prompt("Enter repository (owner/repo)");
                        String[] parts = repoInput.split("/");
                        if (parts.length != 2) {
                            System.out.println("Invalid format");
                            break;
                        }
                        Repository repo = client.getRepoInfo(parts[0], parts[1]);
                        System.out.println(repo);
                        break;
                    case "2":
                        String lang = ConsoleUtils.prompt("Enter language (e.g., Java)");
                        List<Repository> trending = client.searchTrending(lang);
                        trending.forEach(System.out::println);
                        break;
                    case "3":
                        String repoIssues = ConsoleUtils.prompt("Enter repository (owner/repo)");
                        String[] pi = repoIssues.split("/");
                        client.getIssues(pi[0], pi[1]).forEach(System.out::println);
                        break;
                    case "4":
                        String repoPR = ConsoleUtils.prompt("Enter repository (owner/repo)");
                        String[] prParts = repoPR.split("/");
                        client.getPullRequests(prParts[0], prParts[1]).forEach(System.out::println);
                        break;
                    case "5":
                        String language = ConsoleUtils.prompt("Trending language to export");
                        List<Repository> trend = client.searchTrending(language);
                        ExportUtils.exportReposCSV(trend, "trending.csv");
                        ExportUtils.exportReposJSON(trend, "trending.json");
                        System.out.println("Exported trending.csv and trending.json");
                        break;
                    case "0":
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option");
                }
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("Configuration Error: " + e.getMessage());
        }
    }
}

