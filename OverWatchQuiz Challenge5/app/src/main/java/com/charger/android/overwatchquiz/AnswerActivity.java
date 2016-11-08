package com.charger.android.overwatchquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class AnswerActivity extends AppCompatActivity {

    /*存入extra中的键值对的键*/
    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.charger.android.overwatchquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "com.charger.android.overwatchquiz.answer_shown";
    /*存入Bundle中的AnswerShown键值*/
    private static final String KEY_ANSWER_IS_ShOWN = "answer_is_shown";

    private boolean mAnswerIsTrue;                                                                  //这是接收答案的变量
    private boolean mAnswerIsShown;                                                                 //判断用户是否看了答案

    private TextView mAnswerTextView;
    private Button mShowAnswer;

    /*newIntent用于创建一个Intent，传入正确答案并启动AnswerActivity*/
    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {                   //参数：context、答案
        Intent i = new Intent(packageContext, AnswerActivity.class);                                //Intent实例
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);                                             //把答案放入Intent
        return i;                                                                                   //返回Intent
    }

    /*协助解析出QuizActivity能用的信息*/
    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);                   //获取extra并存入变量

        if (savedInstanceState != null) {
            mAnswerIsShown = savedInstanceState.getBoolean(KEY_ANSWER_IS_ShOWN, false);
            setAnswerShownResult(mAnswerIsShown);
        }


        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);                                                         //标记用户已看答案
                mAnswerIsShown = true;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_ANSWER_IS_ShOWN, mAnswerIsShown);
    }

    /*设置返回给QuizActivity的结果*/
    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

}
