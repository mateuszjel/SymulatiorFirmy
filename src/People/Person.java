package People;

import Game.Random;

public class Person {
    final private String[] firstNames = {
            "Weronika", "Aurelia", "Anatol", "Jowita", "Wacław", "Luiza", "Alfreda", "Fabian", "Zygfryd", "Sara", "Bernard", "Rajmund", "Aniela", "Marianna"
    };
    final private String[] lastNames = {
            "Woźniak", "Adamczyk", "Madej", "Michalik", "Dziedzic", "Kurek", "Socha", "Bednarek", "Kaczmarek", "Matusiak", "Krawczyk", "Szczepaniak"
    };
    protected String firstName;
    protected String lastName;
    public Person(){
        this.firstName = this.firstNames[Random.randInt(0,this.firstNames.length)-1];
        this.lastName = this.lastNames[Random.randInt(0,this.lastNames.length)-1];
    }
    public String toString(){
        return this.firstName + " " + this.lastName;
    }
}
