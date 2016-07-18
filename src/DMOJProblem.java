/**
 * Created by Jim_Gao on 12/24/2015.
 */
public class DMOJProblem implements Comparable<DMOJProblem>{
    public String problemID;
    public String problemName;
    public double pointValue;

    public DMOJProblem(String id, String name, double value){
        this.problemID = id;
        this.problemName = name;
        this.pointValue = value;
    }


    @Override
    public boolean equals(Object o){
        if (!(o instanceof DMOJProblem)) return false;
        DMOJProblem prob = (DMOJProblem) o;
        return this.problemID.trim().equals(prob.problemID.trim());
    }

    @Override
    public int compareTo(DMOJProblem prob) {
        return this.problemID.compareTo(prob.problemID);
    }

    public String toString(){
        return this.problemName + "  [" + this.problemID + "]";
    }
}
