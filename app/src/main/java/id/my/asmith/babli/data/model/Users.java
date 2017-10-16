package id.my.asmith.babli.data.model;

/**
 * Created by A. A. Sumitro on 10/15/2017.
 * aasumitro@gmail.com
 * https://asmith.my.id/
 */

public class Users {
    private String name;
    private String email;
    private String uid;
    private String password;
    private String old_password;
    private String new_password;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUnique_id() {
        return uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

}
