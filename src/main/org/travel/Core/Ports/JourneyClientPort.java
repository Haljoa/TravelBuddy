package org.travel.Core.Ports;

//dette er en out-port som definerer hvordan nettverks-kallet fra GraphQLClient skal skje
public interface JourneyClientPort {
    String journeyV3Query(String from, String to) throws Exception;
}
