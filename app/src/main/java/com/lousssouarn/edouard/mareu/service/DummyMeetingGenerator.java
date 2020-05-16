package com.lousssouarn.edouard.mareu.service;

import com.lousssouarn.edouard.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting(0xFFEF5350,"Réunion A","10-05-2020","14h30","Bangkok","jean@lamzone.com, isa@lamzone.com,eric@lamzone.com"),
            new Meeting(0xFFEC407A,"Réunion B","10-05-2020","11h00","Londres","dave@lamzone.com,emily@lamzone.com,sabrina@lamzone.com"),
            new Meeting(0xFFAB47BC,"Réunion C","10-05-2020","09h00","Paris","leila@lamzone.com,mathieu@lamzone.com"),
            new Meeting(0xFF7E57C2,"Réunion D","10-05-2020","09h30","Dubaï","michel@lamzone.com,amandine@lamzone.com,alain@lazone.com"),
            new Meeting(0xFF5C6BC0,"Réunion E","10-05-2020","14h00","Singapour","damien@lamzone.com,geraldine@lamzone.com,,isaure@lamzone.com,francois@lamzone.com"),
            new Meeting(0xFF42A5F5,"Réunion F","11-05-2020","09h00","New York","moussa@lamzone.com,karim@lamzone.com,driss@lamzone.com,emilie@lamzone.com"),
            new Meeting(0xFF26C6DA,"Réunion G","12-05-2020","14h00","Kuala Lampour","aurelien@lamzone.com,suzy@lamzone.com"),
            new Meeting(0xFF26A69A,"Réunion H","11-05-2020","17h00","Tokyo","sam@lamzone.com,denise@lamzone.com,steph@lamzone.com"),
            new Meeting(0xFF66BB6A,"Réunion I","12-05-2020","15h45","Istanboul","hassan@lamzone.com,elodie@lamzone.com,romain@lamzone.com,nathalie@lamzone.com"),
            new Meeting(0xFFFFEB3B,"Réunion J","11-05-2020","10h00","Séoul","joey@lamzone.com,karl@lamzone.com")
);

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
