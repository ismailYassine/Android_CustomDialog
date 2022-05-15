package com.example.trafficfinesapp_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DialogForm.ItemClickListener {

    List<Offender> offenderList;

    int positionClickedItemRecyclerView = 0;

    Boolean isSchoolOrConstructionZone;

    CustomAdapter customAdapter;

    int spinnerSelectionPosition;
    String spinnerSelectionValue;

    FloatingActionButton addButton;
    TextInputLayout firstName;
    TextInputLayout lastName;
    Spinner spinner;
    RadioButton radioButton;
    TextInputLayout speedTextInput;
    TextInputLayout fineAmountTextInput;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));

        TextInputEditText textInputEditText = findViewById(R.id.speedText);

        offenderList = new ArrayList<>();

        addButton = findViewById(R.id.addButton);

        firstName = findViewById(R.id.textInputLastName);
        lastName = findViewById(R.id.textInputFirstName);
        spinner = findViewById(R.id.spinner);
        radioButton = findViewById(R.id.radioButton);
        speedTextInput = findViewById(R.id.textInputSpeed);
        fineAmountTextInput = findViewById(R.id.textInputFinedAmount);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        customAdapter = new CustomAdapter(offenderList, new CustomAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Offender offender) {
               positionClickedItemRecyclerView = offenderList.indexOf(offender);
                fillInputs(offender);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(customAdapter);

        ArrayAdapter<CharSequence> charSequenceArrayAdapter = ArrayAdapter.createFromResource(this, R.array.fineTypes, android.R.layout.simple_spinner_item);
        charSequenceArrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(charSequenceArrayAdapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setDropDownWidth(500);

        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isSchoolOrConstructionZone = b;
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new DialogForm();
                dialog.show(getSupportFragmentManager(), "DialogForm");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            //Back button
            case R.id.btnAdd:

                String lastNameEntered = firstName.getEditText().getText().toString().trim();
                String firstNameEntered = lastName.getEditText().getText().toString().trim();

                int speedLimitZoneNoted = parsStringToIn(spinnerSelectionValue);;

                int offenderSpeedInt = parsStringToIn(speedTextInput.getEditText().getText().toString().trim());

                Boolean isSchoolOrConstructionZoneNoted = isSchoolOrConstructionZone;

                String fineDate = date();

                int fineAmount = fineAmount(offenderSpeedInt, speedLimitZoneNoted);

                if(spinnerSelectionPosition == 0){
                    Toast.makeText(getApplicationContext(), "Veillez selectionner un zone de limite", Toast.LENGTH_LONG).show();
                }else if(speedLimitZoneNoted > offenderSpeedInt)  {
                    showAlertDialog();
                }else {

                    addOffender(lastNameEntered, firstNameEntered, fineAmount, spinnerSelectionPosition, offenderSpeedInt, isSchoolOrConstructionZoneNoted, fineDate);
                    fineAmountTextInput.getEditText().setText(String.valueOf(fineAmount));
                    clearInputs();

                }

//                if (lastNameEntered.isEmpty()) {
////                    lastName.setEndIconDrawable(getDrawable(R.drawable.ic_error_24));
//                    lastName.setHelperText("Entrer un nom valide");
//
//                } else {
//                    if(firstNameEntered.isEmpty()){
//                        firstName.setHelperText("Entrer un prenom valide");
//                    }
//
////                    addOffender(lastNameEntered, firstNameEntered, fineAmount, speedLimitZoneNoted, offenderSpeedInt, isSchoolOrConstructionZoneNoted, formattedDate);
//                }

                return true;
            case R.id.btnClear:
                clearInputs();
                return true;

            case R.id.btnDelete:
                deleteEntry();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spinnerSelectionPosition = i;
        spinnerSelectionValue = spinner.getSelectedItem().toString();
//        Toast.makeText(getApplicationContext(), spinnerSelectionValue, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private void addOffender(
            String lastName,
            String firstName,
            Integer finedAmount,
            Integer speedLimitZone,
            Integer offenderSpeed,
            Boolean isSchoolOrConstructionZoneNoted,
            String date) {

        Offender offender = new Offender(lastName, firstName, finedAmount, speedLimitZone, offenderSpeed, isSchoolOrConstructionZoneNoted, date);
        offenderList.add(0, offender);
        customAdapter.notifyItemInserted(0);
        recyclerView.getLayoutManager().scrollToPosition(0);

    }

    public String date(){
        Date fineDate = new Date();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public int parsStringToIn(String valueToPars){
        int intValue = 0;
        try {
            intValue = Integer.parseInt(valueToPars);
        } catch (NumberFormatException e) {
            //Log it if needed
        }
        return intValue;
    }

    public int fineAmount(Integer offenderSpeed, Integer speedLimitZone){

        int[] speedDiff2    = {4, 9, 10, 14, 19, 20, 24, 29, 30, 34, 39, 44, 45, 49};
        int[] myArrayFine2  = {15, 25, 35, 35, 45, 55, 75, 90, 105, 135, 155, 175, 195, 240};
        int fineAmount = 0;

        for(int i = 0; i <= myArrayFine2.length; i++){
            if (offenderSpeed <= (speedLimitZone + speedDiff2[i])){
                fineAmount = myArrayFine2[i];
                break;
            }
        }
        return fineAmount;
    }

    @Override
    public void onItemClick(Offender offender) {

        positionClickedItemRecyclerView = offenderList.indexOf(offender);
        Toast.makeText(getApplicationContext(), offender.firstName +
                "", Toast.LENGTH_LONG).show();
    }

    public void clearInputs(){

        lastName.getEditText().setText("");
        firstName.getEditText().setText("");
        spinner.setSelection(0);
        radioButton.setChecked(false);
        speedTextInput.getEditText().setText("");
        fineAmountTextInput.getEditText().setText("");
    }

    public void deleteEntry(){
        clearInputs();
        offenderList.remove(positionClickedItemRecyclerView);
        customAdapter.notifyItemRemoved(positionClickedItemRecyclerView);
    }

    public void fillInputs(Offender offender){
        firstName.getEditText().setText(offender.firstName);
        lastName.getEditText().setText(offender.lastName);
        spinner.setSelection(offender.speedLimitZone);
//        radioButton.setChecked(offender.isSchoolOrWorkZone);
//        speedTextInput.getEditText().setText(offender.offenderSpeed);
//        fineAmountTextInput.getEditText().setText(offender.fineAmount);
    }



    public void showAlertDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Vitesse");
        alertDialog.setMessage("La vitesse est plus petite que la vitesse permise");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


}