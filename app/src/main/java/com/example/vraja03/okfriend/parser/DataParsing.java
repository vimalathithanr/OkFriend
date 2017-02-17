package com.example.vraja03.okfriend.parser;

import android.content.Context;

import com.example.vraja03.okfriend.model.Answer;
import com.example.vraja03.okfriend.model.MatchProfile;
import com.example.vraja03.okfriend.model.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by VRAJA03 on 2/16/2017.
 */

public class DataParsing {

    public ArrayList<Profile> profiles = new ArrayList<>();

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {

            InputStream is = context.getAssets().open("input.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

            jsonReader(json);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    private void jsonReader(String json) {

        try {
            JSONObject rawData = new JSONObject(json);

            JSONArray profilesArray = rawData.optJSONArray("profiles");

            for (int i = 0; i < profilesArray.length(); i++) {
                JSONObject singleProfile = (JSONObject) profilesArray.get(i);
                Profile currentProfile = new Profile();
                currentProfile.setProfileId(singleProfile.optInt("id"));
                JSONArray singleProfileAnswers = singleProfile.optJSONArray("answers");
                ArrayList<Answer> currentProfileAnswers = new ArrayList<>();

                for (int j = 0; j < singleProfileAnswers.length(); j++) {
                    JSONObject currentAnswerJsonObj = (JSONObject) singleProfileAnswers.get(j);
                    Answer currentAnswer = new Answer();
                    currentAnswer.setQuestionId(currentAnswerJsonObj.optInt("questionId"));
                    currentAnswer.setAnswer(currentAnswerJsonObj.optInt("answer"));

                    JSONArray acceptableAnswersArrayJsonObj = currentAnswerJsonObj.getJSONArray("acceptableAnswers");
                    int[] accepatableAnswersArray = new int[acceptableAnswersArrayJsonObj.length()];
                    for (int k = 0; k < acceptableAnswersArrayJsonObj.length(); k++) {
                        accepatableAnswersArray[k] = acceptableAnswersArrayJsonObj.optInt(k);
                    }
                    currentAnswer.setAcceptableAnswers(accepatableAnswersArray);
                    currentAnswer.setImportance(currentAnswerJsonObj.optInt("importance"));

                    currentProfileAnswers.add(currentAnswer);
                }

                currentProfile.setAnswers250(currentProfileAnswers);
                profiles.add(currentProfile);
            }

            System.out.println("$$$$" + profiles.size());
            matchProfile();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void matchProfile() {

        for (int currProfileIndex = 0; currProfileIndex < profiles.size(); currProfileIndex++) {

            // Profile
            Profile currentProfile = profiles.get(currProfileIndex);

            //All Q A
            ArrayList<Answer> currentAnswerArray = currentProfile.getAnswers250();

            // Current answers

            for (int currAnswerIndex = 0; currAnswerIndex < currentAnswerArray.size(); currAnswerIndex++) {

                for (int otherProfileIndex = 0; otherProfileIndex < profiles.size(); otherProfileIndex++) {

                    if (otherProfileIndex != currProfileIndex) {

                        MatchProfile matchProfile = new MatchProfile();
                        Profile otherProfile = profiles.get(otherProfileIndex);
                        matchProfile.setMarks(compareTwoProfiles(currentProfile, otherProfile));
                        profiles.get(currProfileIndex).addMatchProfile(matchProfile);
                    }
                }
            }
        }

        sortProfiles_BasedOnMarks();
    }


    public int compareTwoProfiles(Profile currentProfile, Profile otherProfile) {

        int marks = 0;

        ArrayList<Answer> currentAnswerArray = currentProfile.getAnswers250();
        ArrayList<Answer> otherAnswerArray = otherProfile.getAnswers250();

        for (Answer currentAnswer : currentAnswerArray) {

            for (int otherAnswerIndex = 0; otherAnswerIndex < otherAnswerArray.size(); otherAnswerIndex++) {

                Answer otherAnswer = otherAnswerArray.get(otherAnswerIndex);

                if (otherAnswer.getQuestionId() == currentAnswer.getQuestionId()) {
                    for (int expectedAnswer : currentAnswer.getAcceptableAnswers()) {
                        if (expectedAnswer == otherAnswer.getAnswer()) {

                            switch (currentAnswer.getImportance()) {
                                case 250: {
                                    if (otherAnswer.getImportance() == 250)
                                        marks += 25;
                                    else if (otherAnswer.getImportance() == 50)
                                        marks += 20;
                                    else if (otherAnswer.getImportance() == 10)
                                        marks += 15;
                                    else if (otherAnswer.getImportance() == 1)
                                        marks += 10;
                                    else
                                        marks += 5;
                                }
                                break;

                                case 50: {
                                    if (otherAnswer.getImportance() == 250)
                                        marks += 20;
                                    else if (otherAnswer.getImportance() == 50)
                                        marks += 18;
                                    else if (otherAnswer.getImportance() == 10)
                                        marks += 14;
                                    else if (otherAnswer.getImportance() == 1)
                                        marks += 9;
                                    else
                                        marks += 4;
                                }
                                break;

                                case 10: {
                                    if (otherAnswer.getImportance() == 250)
                                        marks += 15;
                                    else if (otherAnswer.getImportance() == 50)
                                        marks += 14;
                                    else if (otherAnswer.getImportance() == 10)
                                        marks += 13;
                                    else if (otherAnswer.getImportance() == 1)
                                        marks += 8;
                                    else
                                        marks += 3;
                                }
                                break;

                                case 1: {
                                    if (otherAnswer.getImportance() == 250)
                                        marks += 10;
                                    else if (otherAnswer.getImportance() == 50)
                                        marks += 9;
                                    else if (otherAnswer.getImportance() == 10)
                                        marks += 8;
                                    else if (otherAnswer.getImportance() == 1)
                                        marks += 7;
                                    else
                                        marks += 2;
                                }
                                break;

                                case 0: {
                                    if (otherAnswer.getImportance() == 250)
                                        marks += 5;
                                    else if (otherAnswer.getImportance() == 50)
                                        marks += 4;
                                    else if (otherAnswer.getImportance() == 10)
                                        marks += 3;
                                    else if (otherAnswer.getImportance() == 1)
                                        marks += 2;
                                    else
                                        marks += 1;
                                }
                                break;

                            }

                        }
                        break;
                    }
                }

            }

        }

        return marks;
    }


    public void sortProfiles_BasedOnMarks() {

        for (int currentProfileIndex = 0; currentProfileIndex < profiles.size(); currentProfileIndex++) {

            Profile currentProfile = profiles.get(currentProfileIndex);
            ArrayList<MatchProfile> matchProfilesArray = currentProfile.getListOfMatches();

            Collections.sort(matchProfilesArray, new Comparator<MatchProfile>() {
                @Override
                public int compare(MatchProfile o1, MatchProfile o2) {
                    return (o1.getMarks() > o2.getMarks()) ? -1 : (o1.getMarks() < o2.getMarks()) ? 1 : 0;
                }
            });

            profiles.get(currentProfileIndex).setListOfMatches(matchProfilesArray);

        }

        for (int i = 0; i < profiles.size(); i++) {

            ArrayList<MatchProfile> matchProfile = profiles.get(i).getListOfMatches();

            System.out.println("Top 10 Matching profile IDs for " + profiles.get(i).getProfileId() + " are ");

            for (int j = 0; j < 10; j++) {
                System.out.println("Matching Profile ID: " +matchProfile.get(j).getProfileID());
                System.out.println("Marks: " +matchProfile.get(j).getMarks());
                System.out.println("====================");
            }

        }

    }

}
