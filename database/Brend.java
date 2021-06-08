
package database;


public class Brend {
    private int id;
    private String name;
    private String company;
    
    public Brend(int id, String name, String company){
        this.id = id;
        this.name = name;
        this.company = company;
    }

    public Brend(String name, String company){
        this.name = name;
        this.company = company;
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }
    
}
