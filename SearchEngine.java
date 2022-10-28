import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> stringList = new ArrayList<String>();


    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            String returnList = "";
            for (String word : stringList) {
                returnList += word + ", ";
            }
            return returnList;
        }
        else if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    this.stringList.add(parameters[1]);
                }
                return String.format("%s has been added to the list", parameters[1]);
            }
        else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String returnString = "";
                    for (String word : stringList) {
                        if (word.contains(parameters[1])) {
                            returnString += word + ", ";
                        }
                    }
                    return returnString;
                }
            }
            return "404 Not Found!";
                
        }
    }
}


class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}