package com.example.edulockapp.ui.quiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.edulockapp.R;
import com.example.edulockapp.services.BackgroundManager;
import com.example.edulockapp.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.Random;

public class Quiz extends AppCompatActivity implements View.OnClickListener  {
    Button btnA, btnB, btnC , btnD , bukaAppAnak;
    LinearLayout hideQuestion;
    TextView tv_question, titleQuiz, descQuiz, titleSoal;
    StepView stepQuiz;
    Integer countCorrect;
    Integer finishQuiz;

    private Question question = new Question();

    private String answer;
    private int questionLength = question.questions.length;

    Random random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        random = new Random();
        countCorrect = 0;
        finishQuiz = 2;

        btnA = findViewById(R.id.btnA);
        btnA.setOnClickListener(this);

        btnB = findViewById(R.id.btnB);
        btnB.setOnClickListener(this);

        btnC = findViewById(R.id.btnC);
        btnC.setOnClickListener(this);

        btnD = findViewById(R.id.btnD);
        btnD.setOnClickListener(this);


        titleQuiz = findViewById(R.id.textView2);
        descQuiz = findViewById(R.id.align);
        titleSoal =findViewById(R.id.align2);
        hideQuestion = findViewById(R.id.container_soal);
        bukaAppAnak = findViewById(R.id.bukaAplikasiAnak);

        stepQuiz = findViewById(R.id.step_quiz);
        tv_question = findViewById(R.id.tv_question);

        NextQuestion(random.nextInt(questionLength));

        stepQuiz.go(0, true);

        stepQuiz.getState()
                .selectedCircleColor(ContextCompat.getColor(this, R.color.custom_color_secondary))
                .selectedStepNumberColor(ContextCompat.getColor(this, R.color.custom_color_primary))
                .steps(new ArrayList<String>() {{
                    add("Soal Pertama");
                    add("soal Kedua");
                }})
                .stepsNumber(3)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .commit();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnA:
                if(btnA.getText() == answer){
                    stepQuiz.go(1,true);
                    addPoint();
                    checkCorrectPoint();
                } else{
                    NextQuestion(random.nextInt(questionLength));
                    stepQuiz.go(0,true);
                    resetPoint();
                }
                break;

            case R.id.btnB:
                if(btnB.getText() == answer){
                    stepQuiz.go(1,true);
                    addPoint();
                    checkCorrectPoint();
                }else{
                    NextQuestion(random.nextInt(questionLength));
                    stepQuiz.go(0,true);
                    resetPoint();
                }
                break;

            case R.id.btnC:
                if(btnC.getText() == answer){
                    stepQuiz.go(1,true);
                    addPoint();
                    checkCorrectPoint();
                }else{
                    NextQuestion(random.nextInt(questionLength));
                    stepQuiz.go(0,true);
                    resetPoint();
                }
                break;

            case R.id.btnD:
                if(btnD.getText() == answer){
                    stepQuiz.go(1,true);
                    addPoint();
                    checkCorrectPoint();
                }else{
                    NextQuestion(random.nextInt(questionLength));
                    stepQuiz.go(0,true);
                    resetPoint();
                }
                break;
        }
    }


    public void startAct() {
        if((getIntent().getStringExtra("Broadcast_receiver") == null)){
        }
        finish();
    }

    private void GameOver(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Quiz.this);
        alertDialogBuilder
                .setMessage("Game Over")
                .setCancelable(false)
                .setPositiveButton("New Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), Quiz.class));
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
        alertDialogBuilder.show();

    }

    private void addPoint(){
        countCorrect +=1;
    }

    private void resetPoint(){
        countCorrect = 0;
    }

    private void checkCorrectPoint(){
        if(countCorrect.equals(finishQuiz)){
            stepQuiz.done(true);
            BackgroundManager.getInstance().init(this).startAlarmManager();
            showBottomSheet();
            hideLayoutQuiz();
        } else {
            NextQuestion(random.nextInt(questionLength));
        }
    }

    private void NextQuestion(int num){
        tv_question.setText(question.getQuestion(num));
        btnA.setText(question.getchoice1(num));
        btnB.setText(question.getchoice2(num));
        btnC.setText(question.getchoice3(num));
        btnD.setText(question.getchoice4(num));

        answer = question.getCorrectAnswer(num);
    }

    private void hideLayoutQuiz(){
        btnA.setVisibility(View.INVISIBLE);
        btnB.setVisibility(View.INVISIBLE);
        btnC.setVisibility(View.INVISIBLE);
        btnD.setVisibility(View.INVISIBLE);
        titleQuiz.setText("Aplikasi Terbuka");
        descQuiz.setText("Kamu berhasil membuka aplikasi dengan menjawab 2 soal benar, Berturut-turut.");
        titleSoal.setVisibility(View.INVISIBLE);
        hideQuestion.setVisibility(View.INVISIBLE);
    }

    private void showBottomSheet(){
        bukaAppAnak = findViewById(R.id.bukaAplikasiAnak);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                Quiz.this, R.style.BottomSheetDialogTheme
        );
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(
                        R.layout.layout_bottom_sheet,
                        findViewById(R.id.bottomSheetContainer)
                );
        bottomSheetView.findViewById(R.id.bukaAplikasiAnak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAct();
            }
        });
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

    }

    public void onBackPressed() {
        startCurrentHomePackage();
        finish();
        super.onBackPressed();
    }

    private void startCurrentHomePackage() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);

        ActivityInfo activityInfo = resolveInfo.activityInfo;
        ComponentName componentName = new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        startActivity(intent);
        new Utils(this).clearLastApp();
    }

}