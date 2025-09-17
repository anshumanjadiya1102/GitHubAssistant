package com.githubassistant.models;

public class PullRequest {
    private String title;
    private String state;
    private String url;

    public PullRequest(String title, String state, String url) {
        this.title = title;
        this.state = state;
        this.url = url;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s)", state, title, url);
    }
}
