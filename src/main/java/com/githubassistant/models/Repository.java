package com.githubassistant.models;

public class Repository {
    private String name;
    private String fullName;
    private String description;
    private int stars;
    private int forks;
    private String htmlUrl;

    public Repository(String name, String fullName, String description, int stars, int forks, String htmlUrl) {
        this.name = name;
        this.fullName = fullName;
        this.description = description;
        this.stars = stars;
        this.forks = forks;
        this.htmlUrl = htmlUrl;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)\nStars: %d | Forks: %d\nURL: %s\nDescription: %s\n",
                name, fullName, stars, forks, htmlUrl, description);
    }
}
