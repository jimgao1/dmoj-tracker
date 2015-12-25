import org.json.JSONObject;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;

public class DMOJUser {
    public String userName;
    public String displayName;
    public double totalPoints;

    public ArrayList<DMOJProblem> solvedProblems;

    public DMOJUser(String name){
        this.userName = name;
        solvedProblems = new ArrayList<DMOJProblem>();
    }

    public String getResponse(String request) throws Exception{
        System.out.println("Processing request: " + request);



        URL url = new URL(request);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String response = "";
        while(true){
            String cur = reader.readLine();
            if (cur == null) break;
            response += cur;
        }

        return response;
    }

    public void updateProblems(){

        try{
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);


            JSONObject userInfo = new JSONObject(getResponse("https://dmoj.ca/api/user/info/" + this.userName));
            this.displayName = (String)userInfo.get("display_name");
            this.totalPoints = (Double)userInfo.get("points");

            JSONObject submissions = new JSONObject(getResponse("https://dmoj.ca/api/user/submissions/" + this.userName));
            Iterator<?> subm = submissions.keys();

            while(subm.hasNext()){
                String submissionID = (String)subm.next();

                if (submissions.get(submissionID) instanceof JSONObject){
                    String problemID = (String)((JSONObject) submissions.get(submissionID)).get("problem");
                    String problemStatus = (String)((JSONObject) submissions.get(submissionID)).get("result");;

                    if (problemStatus.equals("AC")) {
                        JSONObject problemInfo = new JSONObject(getResponse("https://dmoj.ca/api/problem/info/" + problemID));
                        String problemName = (String) problemInfo.get("name");
                        double problemPoints = (Double) problemInfo.get("points");

                        boolean flag = true;

                        for (DMOJProblem p : this.solvedProblems){
                            if (p.problemID.equals(problemID)){
                                flag = false;
                            }
                        }

                        if (flag)
                            this.solvedProblems.add(new DMOJProblem(problemID, problemName, problemPoints));
                    }
                } else {
                    throw new Exception("Error parsing submissions");
                }
            }

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
