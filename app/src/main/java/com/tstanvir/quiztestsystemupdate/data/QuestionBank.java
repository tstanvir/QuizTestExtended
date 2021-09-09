package com.tstanvir.quiztestsystemupdate.data;


import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.tstanvir.quiztestsystemupdate.controller.AppController;
import com.tstanvir.quiztestsystemupdate.model.Question;
import com.tstanvir.quiztestsystemupdate.utils.AnswerListAsyncResponse;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    ArrayList<Question> questionArrayList = new ArrayList<>();
    //api from which we are parsing data
    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    public List<Question> getQuestions(AnswerListAsyncResponse answerListAsyncResponse) {
        //Receiving a JsonArray from the above api through http "get" method
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, (JSONArray) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0;i<response.length();i++){
                            Question question=new Question();
                            try {
                                //Log.d("Json",response.getJSONArray(i).get(0).toString());
                                question.setQuestion(response.getJSONArray(i).get(0).toString());
                                question.setAnsToTheQues(response.getJSONArray(i).getBoolean(1));
                                questionArrayList.add(question);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );
        AppController.getInstance().addToRequestQueue(jsonArrayRequest); //adding to the singleton request queue.
        answerListAsyncResponse.processFinished(questionArrayList);
        return questionArrayList;
    }

}
