/**
 * Created by Jim_Gao on 12/24/2015.
 */
public class DMOJProblem {
    public String problemID;
    public String problemName;
    public double pointValue;

    public DMOJProblem(String id, String name, double value){
        this.problemID = id;
        this.problemName = name;
        this.pointValue = value;
    }

    public boolean equals(DMOJProblem prob){
        return this.problemID.equals(prob.problemID);
    }

}
