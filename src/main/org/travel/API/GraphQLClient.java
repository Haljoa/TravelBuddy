package org.travel.API;
import org.travel.Core.Ports.JourneyClientPort;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//denne klassen er en dependency injector, for http funksjonalitet, for EnturTripDataAdapter
public class GraphQLClient implements JourneyClientPort {

    @Override
    public String journeyV3Query(String fromPlace, String toPlace) throws Exception {
        String Response;
//      String fromPlace = Userfromto.Fromstop("Fra");//"NSR:StopPlace:2534"
//      String toPlace = Userfromto.Tostop("Til");//"NSR:StopPlace:60053"
      /*
      Connection to entur API. fromPlace and toPlace are variables that need to be valid
      stops (see above for examples of valid stops).
      This in can be gotten from a different API, but for MVP is not necessary.
      For a non mvp version headers have to be sorted so it won't be rate limited
      however again not necessary for MVP.
       */

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
/*
Response is gotten in a json format, can inherently be added to mongodb straight.
The response contains three fastest trip options,
but if there is just one way to get to the end point
then it is instead received with three different times.
 */
        return Response;
    }
}