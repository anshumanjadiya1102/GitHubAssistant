package com.githubassistant;

import com.githubassistant.api.GitHubClient;
import com.githubassistant.models.Repository;
import com.githubassistant.utils.ConsoleUtils;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            GitHubClient client = new GitHubClient();
            System.out.println("=== GitHub Assistant ===");

            String input = ConsoleUtils.prompt("Enter repository (owner/repo)");
            String[] parts = input.split("/");
            if (parts.length != 2) {
                System.out.println("Invalid format. Use owner/repo");
                return;
            }

            Repository repo = client.getRepoInfo(parts[0], parts[1]);
            System.out.println("\nRepository Info:");
            System.out.println(repo);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("Configuration Error: " + e.getMessage());
        }
    }
}
