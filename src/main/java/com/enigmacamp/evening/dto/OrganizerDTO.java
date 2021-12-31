package com.enigmacamp.evening.dto;

public class OrganizerDTO {
    private String searchByOrganizerName;

    private String searchByOrganizerEmail;

    public OrganizerDTO(String searchByOrganizerName, String searchByOrganizerEmail) {
        this.searchByOrganizerName = searchByOrganizerName;
        this.searchByOrganizerEmail = searchByOrganizerEmail;
    }

    public String getSearchByOrganizerName() {
        return searchByOrganizerName;
    }

    public void setSearchByOrganizerName(String searchByOrganizerName) {
        this.searchByOrganizerName = searchByOrganizerName;
    }

    public String getSearchByOrganizerEmail() {
        return searchByOrganizerEmail;
    }

    public void setSearchByOrganizerEmail(String searchByOrganizerEmail) {
        this.searchByOrganizerEmail = searchByOrganizerEmail;
    }
}
