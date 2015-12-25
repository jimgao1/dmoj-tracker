
public class Main {

    public static void main(String[] args){
        DMOJUser user = new DMOJUser("XIAOAGE");
        user.updateProblems();

        System.out.println(user.userName);
        System.out.println(user.displayName);
        System.out.println(user.totalPoints);

        for (DMOJProblem prob : user.solvedProblems){
            System.out.printf("id: %s, name: %s, points: %f\n", prob.problemID, prob.problemName, prob.pointValue);
        }
    }

}
