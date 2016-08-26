package ixat.ixat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.grantland.widget.AutofitTextView;

/**
 * Created by hp on 2016-06-23.
 */
public class GamesAdapter extends BaseAdapter {

    private Context context;
    private ImageView imageView;
    private AutofitTextView textView;
    private LayoutInflater layoutInflater;
    private List<Games> game;

    public GamesAdapter(Context context, List<Games> game)
    {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.game = game;
    }

    @Override
    public int getCount() {
        return game.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View unRange = (View)layoutInflater.inflate(R.layout.jeux_list,null);

        imageView = (ImageView) unRange.findViewById(R.id.imageView2);
        textView = (AutofitTextView) unRange.findViewById(R.id.textView);

        imageView.setImageDrawable(game.get(position).getIcon());
        textView.setText(game.get(position).getGameDescription());


        return unRange;
    }
}
