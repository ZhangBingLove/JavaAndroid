package com.haier.uhome.os.javaandroid;

public class User {

    private String name;
    private String age;


    public static class Build {

        private String name;
        private String age;

        public Build name(String name) {
            this.name = name;
            return this;
        }

        public Build age(String age) {
            this.age = age;
            return this;
        }

        public User build(){
            return new User(this);
        }

    }

    private User(Build build) {
        this.name = build.name;
        this.age = build.age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
