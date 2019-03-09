package is.landsbankinn.eta.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import is.landsbankinn.eta.R;
import is.landsbankinn.eta.models.Review;

public class ReviewAdapter extends RecyclerView.Adapter {

    private List<Review> mReviewList;

    public ReviewAdapter(Context context, List<Review> reviewList) {
        mReviewList = reviewList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_list_item, parent, false);


        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (mReviewList.size() != 0) {
            System.out.println("_________________________");
            System.out.println(position);
            System.out.println(holder.getAdapterPosition());
            Review review = mReviewList.get(position);
            ((ReviewViewHolder)holder).bind(review);
        }
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }
}