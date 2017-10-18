package id.my.asmith.babli.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.my.asmith.babli.R;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.txt_auth_register_cplogin) TextView loginCaption;
    @BindView(R.id.spn_auth_register_spcode) CountryCodePicker codeNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Join with Babli");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24px);
        setSupportActionBar(toolbar);

        //Login caption
        String captionLogin = "Already a member? <b>Sing in</b>";
        SpannableStringBuilder spannableStringBuilderLogin = new SpannableStringBuilder(Html.fromHtml(captionLogin));
        spannableStringBuilderLogin.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                RegisterActivity.this.overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        }, captionLogin.indexOf("Sing in") - 3, spannableStringBuilderLogin.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilderLogin.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), captionLogin
                .indexOf("Sing in") - 3, spannableStringBuilderLogin.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        loginCaption.setText(spannableStringBuilderLogin);
        loginCaption.setMovementMethod(LinkMovementMethod.getInstance());

        Log.d("ASD", codeNumber.getSelectedCountryCodeWithPlus()+codeNumber.getSelectedCountryNameCode()
                +codeNumber.getSelectedCountryName()+codeNumber.getSelectedCountryCode());
    }
}
