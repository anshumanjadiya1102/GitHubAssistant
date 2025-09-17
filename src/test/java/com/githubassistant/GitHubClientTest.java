package com.githubassistant;

import com.githubassistant.api.GitHubClient;
import com.githubassistant.models.Repository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class GitHubClientTest {

    @Test
    void testGetRepoInfo() throws Exception {
        GitHubClient client = new GitHubClient();
        Repository repo = client.getRepoInfo("octocat", "Hello-World");
        assertNotNull(repo);
        System.out.println(repo);
    }
}
