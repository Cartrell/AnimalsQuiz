package com.example.android.animalsquiz.questionbuilder;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.android.animalsquiz.IQuizChoiceCallbacks;
import com.example.android.animalsquiz.QuestionData;
import com.example.android.animalsquiz.R;

public class EditQuestionBuilder extends QuestionBuilder implements TextWatcher {

  //===============================================================
  // members
  //===============================================================
  private EditText m_editText;

  //===============================================================
  // public
  //===============================================================

  //---------------------------------------------------------------
  // afterTextChanged
  //---------------------------------------------------------------
  @Override
  public void afterTextChanged(Editable editable) {
  }

  //---------------------------------------------------------------
  // beforeTextChanged
  //---------------------------------------------------------------
  @Override
  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
  }

  //---------------------------------------------------------------
  // build
  //---------------------------------------------------------------
  @Override
  public ViewGroup build(Context context, QuestionData questionData, boolean isLastQuestion,
                         IQuizChoiceCallbacks callbacks) {
    super.build(context, questionData, isLastQuestion, callbacks);

    //Create the question linear layout. This is a container for all the view objects for
    // this question.
    LinearLayout linearLayout = new LinearLayout(context);
    linearLayout.setTag(QuestionData.CHOICE_TYPE_EDIT);
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    linearLayout.setLayoutParams(params);

    linearLayout.setOrientation(LinearLayout.VERTICAL);

    createQuestionTextView(linearLayout, questionData);
    createQuestionImage(linearLayout, questionData);
    createEditText(linearLayout, questionData);

    if (!isLastQuestion) {
      createQuestionDivider(linearLayout);
    }

    return(linearLayout);
  }

  //---------------------------------------------------------------
  // onTextChanged
  //---------------------------------------------------------------
  @Override
  public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
    if (m_callbacks != null) {
      m_callbacks.quizChoiceChanged(getQuestionId(), charSequence.toString());
    }
  }

  //---------------------------------------------------------------
  // setUserChoice
  //---------------------------------------------------------------
  @Override
  public void setUserChoice(Object userChoice) {
    if (userChoice != null) {
      m_editText.setText((String)userChoice);
    }
  }

  //===============================================================
  // private
  //===============================================================

  //---------------------------------------------------------------
  // createEditText
  //---------------------------------------------------------------
  private void createEditText(LinearLayout linearLayout, QuestionData questionData) {
    m_editText = new EditText(linearLayout.getContext());
    m_editText.setId(QuestionBuilder.EDIT_TEXT_TAG);
    m_editText.setTag(questionData.getCorrectChoiceStringIds());
    m_editText.setHint(R.string.edit_text_hint);
    m_editText.addTextChangedListener(this);

    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    params.leftMargin = params.rightMargin = 16;
    m_editText.setLayoutParams(params);

    linearLayout.addView(m_editText);
  }
}
