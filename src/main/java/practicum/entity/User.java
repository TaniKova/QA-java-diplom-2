package practicum.entity;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class User {
    private String name;
    private String email;
    private String password;

    User(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static User generateRandomUser(){
        String name = randomAlphabetic(10);
        String email = randomAlphabetic(10) + "@" + randomAlphabetic(6)+ ".ru";
        String password = randomAlphabetic(15);

        return new User(name, email, password);
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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserLogin getLogin(){
        return new UserLogin(email, password);
    }
}

