package sang.com.minitoolsimple;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import sang.com.minitools.widget.CircularFramlayout;
import sang.com.minitools.widget.CircularImageView;
import sang.com.minitools.xadapter.XAdapter;
import sang.com.minitools.xadapter.holder.BaseHolder;
import sang.com.minitoolsimple.entity.CircularBean;

import static android.widget.ImageView.ScaleType.MATRIX;

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

    @BindView(R.id.img1)
    CircularFramlayout img1;
   @BindView(R.id.rv)
   RecyclerView rv;
    private XAdapter<CircularBean> adapter;

    private int borderColor=Color.RED;
    private int radio;
    private int borderWidth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular_image);
        ButterKnife.bind(this);
        initData();
        intiListener();
    }

    private void intiListener() {
        sbRadio.setOnSeekBarChangeListener(this);
        sbLeft.setOnSeekBarChangeListener(this);
        sbRight.setOnSeekBarChangeListener(this);
        cbBorder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                img1.setShowBorder(isChecked);
                switch (new Random().nextInt(3)) {
                    case 0:
                        borderColor=Color.RED;
                        img1.setBorderColor(Color.RED);

                        break;
                    case 1:
                        img1.setBorderColor(Color.BLUE);
                        borderColor=Color.BLUE;
                        break;
                    case 2:
                        borderColor=Color.GRAY;
                        img1.setBorderColor(Color.GRAY);

                        break;
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initData() {
        GridLayoutManager manager =new GridLayoutManager(this,3);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        List<CircularBean> beans =new ArrayList<>();
        beans.add(new CircularBean("FIT_XY",ImageView.ScaleType.FIT_XY));
        beans.add(new CircularBean("FIT_END",ImageView.ScaleType.FIT_END));

        beans.add(new CircularBean("CENTER",ImageView.ScaleType.CENTER));
        beans.add(new CircularBean("CENTER_CROP",ImageView.ScaleType.CENTER_CROP));
        beans.add(new CircularBean("CENTER_INSIDE",ImageView.ScaleType.CENTER_INSIDE));
        beans.add(new CircularBean("FIT_CENTER",ImageView.ScaleType.FIT_CENTER));


        adapter=new XAdapter<CircularBean>(this,beans) {
            @Override
            protected BaseHolder<CircularBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<CircularBean>(CircularImageActivity.this,parent,R.layout.item_cirlular){
                    @Override
                    public void initView(View itemView, int position, CircularBean data) {
                        super.initView(itemView, position, data);
                        TextView tvTilte = itemView.findViewById(R.id.tv_title);
                        tvTilte.setText(data.getTitle());
                        CircularImageView imageView =itemView.findViewById(R.id.img);
                        imageView.setImageResource(R.mipmap.a);
                        imageView.setScaleType(data.getScaleType());
                        imageView.setBorderColor(borderColor);
                        imageView.setShowBorder(cbBorder.isChecked());
                        imageView.setRadius(radio);
                        imageView.setBorderWidth(borderWidth);
                    }
                };
            }
        };
        rv.setAdapter(adapter);


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sb_radio:
                radio=progress;
                adapter.notifyDataSetChanged();
                img1.setRadius(progress);
                break;
            case R.id.sb_left:
                img1.setRadius(progress, 0, 0, 0);
                break;

            case R.id.sb_right:
                borderWidth=progress;
                img1.setBorderWidth(progress);
                adapter.notifyDataSetChanged();
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
