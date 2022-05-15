package com.example.trafficfinesapp_android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DetailsDialog extends DialogFragment {

    ItemClickListenerDetailsDialog listener;

    Offender offender;

    TextView date;
    TextView firstName;
    TextView lastName;
    TextView offenderSpeed;
    TextView speedLimit;
    TextView finedAmount;

    Button deleteButton;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ItemClickListenerDetailsDialog) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Implement CustomDialogListener");
        }
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        Offender offender = bundle.getParcelable("offender");

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = inflater.inflate(R.layout.dialog_show_details, null, false);

        date = view.findViewById(R.id.dateTvDetailsScreen);
        firstName = view.findViewById(R.id.firsNameTvDetailsScreenDialog);
        lastName = view.findViewById(R.id.lastNameTvDetailsScreen);
        offenderSpeed = view.findViewById(R.id.speedTvDetailsScreenDialog);
        speedLimit = view.findViewById(R.id.speedLimitZoneTvDetailsScreenDialog);
        finedAmount = view.findViewById(R.id.findedAmountTvDetailsScreenDialog);

        Button deleteButton = view.findViewById(R.id.btnDeleteEntry);

        date.setText(offender.fineDate);
        firstName.setText(offender.firstName);
        lastName.setText(offender.lastName);
        offenderSpeed.setText(String.valueOf(offender.offenderSpeed));
        speedLimit.setText(String.valueOf(offender.speedLimitZone));
        finedAmount.setText(String.valueOf(offender.fineAmount)+"$");

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClickDetails(offender);

//                Toast.makeText(getActivity(), "Cancel button pressed" +
//                        "", Toast.LENGTH_LONG).show();
//
//                dismiss();
            }
        });

        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        return alertDialog;
    }



    public interface ItemClickListenerDetailsDialog { void onItemClickDetails(Offender offender); }


}
