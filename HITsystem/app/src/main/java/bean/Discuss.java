package bean;

public class Discuss {


    private String discuss_id;
    private String discuss_topic;
    private String stunum;
    private String studetail;
    private String teanum;
    private String teadetail;

    public Discuss(String discuss_id, String discuss_topic,String stunum,String studetail,String teanum,String teadetail){
        this.discuss_id=discuss_id;
        this.discuss_topic=discuss_topic;
        this.stunum=stunum;
        this.studetail=studetail;
        this.teanum=teanum;
        this.teadetail=teadetail;
    }

    public String getDiscuss_id() { return discuss_id; }

    public String getDiscuss_topic() { return discuss_topic; }

    public String getStunum() { return stunum; }

    public String getStudetail() { return studetail; }

    public String getTeanum() { return teanum; }

    public String getTeadetail() { return teadetail; }

    public String toString(){
        return "discussid="+discuss_id+"discustopis="+discuss_topic+"stunum="+stunum+"studetail="+studetail+"teanum="+teanum+"teadetail="+teadetail;
    }
}
