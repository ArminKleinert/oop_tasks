package tutorium;

public class Cat {
    private String name;
    private final String status;
    private String emotion;
    private String activity;
    private String noise;
    private int age, weight;
    private boolean alive;

    public Cat() {
        this.name = "garfield";
        this.status = "pet";
        this.emotion = "entiteld";
        this.activity = "chilling";
        this.noise = "meow";
        this.age = 5;
        this.weight = 5000;
        this.alive = true;
    }

    public void food() {
        activity = "sleeping";
        weight += 500;
        noise = "mampfmampf";
    }

    public void mouse() {
        emotion = "exited";
        activity = "hunting";
        noise = "MEOW";
    }

    public void hound() {
        emotion = "scared";
        activity = "running";
        weight -= 500;
        noise = "mOeW";
    }

    public void couch() {
        weight -= 100;
        emotion = "empowerd";
        activity = "scratching couch";
        noise = "purpur";
    }

    public void newCat(String name, int age, int weight, boolean alive, String emotion, String activity, String noise) {
        this.name =name;
        this.age =age;
        this.weight =weight;
        this.alive =alive;
        this.emotion =emotion;
        this.activity =activity;
        this.noise =noise;
    }

    public void isAlive() {
        if (weight > 10000 || weight < 2000) {
            alive = false;
        }
        System.out.println(name + " is dead.");
    }
}
