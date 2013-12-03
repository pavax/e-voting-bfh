package ch.bfh.ti.advancedweb.evoting.web;

import java.util.Set;

public class CurrentUserModel {

    private String userId;

    private String username;

    private Set<String> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public boolean isAdmin(){
        return roles.contains("ROLE_ADMIN");
    }
}
