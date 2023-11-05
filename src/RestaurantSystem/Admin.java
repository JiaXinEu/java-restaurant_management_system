package RestaurantSystem;

public class Admin {
    String username,adminName, manager, signDate;

    public Admin(String username, String adminName, String manager, String signDate) {
        this.username = username;
        this.adminName = adminName;
        this.manager = manager;
        this.signDate = signDate;
    }

    public String getUsername() {
        return username;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getManager() {
        return manager;
    }

    public String getSignDate() {
        return signDate;
    }
}
