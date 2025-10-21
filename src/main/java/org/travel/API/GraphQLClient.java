package main.java.org.travel.API;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GraphQLClient {
    public static String JourneyV3Query(String fromPlace, String toPlace) throws Exception {
        String Response;
//      String fromPlace = Userfromto.Fromstop("Fra");//"NSR:StopPlace:2534"
//      String toPlace = Userfromto.Tostop("Til");//"NSR:StopPlace:60053"
        String graphqlEndpoint = "https://api.entur.io/journey-planner/v3/graphql";
        String requestBody = String.format("""
            {
              "query": "query { trip(from: {place: \\"%s\\"}, to: {place: \\"%s\\"}) { tripPatterns { duration walkDistance legs { expectedStartTime expectedEndTime mode distance line { id publicCode } } } } }"
            }
            """, fromPlace, toPlace);


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(graphqlEndpoint))
//              .headers("Content-Type","ET-Client-Name")
//              .header("ET-Client-Name","Høgskolen_i_Østfold-Software_engineering")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response: " + response.body());
        Response = response.body();

        return Response;
    }
}