package sang.com.minitoolsimple.entity;

import android.widget.ImageView;

/**
 * 作者： ${PING} on 2018/6/11.
 */

public class CircularBean {

    private String title="";
    private ImageView.ScaleType scaleType;

    public CircularBean(String title, ImageView.ScaleType scaleType) {
        this.title = title;
        this.scaleType = scaleType;
    }

    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
