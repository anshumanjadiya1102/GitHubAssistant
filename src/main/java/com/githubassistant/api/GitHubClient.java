package com.githubassistant.api;

import com.githubassistant.models.Issue;
import com.githubassistant.models.PullRequest;
import com.githubassistant.models.Repository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GitHubClient {

    private final OkHttpClient client = new OkHttpClient();
    private final String token;

    public GitHubClient() throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            throw new IllegalStateException("config.properties not found. Create it from config.properties.example");
        }
        token = props.getProperty("github.token");
        if (token == null || token.isEmpty()) {
            throw new IllegalStateException("GitHub token missing in config.properties");
        }
    }

    private Request.Builder requestBuilder(String url) {
        return new Request.Builder()
                .url(url)
                .header("Authorization", "token " + token)
                .header("Accept", "application/vnd.github.v3+json");
    }

    public Repository getRepoInfo(String owner, String repoName) throws IOException {
        String url = String.format("https://api.github.com/repos/%s/%s", owner, repoName);
        try (Response response = client.newCall(requestBuilder(url).build()).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            JsonObject json = JsonParser.parseString(response.body().string()).getAsJsonObject();
            return new Repository(
                    json.get("name").getAsString(),
                    json.get("full_name").getAsString(),
                    json.has("description") && !json.get("description").isJsonNull() ? json.get("description").getAsString() : "",
                    json.get("stargazers_count").getAsInt(),
                    json.get("forks_count").getAsInt(),
                    json.get("html_url").getAsString()
            );
        }
    }

    public List<Issue> getIssues(String owner, String repoName) throws IOException {
        String url = String.format("https://api.github.com/repos/%s/%s/issues", owner, repoName);
        try (Response response = client.newCall(requestBuilder(url).build()).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            JsonArray array = JsonParser.parseString(response.body().string()).getAsJsonArray();
            List<Issue> issues = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                JsonObject obj = array.get(i).getAsJsonObject();
                if (!obj.has("pull_request")) { // ignore PRs
                    issues.add(new Issue(
                            obj.get("title").getAsString(),
                            obj.get("state").getAsString(),
                            obj.get("html_url").getAsString()
                    ));
                }
            }
            return issues;
        }
    }

    public List<PullRequest> getPullRequests(String owner, String repoName) throws IOException {
        String url = String.format("https://api.github.com/repos/%s/%s/pulls", owner, repoName);
        try (Response response = client.newCall(requestBuilder(url).build()).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            JsonArray array = JsonParser.parseString(response.body().string()).getAsJsonArray();
            List<PullRequest> prs = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                JsonObject obj = array.get(i).getAsJsonObject();
                prs.add(new PullRequest(
                        obj.get("title").getAsString(),
                        obj.get("state").getAsString(),
                        obj.get("html_url").getAsString()
                ));
            }
            return prs;
        }
    }

    public List<Repository> searchTrending(String language) throws IOException {
        // GitHub search API: sort by stars, last 30 days
        String url = "https://api.github.com/search/repositories?q=language:" + language + "+created:>=" +
                java.time.LocalDate.now().minusDays(30) + "&sort=stars&order=desc&per_page=5";
        try (Response response = client.newCall(requestBuilder(url).build()).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            JsonArray items = JsonParser.parseString(response.body().string()).getAsJsonObject().getAsJsonArray("items");
            List<Repository> repos = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                JsonObject json = items.get(i).getAsJsonObject();
                repos.add(new Repository(
                        json.get("name").getAsString(),
                        json.get("full_name").getAsString(),
                        json.has("description") && !json.get("description").isJsonNull() ? json.get("description").getAsString() : "",
                        json.get("stargazers_count").getAsInt(),
                        json.get("forks_count").getAsInt(),
                        json.get("html_url").getAsString()
                ));
            }
            return repos;
        }
    }
}

