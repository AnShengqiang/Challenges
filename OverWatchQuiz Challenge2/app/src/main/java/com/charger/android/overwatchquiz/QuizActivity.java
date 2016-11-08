package com.charger.android.overwatchquiz;

import android.support.v7.app.AppCompatActivity;//AppCompatActivity为旧版安卓系统提供支持
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";

    /*定义成员变量*/
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mPrevButton;
    private ImageButton mNextButton;
    private TextView mQuestionTextView;

    /*新建Question[]对象数组，用来装Question对象
    * 每一个Question对象都是一个守望先锋题目*/
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.xiaomei_question, true),
            new Question(R.string.lucio_question, false),
            new Question(R.string.torbjorn_question, true),
            new Question(R.string.soldier76_question, false),
            new Question(R.string.reinhardt_question, true),
            new Question(R.string.winston_question, false)
    };

    /*数组索引变量*/
    private int mCurrentIndex = 0;

    /*封装更新问题的代码*/
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();                                 //获取当前索引下的问题的资源id
        mQuestionTextView.setText(question);                                                        //将id传入setText方法，显示当前索引下的问题*/
    }

    /*验证用户是否回答正确的方法*/
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;                                                                       //用于存入答对与答错的变量id

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);                       //引用question_text_view组件
        updateQuestion();


        /*引用Button组件*/
        mTrueButton = (Button) findViewById(R.id.true_button);
        /*监听Button点击事件 这里使用匿名内部类*/
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击true按钮发生的事件
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击false按钮发生的事件
                checkAnswer(false);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + mQuestionBank.length - 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

}
