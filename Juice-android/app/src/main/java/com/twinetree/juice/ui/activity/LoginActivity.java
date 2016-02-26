package com.twinetree.juice.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.twinetree.juice.R;
import com.twinetree.juice.datasets.User;
import com.twinetree.juice.network.BasicRequest;
import com.twinetree.juice.util.GoogleUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final int RC_SIGN_IN = 0;
    private static final String TAG = "gergreg";

    private final static String GPLUS_SCOPE
            = "https://www.googleapis.com/auth/plus.login";
    private final static String mScopes
            = "oauth2:" + " " + GPLUS_SCOPE;
    int REQUEST_CODE_TOKEN_AUTH = 8;

    private CallbackManager callbackManager;
    private AccessTokenTracker facebookTokenTracker;
    private ProfileTracker facebookProfileTracker;
    private GoogleApiClient googleApiClient;
    private boolean intentInProgress, isSignInClicked;
    private ConnectionResult connectionResult;
    private boolean isFbLogin, isGoogleLogin;
    private SignInButton googleSignIn;
    private Button getToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();

        LoginButton loginButton = (LoginButton) findViewById(R.id.activity_login_facebook);
        googleSignIn = (SignInButton) findViewById(R.id.activity_login_google);
        getToken = (Button) findViewById(R.id.activity_login_auth_token);

        loginButton.setReadPermissions("email");

        facebookTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                isFbLogin = true;
                Toast.makeText(LoginActivity.this, "Facebook Access Token", Toast.LENGTH_SHORT).show();
            }
        };

        facebookProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                isFbLogin = (!isFbLogin);
                Toast.makeText(LoginActivity.this, "Facebook Current Profile", Toast.LENGTH_SHORT).show();
            }
        };

        facebookTokenTracker.startTracking();
        facebookProfileTracker.startTracking();
        googleSignIn.setOnClickListener(this);
        getToken.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (isFbLogin) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

        if (requestCode == REQUEST_CODE_TOKEN_AUTH && resultCode == RESULT_OK) {
            // We had to sign in - now we can finish off the token request.
            new GoogleAuthTokenTask().execute(Plus.AccountApi.getAccountName(googleApiClient));
        }

        if (requestCode == RC_SIGN_IN & isSignInClicked) {
            if (resultCode != RESULT_OK) {
                isSignInClicked = false;
            }

            intentInProgress = false;

            if (!googleApiClient.isConnecting()) {
                googleApiClient.connect();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        facebookTokenTracker.stopTracking();
        facebookProfileTracker.stopTracking();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        isSignInClicked = false;
        getProfileInformation();
        updateUi(true);
        //serialize();
    }

    private void serialize() {

        try {
            String fileName = "datari";
            SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.google_api_client), MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(getResources().getString(R.string.google_api_client_file), fileName);
            editor.commit();

            FileOutputStream fos = this.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = null;
            os = new ObjectOutputStream(fos);
            os.writeObject(this);
            os.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(googleApiClient) != null) {
                String accountName = Plus.AccountApi.getAccountName(googleApiClient);
                String avatarUrl = Plus.PeopleApi.getCurrentPerson(googleApiClient).getImage().getUrl();
                String id = Plus.PeopleApi.getCurrentPerson(googleApiClient).getId();
                //String coverUrl = Plus.PeopleApi.getCurrentPerson(googleApiClient).getCover().getCoverPhoto().getUrl();

                User user = new User(this);

                user.setAccountName(accountName);
                user.setAvatarUrl(avatarUrl);
                user.setGoogleId(id);

                getGoogleAuthToken();
            }
        } catch (Exception e) {
            Log.i("jbefb", e.getMessage());
            e.printStackTrace();
        }
        //Toast.makeText(LoginActivity.this, "Google SignIn Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
        updateUi(false);
    }

    private void updateUi(boolean b) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!intentInProgress) {
            connectionResult = result;
            if (isSignInClicked) {
                resolveSignInError();
            }
        }
    }

    private void resolveSignInError() {
        if (connectionResult.hasResolution()) {
            try {
                intentInProgress = true;
                connectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                intentInProgress = false;
                googleApiClient.connect();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_login_google:
                onSignInClicked();
                break;
            case R.id.activity_login_auth_token:
                getGoogleAuthToken();
                break;
        }
    }

    private void getGoogleAuthToken() {
        new GoogleAuthTokenTask().execute(Plus.AccountApi.getAccountName(googleApiClient));
    }

    private class GoogleAuthTokenTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String accountName = params[0];
            String scopes = "oauth2:profile email";
            String token = null;
            try {
                token = GoogleAuthUtil.getToken(getApplicationContext(), accountName, scopes);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            } catch (UserRecoverableAuthException e) {
                startActivityForResult(e.getIntent(), REQUEST_CODE_TOKEN_AUTH);
            } catch (GoogleAuthException e) {
                Log.e(TAG, e.getMessage());
            }
            return token;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            User.setAccessToken(s);
            User user = new User(LoginActivity.this);
            user.setUserLogin(true);
            BasicRequest.execute(LoginActivity.this);
            Log.i(TAG, s);
        }
    }

    private void onSignInClicked() {
        if (!googleApiClient.isConnecting()) {
            isSignInClicked = true;
            resolveSignInError();
        }
    }
}
