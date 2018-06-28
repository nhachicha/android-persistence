package com.example.android.persistence.codelab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

public class LoginActivity extends AppCompatActivity {

    private static final String INSTANCE_ADDRESS = "";// REPLACE WITH REALM CLOUD URL ex foo.us1a.cloud.realm.io
    private static final String AUTH_URL = "https://" + INSTANCE_ADDRESS + "/auth";
    private static final String BOOKS_REALM_URL = "realms://" + INSTANCE_ADDRESS + "/~/books";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SyncUser currentUser = SyncUser.current();

        if (currentUser == null) {
            // show progress bar
            Toast.makeText(LoginActivity.this, "Login ...", Toast.LENGTH_SHORT).show();
            // logs the user automatically using anonymous user
            SyncCredentials anonymousCredential = SyncCredentials.anonymous();
            SyncUser.logInAsync(anonymousCredential, AUTH_URL, new SyncUser.Callback<SyncUser>() {
                @Override
                public void onSuccess(SyncUser syncUser) {
                    createDefaultConfigurationAndNavigateToNextActivity(syncUser);
                }

                @Override
                public void onError(ObjectServerError error) {
                    Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            });

        } else {
            createDefaultConfigurationAndNavigateToNextActivity(currentUser);
        }
    }

    private void createDefaultConfigurationAndNavigateToNextActivity(SyncUser syncUser) {
        Realm.setDefaultConfiguration(
                syncUser.createConfiguration(BOOKS_REALM_URL).fullSynchronization().build());
        // Realm ready, navigate to next Activity
        Intent intent = new Intent(LoginActivity.this, CustomResultUserActivity.class);
        startActivity(intent);
    }
}
