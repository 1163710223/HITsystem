package bean;

public class Class {
    private String name;
    private String id;
    private int imageId;
    public Class(){

    }
    public Class(String name, String id, int imageId){
        this.imageId=imageId;
        this.name=name;
        this.id=id;
    }
    public Class(String name, int imageId){
        this.imageId=imageId;
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
    public String getId(){
        return id;
    }
}
