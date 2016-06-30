package web;
import static spark.Spark.*;

public class Application {
    /**
     *
     * @param status status code of the number
     * @param number word representation of a number
     * @return JSON compliant representation of either the new number in words, or the error message
     */
    private static String statusNumberJSON(String status, String number){
        String jsonReturn = "{ " +
                "\"Status\": \"#{status}\"," +
                "\"Number\": \"#{number}\"" +
                "}";
        return jsonReturn
                .replace("#{status}", status)
                .replace("#{number}", number);
    }

    /**
     * Attempts to get the word representation of a given numeric string,
     * and then returns a JSON file.
     * @param x the word to try to convert to a number (in word representation)
     * @return JSON compliant messages.
     */
    private static String numberToWord(String x){
        try{
            NumberToWord number = new NumberToWord(x);
            return statusNumberJSON("0", number.niceString());
        } catch (NumberFormatException e){
            String status = "NumberFormatException: " + e.getMessage().replace("\"", "\\\"");
            return statusNumberJSON(status, "Nil");
        } catch (NumberToWordException e) {
            String status = "NumberFormatException: " + e.getMessage().replace("\"", "\\\"");
            return statusNumberJSON(status, "Nil");
        }
    }

    /**
     * Main application that sets the routes and static file location
     * @param args additional arguments
     */
    public static void main(String[] args) {
        // Port to run on
        port(getHerokuAssignedPort());

        // Static files location
        staticFiles.location("/public");

        // Routes
        get("/api/number/:number", (request, response) -> numberToWord(request.params(":number")));
        get("/api/number/", (request, response) -> numberToWord(request.params(null)));
        get("/api/number/*", (request, response) -> numberToWord(request.splat()[0]));
    }

    /**
     * If running on Heroku, get the assigned port number from Heroku
     * @return port to listen on
     */
    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 9292; //return default port if heroku-port isn't set (i.e. on localhost)
    }


}
