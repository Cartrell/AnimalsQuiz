package com.example.android.animalsquiz;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class RetainedFragment extends Fragment {
  //===============================================================
  // member variables
  //===============================================================

  //data to retain
  private ArrayList<QuestionData> m_questionsDataDb;
  private ArrayList<QuestionData> m_selectedQuestionsData;
  private UserChoicesData m_userChoicesData;

  //===============================================================
  // public
  //===============================================================

  //---------------------------------------------------------------
  // getSelectedQuestionsData
  //---------------------------------------------------------------
  public ArrayList<QuestionData> getSelectedQuestionsData() {
    return(m_selectedQuestionsData);
  }

  //---------------------------------------------------------------
  // getQuestionsDataDb
  //---------------------------------------------------------------
  public ArrayList<QuestionData> getQuestionsDataDb() {
    return(m_questionsDataDb);
  }

  //---------------------------------------------------------------
  // getUserChoicesData
  //---------------------------------------------------------------
  public UserChoicesData getUserChoicesData() {
    return(m_userChoicesData);
  }

  //---------------------------------------------------------------
  // onCreate
  //---------------------------------------------------------------
  // this method is only called once for this fragment
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //retain this fragment
    setRetainInstance(true);
  }

  //---------------------------------------------------------------
  // setSelectedQuestionsData
  //---------------------------------------------------------------
  public void setSelectedQuestionsData(ArrayList<QuestionData> questionData) {
    m_selectedQuestionsData = questionData;
  }

  //---------------------------------------------------------------
  // setQuestionsDataDb
  //---------------------------------------------------------------
  public void setQuestionsDataDb(ArrayList<QuestionData> questionsData) {
    m_questionsDataDb = questionsData;
  }

  //---------------------------------------------------------------
  // setUserChoicesData
  //---------------------------------------------------------------
  public void setUserChoicesData(UserChoicesData userChoicesData) {
    m_userChoicesData = userChoicesData;
  }
}
