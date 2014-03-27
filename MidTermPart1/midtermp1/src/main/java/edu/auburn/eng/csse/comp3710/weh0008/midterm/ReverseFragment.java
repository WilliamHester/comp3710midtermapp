package edu.auburn.eng.csse.comp3710.weh0008.midterm;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by William on 3/3/14.
 */
public class ReverseFragment extends Fragment {

    private ReversedListener mCallback;
    private String mReversed;
    private String mOriginal;

    private static final String REVERSED_TEXT = "reversedText";
    private static final String ENTERED_TEXT = "enteredText";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mReversed = savedInstanceState.getString(REVERSED_TEXT);
            mOriginal = savedInstanceState.getString(ENTERED_TEXT);
        }

        try {
            mCallback = (ReversedListener) getActivity();
        } catch (ClassCastException e) {
            Log.e("ReverseFragment", "Callback could not be cast to ReversedListener."
                    + "\nHosting activity must implement ReversedListener.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, null);
        final EditText enteredText = (EditText) v.findViewById(R.id.entered_text);
        Button reverseText = (Button) v.findViewById(R.id.reverse_me);
        final TextView reversedText = (TextView) v.findViewById(R.id.reversed_word);
        Button historyOriginal = (Button) v.findViewById(R.id.history_original);
        Button historyReversed = (Button) v.findViewById(R.id.history_reversed);

        reverseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (enteredText.getText().toString().length() > 0) {
                    String text = enteredText.getText().toString().trim();
                    Phrase p = new Phrase(text);
                    mCallback.onWordReversed(p);
                    reversedText.setText(p.getReversed());
                    mReversed = p.getReversed();
                    mOriginal = p.getOriginal();
                }
            }
        });

        historyOriginal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onHistoryOriginalPressed();
            }
        });

        historyReversed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onHistoryReversedPressed();
            }
        });

        if (savedInstanceState != null) {
            reversedText.setText(mReversed);
            enteredText.setText(mOriginal);
        } else if (mReversed != null) {
            reversedText.setText(mReversed);
            enteredText.setText(mOriginal);
        }

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mOriginal != null)
            outState.putString(ENTERED_TEXT, mOriginal);
        if (mReversed != null)
            outState.putString(REVERSED_TEXT, mReversed);

        super.onSaveInstanceState(outState);
    }

    public interface ReversedListener {

        /**
         * Passes the reversed word to the hosting activity.
         *
         * @param p the phrase that is input
         */
        public void onWordReversed(Phrase p);

        /**
         * Is invoked when the history_original button is pressed
         */
        public void onHistoryOriginalPressed();

        /**
         * Is invoked when the history_reversed button is pressed
         */
        public void onHistoryReversedPressed();
    }

}
