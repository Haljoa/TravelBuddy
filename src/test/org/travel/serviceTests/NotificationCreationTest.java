package org.travel.serviceTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.travel.Core.Domain.Notification.DelayNotification;
import org.travel.Core.Domain.Notification.Notification;
import org.travel.Core.Domain.Notification.NotificationType;
import org.travel.Core.Domain.Notification.OnOffNotification;
import org.travel.Core.Service.NotificationAdapter;
import org.travel.Core.DTO.NotificationData;


public class NotificationCreationTest {

    NotificationAdapter AdapterTest = new NotificationAdapter();
    org.travel.Core.DTO.NotificationData PublicDTOtest = new NotificationData();

    @Test
    void CreateDelayObjectwithNotificationAdapter() {
        //arrange
        //Create DTO
        org.travel.Core.DTO.NotificationData Delaytest = new NotificationData();
        Delaytest.notificationID = 1;  // Equals to Forsinkelse
        Delaytest.routeID = 23;
        Delaytest.delay = 4;


        //Create expected object using Domain class
        DelayNotification TestObject = new DelayNotification(
                "Forsinkelse", 1, 23,4);

        //act
        Notification DelayAdapterResult = AdapterTest.create(NotificationType.DELAY,Delaytest);

        //assert
        Assertions.assertTrue(DelayAdapterResult instanceof DelayNotification);
        DelayNotification delayResult = (DelayNotification) DelayAdapterResult;
        Assertions.assertEquals(TestObject.getDescription(),delayResult.getDescription());
        Assertions.assertEquals(TestObject.getNotificationID(),delayResult.getNotificationID());
        Assertions.assertEquals(TestObject.getDelay(),delayResult.getDelay());
        Assertions.assertEquals(TestObject.getRouteID(),delayResult.getRouteID());


    }

    @Test
    void NotificationAdapterErrorResult() {
        //arrange
        //create error DTO
        org.travel.Core.DTO.NotificationData errorDelayTest = new NotificationData();
        errorDelayTest.delay = 10;
        errorDelayTest.routeID = 123;
        errorDelayTest.notificationID = 300; //Should result in error

        //act

        //Create Notification object using adapter
        Notification errorResult = AdapterTest.create(NotificationType.DELAY,errorDelayTest);


        Assertions.assertTrue(errorResult instanceof DelayNotification);
        Assertions.assertEquals("error", errorResult.getDescription());



    }
    @Test
    void CreateOnOffDelayNotification(){
        //arrange
        //create DTO
        PublicDTOtest.notificationID = 6; // The same as first variable in the new On Off Object below
        PublicDTOtest.untilOnOff = 3;
        PublicDTOtest.routeID = 22;
        PublicDTOtest.stopID = 43;
        //Create On Off notification using the class
        OnOffNotification TestObject = new OnOffNotification(
                "Neste stasjon er avgangsstoppet",6,3,22,43);

        //act
        Notification OnOffAdapterResult = AdapterTest.create(NotificationType.ON_OFF,PublicDTOtest);

        //assert
        Assertions.assertTrue(OnOffAdapterResult instanceof OnOffNotification);
        OnOffNotification OnOffResult = (OnOffNotification) OnOffAdapterResult;
        Assertions.assertEquals(TestObject.getDescription(),OnOffResult.getDescription());
        Assertions.assertEquals(TestObject.getNotificationID(),OnOffResult.getNotificationID());
        Assertions.assertEquals(TestObject.getRouteID(),OnOffResult.getRouteID());
        Assertions.assertEquals(TestObject.getStopID(),OnOffResult.getStopID());

    }

}
