package com.tstanvir.quiztestsystemupdate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class LandingActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText name;
    private Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        name=findViewById(R.id.personName);
        submitButton=findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.submit_button:
            {
                String nameString=name.getText().toString().trim();
                //TODO: Implement the control part

                if(nameString.length()>0){
                    Intent intent=new Intent(LandingActivity.this,MainActivity.class);
                    intent.putExtra("nameString",nameString);
                    startActivity(intent);

                }
                else Toast.makeText(LandingActivity.this,"Enter Your Name Please",LENGTH_SHORT).show();
                break;
            }
        }
    }
}