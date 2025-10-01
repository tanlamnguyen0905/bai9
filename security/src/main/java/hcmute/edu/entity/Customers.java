package hcmute.edu.entity;

import jakarta.persistence.*;
public class Customers {
    private String id;
    private String name;
    private String phoneNumber;
    private String email;

    // private constructor để ép buộc dùng Builder
    private Customers(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;
    }

    // Getter (có thể tự viết hoặc dùng IDE generate)
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    // Static inner class Builder
    public static class Builder {
        private String id;
        private String name;
        private String phoneNumber;
        private String email;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Customers build() {
            return new Customers(this);
        }
    }
}
