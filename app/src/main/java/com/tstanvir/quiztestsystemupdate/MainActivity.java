package com.tstanvir.quiztestsystemupdate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tstanvir.quiztestsystemupdate.data.QuestionBank;
import com.tstanvir.quiztestsystemupdate.model.Question;
import com.tstanvir.quiztestsystemupdate.utils.AnswerListAsyncResponse;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView prevButton;
    private ImageView nextButton;
    private Button trueButton;
    private Button falseButton;
    private Button resetButton;
    private Button checkButton;
    private TextView showQuestion;
    private TextView ansCounterShow;
    private TextView maxScore;
    private int answerCounter=0;
    private StringBuilder ansState=new StringBuilder();
    private double userPerformance=0.0;
    private String nameString;
    List<Question>questionList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SharedPreferences getSharedData;
    String messageId="message";
    private int maxCount=0;

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
        showQuestion=findViewById(R.id.show_question);
        ansCounterShow=findViewById(R.id.ans_counter);
        maxScore=findViewById(R.id.max_score);

        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        checkButton.setOnClickListener(this);
        nameString=getIntent().getExtras().getString("nameString");
        showQuestion.setMovementMethod(new ScrollingMovementMethod()); //showQuestion is now scrollable.
        sharedPreferences=getSharedPreferences(messageId,MODE_PRIVATE);
        editor= sharedPreferences.edit();
        questionList= new QuestionBank().getQuestions(
                new AnswerListAsyncResponse(){
                    @Override
                    public void processFinished(ArrayList<Question> questionArrayList) {
                        //Log.d("test",""+questionArrayList.get(answerCounter).getQuestion());
                        //start from where user leave.
                        init();
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                            resetALL();
//                        }
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
                if(ansState.charAt(answerCounter)=='0'){
                    ansState.replace(answerCounter,answerCounter+1,"1");
                    checkAnswer(true);
                }
                else{
                    Toast.makeText(this,""+getString(R.string.feed_back),Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.false_button:{
                if(ansState.charAt(answerCounter)=='0'){
                    ansState.replace(answerCounter,answerCounter+1,"1");
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
                DecimalFormat df= new DecimalFormat();
                double ans=(10.0*userPerformance);
                if(!Double.isNaN(ans)) Toast.makeText(this,nameString+"'s score is "+df.format(ans)+".",Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(this,nameString+", you didn't answer one.",Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void checkAnswer(boolean usersAnswer) {
        if(usersAnswer==questionList.get(answerCounter).isAnsToTheQues()){
            userPerformance++;
            fadeAnim();
            updateQuestion();
            Toast.makeText(this, ""+getString(R.string.correct_answer),Toast.LENGTH_SHORT).show();
        }
        else{
            if(userPerformance>0) userPerformance--;
            shakeAnim();
            updateQuestion();
            Toast.makeText(this,""+getString(R.string.wrong_answer),Toast.LENGTH_SHORT).show();
        }
    }

    private void resetALL() {
        ansState.setLength(0);
        for(int i=0;i<questionList.size();i++){
            ansState.append('0');
        }
        answerCounter=0;
        userPerformance=0.0;
        maxCount=0;
        maxScore.setText(maxCount+"");
        updateQuestion();
    }
    private void init(){
        getSharedData=getSharedPreferences(messageId,MODE_PRIVATE);
        String name=getSharedData.getString(nameString,"None registered");
        //Log.d("name",name);
        if(name=="None registered"){
            resetALL();
        }
        else{
            ansState.setLength(0);
            ansState.append(getSharedData.getString(nameString+"ansState","ansState_is_not_found"));
            answerCounter=getSharedData.getInt(nameString+"answerCounter",0);
            userPerformance=getSharedData.getInt(nameString+"userPerformance",0);
            maxCount=getSharedData.getInt(nameString+"maxCount",0);
            maxScore.setText(maxCount+"");
            updateQuestion();
        }

    }


    private void updateQuestion() {
        showQuestion.scrollTo(0,0);
        ansCounterShow.setText(answerCounter+" / "+questionList.size());
        maxCount=Math.max(maxCount,(int)userPerformance*10);
        maxScore.setText(maxCount+"");
        showQuestion.setText( questionList.get(answerCounter).getQuestion());
    }
    private void shakeAnim(){
        Animation shake= AnimationUtils.loadAnimation(this,R.anim.shake_anim);
        final CardView cardView=findViewById(R.id.cardView);
        cardView.setAnimation(shake);
        shakeItBaby();
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void fadeAnim(){
        final CardView cardView=findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation= new AlphaAnimation(1.0f,0.0f);
        alphaAnimation.setDuration(250);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    private void shakeItBaby() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(250, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(250);
        }
    }

    @Override
    protected void onDestroy() {
        //Log.d("OnDestroy: ",nameString);
        editor.putString(nameString,nameString);
        editor.putInt(nameString+"userPerformance",(int)userPerformance);
        editor.putString(nameString+"ansState", String.valueOf(ansState));
        editor.putInt(nameString+"answerCounter",answerCounter);
        editor.putInt(nameString+"maxCount",Math.max(maxCount,(int)userPerformance*10));
        editor.apply();
        super.onDestroy();
    }
}