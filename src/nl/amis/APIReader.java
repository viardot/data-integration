package nl.amis;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APIReader {

private static final String prodURLGetData = "https://randomnumbergenerator-typescriptsig.azurewebsites.net/api/randomnumbergenerator?code=3VvfRSG9rwNkWTlPXCR8g0SADbhqf/axR3iDMnu2SvLIPND6ks/8LA==";
private static final String prodURLGetTrivia = "http://numbersapi.com/";
public static final String prodURLPostData = "https://randomnumbergenerator-typescriptsig.azurewebsites.net/api/NumericFactsDatabase?code=LbOqqn6XQNeHKxkI9Dd1DX7QtAUalVv1sF4ReBd/pBSdx0E1Fd6QAw=="; 

  public static void main(String[] args)  {
    try {
     System.out.print("Start integration, await response... : ");
     System.out.println(GetData());
    } catch (IOException | InterruptedException e) {
      System.out.println("unable to process integration");
      e.printStackTrace();
    }
  }

  private static String doGet(String url) throws IOException, InterruptedException  { 

     HttpClient client = HttpClient.newHttpClient();
     HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

     HttpResponse<String> response = client.send(request,
         HttpResponse.BodyHandlers.ofString());
        
     if (response.statusCode() == 200) return response.body();
     return null;		
  }
	
  private static Integer GetData() throws IOException, InterruptedException {

    String response = doGet(prodURLGetData);

    if (response == null) return 0;

    Integer[] intArray = ProcessJSON.getNumbers(response);
    String trivia = null;
    Integer i = 0;

    while (trivia == null && i < intArray.length) {
      trivia = getTrivia(intArray[i]);
      i++;
    }
    i--;
    if (trivia != null) return doPost(trivia, intArray[i]);
    return 0;
  }
	
  private static String getTrivia(Integer number) throws IOException, InterruptedException {	
    return doGet(prodURLGetTrivia + number + "/trivia");
  }
	
  private static Integer doPost(String trivia, Integer number) throws IOException, InterruptedException {

    String PostJSON = "{\"number\": \"" + number + "\",\"trivia\": \"" + trivia + "\", \"names\":[\"Marcel\", \"Sam\"], \"datetime\": \"2021-05-03T13:59:23.422\"}";
		
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(prodURLPostData))
                .POST(HttpRequest.BodyPublishers.ofString(PostJSON))
                .build();
	
    HttpResponse<String> response = client.send(request,
    HttpResponse.BodyHandlers.ofString());
     
    return response.statusCode();
  }
}
