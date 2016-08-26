package ixat.ixat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hp on 2016-06-23.
 */
public class AppsAdapter extends BaseAdapter {

    private Context context;
    private ImageView imageView;
    private CheckBox checkBox;
    private LayoutInflater layoutInflater;
    private List<ApplicationInfo> apps;

    public AppsAdapter(Context context, List<ApplicationInfo> apps)
    {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.apps = apps;
    }

    @Override
    public int getCount() {
        return apps.size();
    }

    @Override
    public ApplicationInfo getItem(int position) {
        return apps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View unRange = (View)layoutInflater.inflate(R.layout.list_choisir_jeu,null);

        imageView = (ImageView) unRange.findViewById(R.id.imageView2);
       // checkBox = (CheckBox) unRange.findViewById(R.id.checkBox);

        ApplicationInfo appinfo = apps.get(position);
        String desc = this.context.getPackageManager().getApplicationLabel(appinfo).toString();
        Drawable ico = this.context.getPackageManager().getApplicationIcon(appinfo);

        imageView.setImageDrawable(ico);
        checkBox.setText(desc);


        return unRange;
    }
}
