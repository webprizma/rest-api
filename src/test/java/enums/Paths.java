package enums;

public enum Paths {

    LOGIN("/api/login"),
    REGISTER("/api/register"),
    USERS("/api/users/");

    public final String url;

    Paths(String url) {
        this.url = url;
    }
}
