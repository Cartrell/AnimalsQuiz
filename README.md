# AnimalsQuiz
This is the quiz app assignment for Lesson 12 of the Grow with Google Challenge Scholarship: Android Basics. The topic of this quiz is Animals.

There the quiz randomly selects between 5 and 10 questions from a pool of 30, so each time you play the quiz, you'll get a different combination of questions.

## Quiz Questions
Questions use the following formats:

 1. Radio buttons: The correct answer is only one of the radio buttons.
 2. Check boxes: The correct answers are two or more check boxes.
 3. Edit text: The correct answer is a specific text. Some answers may have more then one acceptable text entry. For example, if the correct answer represents a number, say seven, both "seven" and "7" are acceptable.

Some questions also use an image, such as a picture to identify.

## How to Edit Questions
You can add new questions, or remove or edit existing ones. Most of this can be done in these two files:
1. `res/values/strings.xml` - This file contains the string resources for the question text and the choices.
2. `res/values/arrays.xml` - This file defines each quiz question. Each question is defined by a series of key/value pairs of data, specified in `<item>` tags. For example, specifying the number of cakes might be like this:

```
<item>numberOfCakes</item>
<item>3</item>
```

The first <item> represents the key, and the second `<item>` represents the value. In this case, we're specifying three cakes.

Each question uses the following keys:

  - `questionId` - A unique identifier for the question. Each question must have a unique one. This key is required for each question, and must be the first key of each question.
  - `questionTextStringId` - The string ID from the strings resource that will be used for the text of the question.
  - `questionType` - Specifies the type of question. Acceptable values are:
    - `radio` - The question will use radio buttons as the choices.
    - `check` - The question will use check boxes as the choices.
    - `edit` - The question will use an edit text to enter the response.
  - `questionImageId` - If this question uses an image to accompany the question text, this specifies the resource ID of the image. This is optional.
  - `correctChoices` - This specifies the number of correct choices, followed by a list of string resource IDs that represent the strings used for the radio or check buttons. If the question type is `edit`, this represents the responses that the question will accept as correct.
  - `incorrectChoices` - This specifies the number of incorrect choices, followed by a list of string resource IDs that represent the strings used for the radio or check buttons. This entry is used only for `radio` and `check` question types, and is not used in `edit` question types.

Here is an example of a question data that uses all the keys:

```
<item>questionId</item>
<item>someQuestionId</item>
```

```
<item>questionTextStringId</item>
<item>@string/someQuestionString</item>
```

```
<item>questionType</item>
<item>check</item>
```

```
<item>incorrectChoices</item>
<item>2</item>
<item>@string/someQuestionChoice1</item>
<item>@string/someQuestionChoice2</item>
```

```
<item>correctChoices</item>
<item>3</item>
<item>@string/someQuestionChoice3</item>
<item>@string/someQuestionChoice4</item>
<item>@string/someQuestionChoice5</item>
```

The log will output the IDs of question as it processes them. If the format of the questions data is invalid, the app will crash.  You can inspect the log to try and figure out which question data caused an app crash by looking at the last question that was successfully parsed. For a future exercise, I may have the app output more information, and  prevent the app from continuing without crashing, or perhaps try to skip the question, though the latter will definitely be a challenge for the parsing. (:

## Results
You can see current progress at any time by pressing the Grading button. A toast will appear at the bottom of the screen. If you want to get the "official" score and feedback, press the Submit button. This will take you to the results screen (activity). If you want to go back to the quiz and re-try any incorrect questions, use the Back button on your device. If you press Play Again, you will get a new quiz, and will not be able to go back.
