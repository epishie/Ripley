/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.entity.model;

public class Link {

    private String mId;
    private String mAuthor;
    private String mAuthorFlairCssClass;
    private String mAuthorFlair;
    private String mDomain;
    private boolean mHidden;
    private boolean mSelf;
    private int mCommentCount;
    private boolean mOver18;
    private String mPermalink;
    private boolean mSaved;
    private int mScore;
    private String mSelfText;
    private String mSelfTextHtml;
    private String mSubreddit;
    private String mThumbnail;
    private String mTitle;
    private String mUrl;
    private boolean mStickied;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Link)) {
            return false;
        }

        Link other = (Link)o;
        return mId.equals(other.mId);
    }

    public String getId() {
        return mId;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getAuthorFlair() {
        return mAuthorFlair;
    }

    public String getAuthorFlairCssClass() {
        return mAuthorFlairCssClass;
    }

    public String getDomain() {
        return mDomain;
    }

    public boolean isHidden() {
        return mHidden;
    }

    public boolean isSelf() {
        return mSelf;
    }

    public int getCommentCount() {
        return mCommentCount;
    }

    public boolean isOver18() {
        return mOver18;
    }

    public String getSelfText() {
        return mSelfText;
    }

    public String getPermalink() {
        return mPermalink;
    }

    public boolean isSaved() {
        return mSaved;
    }

    public int getScore() {
        return mScore;
    }

    public String getSelfTextHtml() {
        return mSelfTextHtml;
    }

    public String getSubreddit() {
        return mSubreddit;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public boolean isStickied() {
        return mStickied;
    }
}
