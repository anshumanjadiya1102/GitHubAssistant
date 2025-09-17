package com.githubassistant.api; 

import com.githubassistant.models.Repository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.FileInputStream;
import java.io.IOException;
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

    public Repository getRepoInfo(String owner, String repoName) throws IOException {
        String url = String.format("https://api.github.com/repos/%s/%s", owner, repoName);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "token " + token)
                .header("Accept", "application/vnd.github.v3+json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String jsonData = response.body().string();
            JsonObject json = JsonParser.parseString(jsonData).getAsJsonObject();

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
}
