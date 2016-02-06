package project1_gtftw.gtftwgetthinforthewin;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.List;
import java.util.Arrays;
import android.widget.Toast;


public class workoutConverter extends AppCompatActivity {

    Spinner workoutSpinner;

    EditText workoutInfo;
    EditText inputtedWeight;
    TextView answer;

    Button converterButton;
    Button reset;

    RadioGroup unitSelector;
    List<String> quoteList = Arrays.asList("Whatever the mind of man can conceive and believe, it can achieve. –Napoleon Hill", "Strive not to be a success, but rather to be of value. –Albert Einstein", "Two roads diverged in a wood, and I—I took the one less traveled by, And that has made all the difference.  –Robert Frost", "I attribute my success to this: I never gave or took any excuse. –Florence Nightingale", "You miss 100% of the shots you don’t take. –Wayne Gretzky", "The most difficult thing is the decision to act, the rest is merely tenacity. –Amelia Earhart", "Every strike brings me closer to the next home run. –Babe Ruth", "Definiteness of purpose is the starting point of all achievement. –W. Clement Stone", "Just Do It -Nike", "While you're slacking, someone is at Mainstacks working their butt off -Ryan Kapur", "Eighty percent of success is showing up. –Woody Allen", "Either you run the day, or the day runs you. –Jim Rohn");
    double global_calories;
    int workoutVal = -1;
    int weight;
    String units = " ";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_converter);


        this.workoutSpinner = (Spinner) findViewById(R.id.workout_selector);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.workout_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workoutSpinner.setAdapter(adapter);

        this.answer = (TextView) findViewById(R.id.answer);
        this.converterButton = (Button) findViewById(R.id.converter);

        this.workoutInfo = (EditText) findViewById(R.id.workout_reps);
        this.inputtedWeight = (EditText) findViewById(R.id.weight);


        //listen to reset button
        this.reset = (Button) findViewById(R.id.reset);
        this.reset.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(i);
            }
        });

        this.converterButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {


                String workoutType = workoutSpinner.getSelectedItem().toString();
                unitSelector = (RadioGroup) findViewById(R.id.unit_selector);

                //getting the selected type of units
                int unitID = unitSelector.getCheckedRadioButtonId();
                RadioButton selectedUnit = (RadioButton) findViewById(unitID);
                units = selectedUnit.getText().toString();

                //calculate inputted # of workout units
                int workoutVal = 0;
                String temp_val = workoutInfo.getText().toString();
                workoutVal = Integer.parseInt(temp_val);

                //calculated inputted weight
                int weightVal = 0;
                String temp_weight = inputtedWeight.getText().toString();
                weight = Integer.parseInt(temp_weight);

                //calculate and print the calories
                answer.setText(calculateCalories(workoutType, workoutVal, units, weight).toString());

                if (answer.getText().equals("Error: work units mismatch")) {
                    answer.setText("Invalid units for specified workout");
                } else {
                    StringBuilder allInfo = new StringBuilder();
                    String calculated_calories = calculateCalories(workoutType, workoutVal, units, weight);
                    allInfo.append(calculated_calories); //main info
                    allInfo.append("\n\n");
                    allInfo.append(calcEquivalence(global_calories, "Pushups", 350, "reps"));
                    allInfo.append("\n");
                    allInfo.append(calcEquivalence(global_calories, "Situps", 200, "reps"));
                    allInfo.append("\n");
                    allInfo.append(calcEquivalence(global_calories, "Squats", 225, "reps"));
                    allInfo.append("\n");
                    allInfo.append(calcEquivalence(global_calories, "Pullups", 100, "reps"));
                    allInfo.append("\n"); allInfo.append("\n");
                    allInfo.append(calcEquivalence(global_calories, "Leg-Lifts", 25, "min"));
                    allInfo.append("\n");
                    allInfo.append(calcEquivalence(global_calories, "Planks", 25, "min"));
                    allInfo.append("\n");
                    allInfo.append(calcEquivalence(global_calories, "Jumping-Jacks", 10, "min"));
                    allInfo.append("\n");
                    allInfo.append(calcEquivalence(global_calories, "Cycling", 12, "min"));
                    allInfo.append("\n");
                    allInfo.append(calcEquivalence(global_calories, "Walking", 20, "min"));
                    allInfo.append("\n");
                    allInfo.append(calcEquivalence(global_calories, "Jogging", 12, "min"));
                    allInfo.append("\n");
                    allInfo.append(calcEquivalence(global_calories, "Swimming", 13, "min"));
                    allInfo.append("\n");
                    allInfo.append(calcEquivalence(global_calories, "Stair-Climbing", 15, "min"));
                    answer.setText(allInfo);
                }
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    protected Double calculator( int workoutVal, int conversionF, int weight){
        double basic_calories = (((double) workoutVal * 100.0) / conversionF);
        double weight_multipler = 1 + (.07148571 * (((double)(weight - 150)) / 10));
        double main_ret = basic_calories * weight_multipler;
        String temp = String.format("%1.2f", main_ret);
        main_ret = Double.valueOf(temp);
        return main_ret;
    }

    protected String calcEquivalence(double calories, String workoutType, int conversionFactor, String units){
        double mult = ((double) conversionFactor*1.0) / 100;
        double equivalentWorkUnits = calories * mult;
        String StrWorkUnits = String.format("%1.2f", equivalentWorkUnits);
        String equivalentString = "To burn " + String.valueOf(calories) + " Calories: " + StrWorkUnits + " " + units + " of " + workoutType;
        return equivalentString;
    }

    //Calculate and and return a string with the calories
    protected String calculateCalories(String workoutType, int workoutVal, String units, int weight) {
        int conversionFactor = -1;

        if (workoutType.equals("Pushups") && units.equals("reps")) {
            conversionFactor = 350;
        } else if (workoutType.equals("Situps") && units.equals("reps")) {
            conversionFactor = 200;
        } else if (workoutType.equals("Squats") && units.equals("reps")) {
            conversionFactor = 225;
        } else if (workoutType.equals("Leg-Lifts") && units.equals("min")) {
            conversionFactor = 25;
        } else if (workoutType.equals("Plank") && units.equals("min")) {
            conversionFactor = 25;
        } else if (workoutType.equals("Jumping-Jacks") && units.equals("min")) {
            conversionFactor = 10;
        } else if (workoutType.equals("Pullups") && units.equals("reps")) {
            conversionFactor = 100;
        } else if (workoutType.equals("Cycling") && units.equals("min")) {
            conversionFactor = 12;
        } else if (workoutType.equals("Walking") && units.equals("min")) {
            conversionFactor = 20;
        } else if (workoutType.equals("Jogging") && units.equals("min")) {
            conversionFactor = 12;
        } else if (workoutType.equals("Swimming") && units.equals("min")) {
            conversionFactor = 13;
        } else if (workoutType.equals("Stair-Climbing") && units.equals("min")) {
            conversionFactor = 15;
        } else if (conversionFactor == -1) {
            return "Error: work units mismatch";
        }

        //calculate for the input
        Double calories =  calculator(workoutVal, conversionFactor, weight);
        global_calories = calories;
        String mainRet = workoutVal + " " + units + " of " + workoutType + " burns " + String.valueOf(calories) + " Calories";
        return mainRet.toString();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "workoutConverter Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://project1_gtftw.gtftwgetthinforthewin/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "workoutConverter Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://project1_gtftw.gtftwgetthinforthewin/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
