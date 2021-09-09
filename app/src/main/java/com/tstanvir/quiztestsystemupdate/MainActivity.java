package com.tstanvir.quiztestsystemupdate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tstanvir.quiztestsystemupdate.data.QuestionBank;
import com.tstanvir.quiztestsystemupdate.model.Question;
import com.tstanvir.quiztestsystemupdate.utils.AnswerListAsyncResponse;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    private int answerCounter=0;
    List<Question>questionList;

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
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        checkButton.setOnClickListener(this);
        questionList= new QuestionBank().getQuestions(
                new AnswerListAsyncResponse(){
                    @Override
                    public void processFinished(ArrayList<Question> questionArrayList) {
                        showQuestion.setText( questionArrayList.get(answerCounter).getQuestion());
                    }
                }
        );

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.prev_button:{

                break;
            }
            case R.id.true_button:{

                break;
            }
            case R.id.false_button:{

                break;
            }
            case R.id.next_button:{


                break;
            }
            case R.id.reset_button:{

                break;
            }
            case R.id.check_button:{

                break;
            }
        }
    }
}