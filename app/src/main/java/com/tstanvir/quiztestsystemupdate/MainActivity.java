package com.tstanvir.quiztestsystemupdate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tstanvir.quiztestsystemupdate.data.QuestionBank;
import com.tstanvir.quiztestsystemupdate.model.Question;
import com.tstanvir.quiztestsystemupdate.utils.AnswerListAsyncResponse;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView prevButton;
    private ImageView nextButton;
    private Button trueButton;
    private Button falseButton;
    private Button resetButton;
    private Button checkButton;
    private CardView questionCard;
    private TextView showQuestion;
    private TextView ansCounterShow;
    private int answerCounter=0;
    private boolean[] ansState;
    private double userPerformance=0.0;
    private String nameString;
    List<Question>questionList;
    private double answered=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prevButton = findViewById(R.id.prev_button);
        nextButton=findViewById(R.id.next_button);
        trueButton=findViewById(R.id.true_button);
        falseButton= findViewById(R.id.false_button);
        resetButton=findViewById(R.id.reset_button);
        checkButton=findViewById(R.id.check_button);
        questionCard= findViewById(R.id.cardView);
        showQuestion=findViewById(R.id.show_question);
        ansCounterShow=findViewById(R.id.ans_counter);

        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        checkButton.setOnClickListener(this);
        nameString=getIntent().getExtras().getString("nameString");
        showQuestion.setMovementMethod(new ScrollingMovementMethod()); //showQuestion is now scrollable.
        questionList= new QuestionBank().getQuestions(
                new AnswerListAsyncResponse(){
                    @Override
                    public void processFinished(ArrayList<Question> questionArrayList) {
                        resetALL();
                    }
                }
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.prev_button:{
                if(answerCounter>0) {
                    answerCounter--;
                    updateQuestion();
                }
                else {
                    Toast.makeText(this,""+getString(R.string.question_boundary),Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.true_button:{
                if(ansState[answerCounter]==false){
                    ansState[answerCounter]=true;
                    checkAnswer(true);
                }
                else{
                    Toast.makeText(this,""+getString(R.string.feed_back),Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.false_button:{
                if(ansState[answerCounter]==false){
                    ansState[answerCounter]=true;
                    checkAnswer(false);
                }
                else{
                    Toast.makeText(this,""+getString(R.string.feed_back),Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.next_button:{

                if(answerCounter<questionList.size()-1){
                    answerCounter++;
                    updateQuestion();
                }
                else{
                    Toast.makeText(this, ""+getString(R.string.question_boundary),Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.reset_button:{
                resetALL();
                break;
            }
            case R.id.check_button:{
                double tot=questionList.size();
                double ans=(100.0*userPerformance)/answered;
                Toast.makeText(this,nameString+"'s score is "+ans+"%",Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    private void checkAnswer(boolean usersAnswer) {
        answered++;
        if(usersAnswer==questionList.get(answerCounter).isAnsToTheQues()){
            userPerformance++;
            Toast.makeText(this, ""+getString(R.string.correct_answer),Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,""+getString(R.string.wrong_answer),Toast.LENGTH_SHORT).show();
        }
    }

    private void resetALL() {
        ansState= new boolean[questionList.size()];
        Arrays.fill(ansState,false);
        answerCounter=0;
        userPerformance=0.0;
        answered=0.0;
        updateQuestion();
    }

    private void updateQuestion() {
        ansCounterShow.setText(answerCounter+" / "+questionList.size());
        showQuestion.setText( questionList.get(answerCounter).getQuestion());
    }
}