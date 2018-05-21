package sang.com.minitoolsimple;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import sang.com.minitools.CircularImageView;

public class CircularImageActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.cb_border)
    CheckBox cbBorder;
    @BindView(R.id.tv_radio)
    TextView tvRadio;
    @BindView(R.id.sb_radio)
    SeekBar sbRadio;
    @BindView(R.id.tv_lefttopradio)
    TextView tvLefttopradio;
    @BindView(R.id.sb_left)
    SeekBar sbLeft;
    @BindView(R.id.tv_border)
    TextView tvBorder;
    @BindView(R.id.sb_right)
    SeekBar sbRight;
    @BindView(R.id.img)
    CircularImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular_image);
        ButterKnife.bind(this);
        sbRadio.setOnSeekBarChangeListener(this);
        sbLeft.setOnSeekBarChangeListener(this);
        sbRight.setOnSeekBarChangeListener(this);
        cbBorder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                img.setShowBorder(isChecked);
                ;
                switch (new Random().nextInt(3)) {
                    case 0:
                        img.setBorderColor(Color.RED);

                        break;
                    case 1:
                        img.setBorderColor(Color.BLUE);

                        break;
                    case 2:
                        img.setBorderColor(Color.GRAY);

                        break;
                }
            }
        });
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sb_radio:
                img.setRadius(progress);
                break;
            case R.id.sb_left:
                img.setRadius(progress, 0, 0, 0);
                break;

            case R.id.sb_right:
                img.setBorderWidth(progress);
                break;
        }
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }


    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
