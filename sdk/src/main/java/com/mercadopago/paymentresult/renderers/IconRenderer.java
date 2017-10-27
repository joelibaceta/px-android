package com.mercadopago.paymentresult.renderers;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.mercadopago.R;
import com.mercadopago.components.Renderer;
import com.mercadopago.paymentresult.components.IconComponent;
import com.mercadopago.util.CircleTransform;
import com.mercadopago.util.ScaleUtil;
import com.squareup.picasso.Picasso;

/**
 * Created by vaserber on 10/23/17.
 */

public class IconRenderer extends Renderer<IconComponent> {

    private View iconView;
    private ImageView iconImageView;
    private ImageView iconBadgeView;

    @Override
    public View render() {
        iconView = LayoutInflater.from(context).inflate(R.layout.mpsdk_icon, null, false);
        iconImageView = (ImageView) iconView.findViewById(R.id.mpsdkIconProduct);
        iconBadgeView = (ImageView) iconView.findViewById(R.id.mpsdkIconBadge);

        renderIcon();
        renderBadge();
        return iconView;
    }

    private void renderIcon() {
//        Drawable iconImage = ContextCompat.getDrawable(context, component.getProps().iconImage);
//        iconImageView.setImageDrawable(iconImage);

        int dimen = ScaleUtil.getPxFromDp(90, context);
        Picasso.with(context)
                .load(component.getProps().iconImage)
                .transform(new CircleTransform())
                .resize(dimen, dimen)
                .centerInside()
//                .placeholder(R.drawable.mpsdk_icon_default)
                .into(iconImageView);
    }

    private void renderBadge() {
        if (component.getProps().badgeImage == 0) {
            iconBadgeView.setVisibility(View.INVISIBLE);
        } else {
            Drawable badgeImage = ContextCompat.getDrawable(context, component.getProps().badgeImage);
            iconBadgeView.setImageDrawable(badgeImage);
            iconBadgeView.setVisibility(View.VISIBLE);
        }
    }

}
