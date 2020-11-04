package com.weekly.engineer.challenge.view;

import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.widget.ShareDialog;
import com.weekly.engineer.challenge.R;
import com.weekly.engineer.challenge.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomFacebookDialog extends DialogFragment {
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    Button share;
    LoginButton login;
    ProfilePictureView profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        View rootView = inflater.inflate(R.layout.fragment_facebook_dialog, container, false);
        callbackManager = CallbackManager.Factory.create();
        login = (LoginButton) rootView.findViewById(R.id.login_button);
        profile = (ProfilePictureView) rootView.findViewById(R.id.picture);
        share = (Button) rootView.findViewById(R.id.share);

        shareDialog = new ShareDialog(this);
        login.setReadPermissions("email,public_profile,user_friends,publish_actions");
        share.setVisibility(View.GONE);

        if (AccessToken.getCurrentAccessToken() != null) {
            RequestData();
            share.setVisibility(View.VISIBLE);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AccessToken.getCurrentAccessToken() != null) {
                    share.setVisibility(View.GONE);
                    profile.setProfileId(null);
                }
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                        .putString("og:type", "game")
                        .putString("og:title","Engineer Challenge")
                        .putString("og:image", MainActivity.app_icon)
                        .putString("og:description", "มาฝึกทำข้อสอบ กว. กัน")
                        .build();

                ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                        .setActionType("enginnerchallenge:play")
                        .putObject("game", object)
                        .build();


                ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
                        .setPreviewPropertyName("game")
                        .setAction(action)
                        .build();

                shareDialog.show(content);

            }
        });
        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if (AccessToken.getCurrentAccessToken() != null) {
                    RequestData();
                    share.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
            }
        });
        return rootView;
    }


    public void RequestData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();
                try {
                    if (json != null) {
                        String text = "<b>Name :</b> " + json.getString("name") + "<br><br><b>Email :</b> " + json.getString("email") + "<br><br><b>Profile link :</b> " + json.getString("link");
                        profile.setProfileId(json.getString("id"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    public void setShare() {
        if (share.getVisibility() != View.VISIBLE) {
            share.setVisibility(View.VISIBLE);
        }
    }
}
