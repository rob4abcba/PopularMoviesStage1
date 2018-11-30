package com.example.android.popularmoviesstage1;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private final String mTitle, mImagePath, mSynopsis, mRating, mReleaseDate;

    public Movie(String title, String imagePath, String synopsis, String rating, String releaseDate) {
        this.mTitle = title;
        this.mImagePath = imagePath;
        this.mSynopsis = synopsis;
        this.mRating = rating;
        this.mReleaseDate = releaseDate;
    }

    // Read a string value from the parcel at the current dataPosition().
    private Movie(Parcel parcel) {
        mTitle = parcel.readString();
        mImagePath = parcel.readString();
        mSynopsis = parcel.readString();
        mRating = parcel.readString();
        mReleaseDate = parcel.readString();
    }

    // Get methods to return movie detail values
    public String getTitle() {
        return mTitle;
    }
    public String getImagePath() {
        return mImagePath;
    }
    public String getSynopsis() {
        return mSynopsis;
    }
    public String getRating() {
        return mRating;
    }
    public String getReleaseDate() {
        return mReleaseDate;
    }

    // Not used?
    // Review again later: https://stackoverflow.com/questions/4076946/parcelable-where-when-is-describecontents-used/4914799
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mImagePath);
        dest.writeString(mSynopsis);
        dest.writeString(mRating);
        dest.writeString(mReleaseDate);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}