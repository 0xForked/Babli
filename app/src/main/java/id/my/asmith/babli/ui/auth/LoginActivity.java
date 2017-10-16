package id.my.asmith.babli.ui.auth;

import android.accounts.AccountManager;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.my.asmith.babli.R;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.txt_auth_login_cpregister) TextView registerCaption;
    @BindView(R.id.txt_auth_login_cgpassword) TextView passwordChange;
    @BindView(R.id.et_auth_login_email) EditText emailLogin;

    private static final int REQUEST_CODE_EMAIL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Login to Babli");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        //Forgot password caption
        String captionPass = "<b>Forgot password?</b>";
        SpannableStringBuilder spannableStringBuilderForgot = new SpannableStringBuilder(Html.fromHtml(captionPass));
        spannableStringBuilderForgot.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        }, captionPass.indexOf("Forgot password?") - 3, spannableStringBuilderForgot.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilderForgot.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), captionPass
                .indexOf("Forgot password?") - 3, spannableStringBuilderForgot.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        passwordChange.setText(spannableStringBuilderForgot);
        passwordChange.setMovementMethod(LinkMovementMethod.getInstance());

        //Register caption
        String captionRegis = "Don't have an account? <b>Sing up</b>";
        SpannableStringBuilder spannableStringBuilderRegister = new SpannableStringBuilder(Html.fromHtml(captionRegis));
        spannableStringBuilderRegister.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                LoginActivity.this.overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        }, captionRegis.indexOf("Sing up") - 3, spannableStringBuilderRegister.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilderRegister.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), captionRegis
                .indexOf("Sing up") - 3, spannableStringBuilderRegister.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        registerCaption.setText(spannableStringBuilderRegister);
        registerCaption.setMovementMethod(LinkMovementMethod.getInstance());

        try {
            Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                    new String[] { GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE }, false, null, null, null, null);
            startActivityForResult(intent, REQUEST_CODE_EMAIL);
        } catch (ActivityNotFoundException e) {
            // TODO
            Log.e("email", "error get user email");
        }

    }

    //Result get email from user phone
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_EMAIL && resultCode == RESULT_OK) {

            //get user email
            final String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            //init alert dialog
            final AlertDialog.Builder alertDialogBuilder =
                    new AlertDialog.Builder(LoginActivity.this);
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setTitle("Use this email!");
            alertDialog.setIcon(R.mipmap.ic_launcher);

            TextView email = new TextView(LoginActivity.this);
            email.setTextSize(25);
            email.setTypeface(null, Typeface.BOLD);
            email.setClickable(true);
            email.setText(accountName);
            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    emailLogin.setText(accountName);
                    alertDialog.dismiss();
                }
            });
            // set view email
            alertDialog.setView(email, 90, 90, 90, 90);
            // show alert
            alertDialog.show();
        }
    }

    //function change user password
    void changePassword() {
        final AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(LoginActivity.this);
        alertDialogBuilder.setTitle("Enter your registered email address!");
        alertDialogBuilder.setIcon(R.mipmap.ic_launcher);
        //Alert dialog will not Cancelable
        alertDialogBuilder.setCancelable(false);
        //Showing EditText in alertDialog
        final EditText inPwd = new EditText(LoginActivity.this);
        alertDialogBuilder.setView(inPwd, 60, 60, 60, 0);
        inPwd.setImeOptions(EditorInfo.IME_ACTION_DONE);
        inPwd.setSingleLine(true);
        // set positive button: Send
        alertDialogBuilder.setPositiveButton("Send",new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(), "Change Password",
                        Toast.LENGTH_LONG).show();
            }
        });
        // set negative button: Cancel
        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                // cancel the alert box and put a Toast to the user
                dialog.cancel();
                // notify to user hes press the cancel button
                Toast.makeText(getApplicationContext(), "Canceled",
                        Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }

}
