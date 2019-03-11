package is.landsbankinn.eta.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import is.landsbankinn.eta.R;
import is.landsbankinn.eta.models.Review;

class ReviewViewHolder extends RecyclerView.ViewHolder {

    TextView mReviewUserName;
    TextView mDescription;
    TextView mRating;
    public ReviewViewHolder(View itemView) {
        super(itemView);
        mReviewUserName = itemView.findViewById(R.id.review_username);

        mRating = itemView.findViewById(R.id.review_grading);

        mDescription = itemView.findViewById(R.id.review_list_text);
    }

    void bind(Review review) {
        mReviewUserName.setText(review.getUsername());


        mRating.setText(String.valueOf(review.getRating()));

        mDescription.setText(review.getText());
    }
}


