package school.ui.tests;

/* Make sure the downloaded jar file is in the classpath or has been added to 
referenced libraries if you are using an SDK like eclipse or netbeans*/
import org.json.JSONArray;
import org.json.JSONObject;
public class TestSending
{
 public static void main(String[] args_)
 {
     // Specify your login credentials
     String username = "SoftEarthTechnologies";
     String apiKey   = "95612f4a153df664312747c3c50cb3d14c015e8144d9bf2cbdb6f640b54e37b4";
     // Specify the numbers that you want to send to in a comma-separated list
     // Please ensure you include the country code (+254 for Kenya in this case)
     String recipients = "+256786277071,+256758910850, +256773610029";
     // And of course we want our recipients to know what we really do
     String message = "We are SoftEarth Technologies. We code all day and sleep all night";
     // Create a new instance of our awesome gateway class
     AfricasTalkingGateway gateway  = new AfricasTalkingGateway(username, apiKey);
     /*************************************************************************************
         NOTE: If connecting to the sandbox:
         1. Use "sandbox" as the username
         2. Use the apiKey generated from your sandbox application
             https://account.africastalking.com/apps/sandbox/settings/key
         3. Add the "sandbox" flag to the constructor
         AfricasTalkingGateway gateway = new AfricasTalkingGateway(username, apiKey, "sandbox");
     **************************************************************************************/
     // Thats it, hit send and we'll take care of the rest. Any errors will
     // be captured in the Exception class below
     try {
         JSONArray results = gateway.sendMessage(recipients, message);
         for( int i = 0; i < results.length(); ++i ) {
             JSONObject result = results.getJSONObject(i);
             System.out.print(result.getString("status") + ","); // status is either "Success" or "error message"
             System.out.print(result.getString("number") + ",");
             System.out.print(result.getString("messageId") + ",");
             System.out.println(result.getString("cost"));
         }
     } catch (Exception e) {
         System.out.println("Encountered an error while sending " + e.getMessage());
     }
 }
}