package edu.auburn.eng.csse.comp3710.weh0008.midterm;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by William on 3/3/14.
 */
public class Phrase implements Parcelable {

    private String mOriginal;
    private String mReversed;
    private int mCount = 1;

    public Phrase(String original) {
        mOriginal = original;
        mReversed = reversePhrase(original);
    }

    public Phrase(Parcel in) {
        Bundle bundle = in.readBundle();
        mOriginal = bundle.getString("original");
        mReversed = bundle.getString("reversed");
        mCount = bundle.getInt("count");
    }

    public String getOriginal() {
        return mOriginal;
    }

    public String getReversed() {
        return mReversed;
    }

    public void incrementCount() {
        mCount++;
    }

    public int getCount() {
        return mCount;
    }

    /**
     * Just a simple private method to convert a string into a reverse of its own string
     *     (By my own - albeit short - performance testing a few months ago, this is the most efficient
     *     way.)
     *
     * @param phrase the word that should be flipped
     * @return the word that has been flipped
     */
    private String reversePhrase(String phrase) {
        char[] reverse = phrase.toCharArray();
        for (int i = 0; i < phrase.length() / 2; i++) {
            char temp = reverse[i];
            reverse[i] = reverse[phrase.length() - i - 1];
            reverse[phrase.length() - i - 1] = temp;
        }
        return new String(reverse);
    }

    @Override
    public boolean equals(Object o) {
        return ((Phrase) o).getOriginal().equals(mOriginal);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Bundle b = new Bundle();
        b.putString("original", mOriginal);
        b.putString("reversed", mReversed);
        b.putInt("count", mCount);
        parcel.writeBundle(b);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Phrase createFromParcel(Parcel in) {
            return new Phrase(in);
        }

        public Phrase[] newArray(int size) {
            return new Phrase[size];
        }
    };

    public static class OriginalPhraseComparator implements Comparator<Phrase> {
        @Override
        public int compare(Phrase phrase, Phrase phrase2) {
            return phrase.getOriginal().compareTo(phrase2.getOriginal());
        }
    }

    public static class ReversedPhraseComparator implements Comparator<Phrase> {
        @Override
        public int compare(Phrase phrase, Phrase phrase2) {
            return phrase.getReversed().compareTo(phrase2.getReversed());
        }
    }
}
