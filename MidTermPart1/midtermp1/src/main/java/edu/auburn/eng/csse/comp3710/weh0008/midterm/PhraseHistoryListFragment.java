package edu.auburn.eng.csse.comp3710.weh0008.midterm;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by William Hester on 3/3/14.
 * Email: WEH0008@auburn.edu
 */
public class PhraseHistoryListFragment extends ListFragment {

    public static final int ORIGINAL_FIRST = 0;
    public static final int REVERSED_FIRST = 1;
    public static final String LIST_ORDER = "listOrder";
    public static final String PHRASES = "phrases";

    private int mOrderType;
    private ArrayList<Phrase> mPhrases;
    private List<String> mPhraseStrings = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mOrderType = savedInstanceState.getInt(LIST_ORDER);
            mPhrases = savedInstanceState.getParcelableArrayList(PHRASES);
        } else if (getArguments() != null) {
            mOrderType = getArguments().getInt(LIST_ORDER);
            mPhrases = getArguments().getParcelableArrayList(PHRASES);
        }
        generateStringList();
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                mPhraseStrings));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(LIST_ORDER, mOrderType);
        outState.putParcelableArrayList(PHRASES, mPhrases);
        super.onSaveInstanceState(outState);
    }

    private void generateStringList() {
        if (mOrderType == ORIGINAL_FIRST) {
            Collections.sort(mPhrases, new Phrase.OriginalPhraseComparator());
            for (Phrase p : mPhrases) {
                if (p.getCount() == 1)
                    mPhraseStrings.add(p.getOriginal() + " -> " + p.getReversed());
                else
                    mPhraseStrings.add(p.getOriginal() + " -> " + p.getReversed() + " ("
                            + p.getCount() + ")");
            }
        } else {
            Collections.sort(mPhrases, new Phrase.ReversedPhraseComparator());
            for (Phrase p : mPhrases) {
                if (p.getCount() == 1)
                    mPhraseStrings.add(p.getReversed() + " <- " + p.getOriginal());
                else
                    mPhraseStrings.add(p.getReversed() + " <- " + p.getOriginal() + " ("
                            + p.getCount() + ")");
            }
        }
    }

}
