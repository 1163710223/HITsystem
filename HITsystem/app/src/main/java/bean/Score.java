package bean;

public class Score {

    private String name;
    private String stunum;
    private String score;

    public Score(){

    }
    public Score(String name, String stunum, String score) {
        this.name = name;
        this.stunum = stunum;
        this.score = score;
    }

    public String getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public String getStunum() {
        return stunum;
    }

    @Override
    public String toString(){

        return "name:"+name+",stunum:"+stunum+",stuscore:"+score;
    }
}
