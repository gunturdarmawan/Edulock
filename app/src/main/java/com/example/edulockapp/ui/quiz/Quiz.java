package com.example.edulockapp.ui.quiz;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.edulockapp.R;
import com.example.edulockapp.services.BackgroundManager;
import com.example.edulockapp.ui.lockapp.LastLock;
import com.example.edulockapp.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Quiz extends AppCompatActivity implements View.OnClickListener  {
    Button btnA, btnB, btnC , btnD , bukaAppAnak , bukaApp;
    LinearLayout hideQuestion;
    TextView tv_question, titleQuiz, descQuiz, titleSoal, percentPoint, titlePercent;
    StepView stepQuiz;
    Integer countCorrect , countIncorrect, finishQuiz, totalCount, percentage ;
    LottieAnimationView circlePoint;
    Random random;

    public int setReminderTime;
    private Question question = new Question();
    private String answer;
    private int questionLength = question.questions.length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        random = new Random();
        countCorrect = 0;
        countIncorrect = 0;
        totalCount = 0;
        percentage = 0;
        finishQuiz = 2;
        setReminderTime =  0;

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
        bukaApp = findViewById(R.id.bukaAplikasi);
        titlePercent = findViewById(R.id.title_percent);
        percentPoint = findViewById(R.id.persentage);
        circlePoint = findViewById(R.id.circle);
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

        bukaApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSuccessData();
                setReminder();
                startAct();
            }
        });

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
                    addPointIncorrect();
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
                    addPointIncorrect();
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
                    addPointIncorrect();
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
                    addPointIncorrect();
                }
                break;
        }
    }

    public void startAct() {
        Intent intent = new Intent(Quiz.this, LastLock.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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

    private void addPointIncorrect(){
        countIncorrect +=1;
    }

    private void resetPoint(){
        countCorrect = 0;
    }

    @SuppressLint("SetTextI18n")
    public void checkCorrectPoint(){
        if(countCorrect.equals(finishQuiz)){
            totalCount = finishQuiz+ countIncorrect;
            percentage = (2*10/totalCount)*10;
            percentPoint.setText(String.valueOf(percentage) + "%");
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

        bukaApp.setVisibility(View.VISIBLE);
        titlePercent.setVisibility(View.VISIBLE);
        percentPoint.setVisibility(View.VISIBLE);
        circlePoint.setVisibility(View.VISIBLE);
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
                saveSuccessData();
                setReminder();
                startAct();
            }
        });
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    public Integer defSetTime(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("newTime", MODE_PRIVATE);
        return preferences.getInt("isSuccessAnswer", 0);
    }
    public void setQuickTime(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("newTime", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("isSuccessAnswer", 15);
        editor.apply();
    }
    public void setMidTime(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("newTime", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("isSuccessAnswer", 30);
        editor.apply();
    }
    public void setLongTime(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("newTime", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("isSuccessAnswer", 45);
        editor.apply();
    }


    ////

    private boolean restoreSuccessData(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        return preferences.getBoolean("isSuccessAnswer", false);
    }

    private void saveSuccessData(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("newPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isSuccessAnswer", true);
        editor.apply();
    }

    private void restartSuccessData(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isSuccessAnswer", false);
        editor.apply();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        cycleActivity();
//        super.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        new Utils(this).clearLastApp();
//        super.onDestroy();
//    }
//
//    public void cycleActivity(){
//        if(restoreSuccessData()){
//            new Utils(this).clearLastApp();
//        } else {
//            String EXTRA_LAST_APP = "EXTRA_LAST_APP";
//            new Utils(this).setLastApp(EXTRA_LAST_APP);
//        }
//    }

    public void onBackPressed() {
        if(setReminderTime == 0){
            startCurrentHomePackage();
            finish();
            super.onBackPressed();
        } else {
            setReminder();
            startAct();
        }
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

    public void setReminder(){
        if(percentage <= 100 && percentage > 60){
            setReminderTime = 20;
            setLongTime();
        } else if(percentage <= 60 && percentage > 30){
            setReminderTime = 15;
            setMidTime();
        } else {
            setReminderTime = 10;
            setQuickTime();
        }

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar startTime = Calendar.getInstance();
        startTime.add(Calendar.SECOND, setReminderTime);
        Intent intent = new Intent(Quiz.this, Quiz.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(Quiz.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC, startTime.getTimeInMillis(), pendingIntent);
    }

}