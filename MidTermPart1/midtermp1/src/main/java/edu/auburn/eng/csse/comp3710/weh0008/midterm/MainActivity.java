package edu.auburn.eng.csse.comp3710.weh0008.midterm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements ReverseFragment.ReversedListener {

    private static final String REVERSE_FRAGMENT_TAG = "reverseFragment";
    private static final String ORIGINAL_HISTORY_FRAGMENT_TAG = "originalHistoryFragment";
    private static final String REVERSE_HISTORY_FRAGMENT_TAG = "reverseHistoryFragment";

    private String mCurrentFragmentTag;
    private Fragment mCurrentFragment;

    private ArrayList<Phrase> mPhrases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager fm = getSupportFragmentManager();

        if (savedInstanceState != null) {
            mPhrases = savedInstanceState.getParcelableArrayList("phrases");
            mCurrentFragmentTag = savedInstanceState.getString("currentFragmentTag");
            mCurrentFragment = getSupportFragmentManager().findFragmentByTag(mCurrentFragmentTag);
        } else {
            mPhrases = new ArrayList<Phrase>();
            mCurrentFragment = new ReverseFragment();
            mCurrentFragmentTag = REVERSE_FRAGMENT_TAG;
            fm.beginTransaction().add(mCurrentFragment, mCurrentFragmentTag);
        }
        fm.beginTransaction()
                .replace(R.id.container, mCurrentFragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle b) {
        b.putParcelableArrayList("phrases", mPhrases);
        b.putString("currentFragmentTag", mCurrentFragmentTag);
        super.onSaveInstanceState(b);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0)
                finish();
            mCurrentFragmentTag = REVERSE_FRAGMENT_TAG;
            getSupportFragmentManager().popBackStack();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onWordReversed(Phrase p) {
        int index = mPhrases.indexOf(p);
        if (index > -1) {
            mPhrases.get(index).incrementCount();
            Toast.makeText(this, R.string.phrase_already_exists, Toast.LENGTH_LONG).show();
        } else {
            mPhrases.add(p);
        }
    }

    @Override
    public void onHistoryOriginalPressed() {
        mCurrentFragmentTag = ORIGINAL_HISTORY_FRAGMENT_TAG;
        FragmentManager fm = getSupportFragmentManager();
        Bundle arguments = new Bundle();
        arguments.putInt(PhraseHistoryListFragment.LIST_ORDER,
                PhraseHistoryListFragment.ORIGINAL_FIRST);
        arguments.putParcelableArrayList(PhraseHistoryListFragment.PHRASES, mPhrases);
        PhraseHistoryListFragment lf = new PhraseHistoryListFragment();
        lf.setArguments(arguments);
        fm.beginTransaction().add(lf, mCurrentFragmentTag);
        fm.beginTransaction()
                .replace(R.id.container, lf)
                .addToBackStack("phraseHistoryFragment")
                .commit();
        mCurrentFragment = lf;
    }

    @Override
    public void onHistoryReversedPressed() {
        mCurrentFragmentTag = REVERSE_HISTORY_FRAGMENT_TAG;
        FragmentManager fm = getSupportFragmentManager();
        Bundle arguments = new Bundle();
        arguments.putInt(PhraseHistoryListFragment.LIST_ORDER,
                PhraseHistoryListFragment.REVERSED_FIRST);
        arguments.putParcelableArrayList(PhraseHistoryListFragment.PHRASES, mPhrases);
        PhraseHistoryListFragment lf = new PhraseHistoryListFragment();
        lf.setArguments(arguments);
        fm.beginTransaction().add(lf, mCurrentFragmentTag);
        fm.beginTransaction()
                .replace(R.id.container, lf)
                .addToBackStack("phraseHistoryFragment")
                .commit();
        mCurrentFragment = lf;
    }
}
