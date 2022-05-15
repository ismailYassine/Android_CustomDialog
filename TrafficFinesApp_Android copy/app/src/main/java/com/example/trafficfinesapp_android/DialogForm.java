package com.example.trafficfinesapp_android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Locale;

public class DialogForm extends DialogFragment {

    ItemClickListener listener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ItemClickListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString() + "Implemet CustomDialogListener");
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = inflater.inflate(R.layout.dialog_form, null, false);
        Button btnSignIn = view.findViewById(R.id.btnSignIn);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        TextView firtName = view.findViewById(R.id.etFirstName);
        TextView lastName = view.findViewById(R.id.etLastName);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstNameInserted = firtName.getText().toString().toLowerCase(Locale.ROOT).trim();
                String lastNameInserted = lastName.getText().toString().toLowerCase(Locale.ROOT).trim();

                Offender offender = new Offender("firstName", "Last name", 35, 60, 64, true, "6 May 2022");
                listener.onItemClick(offender);
                dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Cancel button pressed" +
                        "", Toast.LENGTH_LONG).show();
                dismiss();
            }
        });
        builder.setView(view);
        return builder.create();
    }

    public interface ItemClickListener{
        void onItemClick(Offender offender);
    }

}
