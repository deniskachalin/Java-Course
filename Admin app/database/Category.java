
package database;


public class Category {
    private int id;
    private String name;
    private int hall;
    
    public Category(int id, String name, int hall){
        this.id = id;
        this.name = name;
        this.hall = hall;
    }

    public Category(String name, int hall){
        this.name = name;
        this.hall = hall;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHall() {
        return hall;
    }
    
}
