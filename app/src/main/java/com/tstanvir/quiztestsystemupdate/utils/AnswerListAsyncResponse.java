package com.tstanvir.quiztestsystemupdate.utils;

import com.tstanvir.quiztestsystemupdate.model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
    void processFinished(ArrayList<Question> questionArrayList);
}
