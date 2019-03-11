package is.landsbankinn.eta.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import is.landsbankinn.eta.R;

public class TagsViewHolder extends RecyclerView.ViewHolder {

    TextView mTagText;

    public TagsViewHolder(View itemView) {
        super(itemView);
        mTagText = itemView.findViewById(R.id.tag_text);
    }

    void bind(String tag) {
        mTagText.setText(tag);
    }
}