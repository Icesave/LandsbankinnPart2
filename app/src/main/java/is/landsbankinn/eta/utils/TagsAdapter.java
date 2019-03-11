package is.landsbankinn.eta.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import is.landsbankinn.eta.R;

public class TagsAdapter extends RecyclerView.Adapter {

    private List<String> mReviewList;

    public TagsAdapter(Context context, List<String> reviewList) {
        mReviewList = reviewList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_list_item, parent, false);


        return new TagsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (mReviewList.size() != 0) {
            String tag = mReviewList.get(position);
            ((TagsViewHolder) holder).bind(tag);
        }
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }
}