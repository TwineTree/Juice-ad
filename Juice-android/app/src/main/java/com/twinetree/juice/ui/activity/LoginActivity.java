package com.twinetree.juice.ui.activity;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.twinetree.juice.R;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final int RC_SIGN_IN = 0;

    private CallbackManager callbackManager;
    private AccessTokenTracker facebookTokenTracker;
    private ProfileTracker facebookProfileTracker;
    private GoogleApiClient googleApiClient;
    private boolean intentInProgress, isSignInClicked;
    private ConnectionResult connectionResult;
    private boolean isFbLogin, isGoogleLogin;
    private SignInButton googleSignIn;

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
    }

    private void getProfileInformation() {
        Toast.makeText(LoginActivity.this, "Google SignIn Success", Toast.LENGTH_SHORT).show();
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
        }
    }

    private void onSignInClicked() {
        if (!googleApiClient.isConnecting()) {
            isSignInClicked = true;
            resolveSignInError();
        }
    }
}
