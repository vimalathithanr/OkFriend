package com.example.vraja03.okfriend.model;

import java.util.ArrayList;

/**
 * Created by VRAJA03 on 2/16/2017.
 */

public class Profile {

    private int profileId;
    private ArrayList<Answer> answers250;

    private ArrayList<MatchProfile> listOfMatches;

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public ArrayList<Answer> getAnswers250() {
        return answers250;
    }

    public void setAnswers250(ArrayList<Answer> answers250) {
        this.answers250 = answers250;
    }

    public void addMatchProfile(MatchProfile profile) {
        if (listOfMatches == null) {
            listOfMatches = new ArrayList<>();
        }
        listOfMatches.add(profile);
    }

    public void setListOfMatches(ArrayList<MatchProfile> list) {
        listOfMatches = list;
    }

    public ArrayList<MatchProfile> getListOfMatches() {
        return listOfMatches;
    }

}
