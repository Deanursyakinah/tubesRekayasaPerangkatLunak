package model;

public abstract class Account {
    private String name, password;
    private AccountStatus status;
    private int id;

    public Account() {
        
    }

    public Account(String name, String password, int id) {
        this.name = name;
        this.password = password;
        this.status = AccountStatus.NOT_BANNED;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
