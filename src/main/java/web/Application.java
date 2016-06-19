/**
 * Created by byron on 16/06/2016.
 */

package web;
import static spark.Spark.*;

public class Application {
    private static String statusNumberJSON(String status, String number){
        String jsonReturn = "{ " +
                "\"Status\": \"#{status}\"," +
                "\"Number\": \"#{number}\"" +
                "}";
        return jsonReturn
                .replace("#{status}", status)
                .replace("#{number}", number);
    }

    private static String numberToWord(String x){
        try{
            NumberToWord number = new NumberToWord(x);
            return statusNumberJSON("0", number.toString());
        } catch (NumberFormatException e){
            String status = "NumberFormatException: " + e.getMessage().replace("\"", "\\\"");
            return statusNumberJSON(status, "Nil");
        } catch (NumberToWordException e) {
            String status = "NumberFormatException: " + e.getMessage().replace("\"", "\\\"");
            return statusNumberJSON(status, "Nil");
        }
    }

    public static void main(String[] args) {
        // Port to run on
        port(9292);

        // Static files location
        staticFiles.location("/public");

        // Routes
        get("/api/number/:number", (request, response) -> numberToWord(request.params(":number")));
        get("/api/number/", (request, response) -> numberToWord(request.params(null)));
        get("/api/number/*", (request, response) -> numberToWord(request.splat()[0]));
    }
}
