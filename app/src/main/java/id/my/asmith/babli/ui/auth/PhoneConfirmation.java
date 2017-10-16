package id.my.asmith.babli.ui.auth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.my.asmith.babli.R;

/**
 * Created by A. A. Sumitro on 10/17/2017.
 * aasumitro@gmail.com
 * https://asmith.my.id/
 */

public class PhoneConfirmation extends AppCompatActivity {

    @BindView(R.id.txt_phone_confirmation_footer_caption) TextView wrongNumber;
    @BindView(R.id.txt_phone_confirmation_footer_notreceivesms) TextView notReceiveSMS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_confirmation);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Verification number");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        //Wrong number caption
        String captionWrong = "Enter a wrong number? <b>tap here</b>";
        SpannableStringBuilder spannableStringBuilderWrong = new SpannableStringBuilder(Html.fromHtml(captionWrong));
        spannableStringBuilderWrong.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                //action
            }
        }, captionWrong.indexOf("tap here") - 3, spannableStringBuilderWrong.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilderWrong.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), captionWrong
                .indexOf("tap here") - 3, spannableStringBuilderWrong.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        wrongNumber.setText(spannableStringBuilderWrong);
        wrongNumber.setMovementMethod(LinkMovementMethod.getInstance());

        //Didn't receive caption
        String captionReceive = "Didn't receive an sms message? <b>tap here</b>";
        SpannableStringBuilder spannableStringBuilderReceive = new SpannableStringBuilder(Html.fromHtml(captionReceive));
        spannableStringBuilderReceive.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                //action
            }
        }, captionReceive.indexOf("tap here") - 3, spannableStringBuilderReceive.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilderReceive.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), captionReceive
                .indexOf("tap here") - 3, spannableStringBuilderReceive.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        notReceiveSMS.setText(spannableStringBuilderReceive);
        notReceiveSMS.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_done) {
            Toast.makeText(PhoneConfirmation.this, "Confirmation phone number",
                    Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
