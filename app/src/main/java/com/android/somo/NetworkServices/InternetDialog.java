package com.android.somo.NetworkServices;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.android.somo.R;

public class InternetDialog extends DialogFragment {

    private ConnectivityManager.NetworkCallback networkCallback;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //connection lost dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.no_internet_dialog, null);
        builder.setView(view);

        //create dialog
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);

        return  dialog;

    }

    @Override
    public void onStart() {
        super.onStart();
        //register network callback to listen to internet connection changes
        ConnectivityManager connectivityManager = (ConnectivityManager)requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest.Builder networkRequestBuilder = new NetworkRequest.Builder();
        networkCallback = new ConnectivityManager.NetworkCallback(){
            @Override
            public void onAvailable(@NonNull Network network) {
                //Internet connection is available
                dismiss();
            }
            @Override
            public void onLost(@NonNull Network network) {
                //internet connection is lost
                show(requireActivity().getSupportFragmentManager(), "internet_dialog");
            }
        };
        connectivityManager.registerNetworkCallback(networkRequestBuilder.build(), networkCallback);
    }

    @Override
    public void onStop() {
        super.onStop();
        //unregister network callback
        ConnectivityManager connectivityManager = (ConnectivityManager)requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }
}
