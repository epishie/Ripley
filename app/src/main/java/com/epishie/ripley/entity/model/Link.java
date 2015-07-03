/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.entity.model;

public class Link {

    private final String mId;
    private final String mAuthor;
    private final String mAuthorFlairCssClass;
    private final String mAuthorFlair;
    private final String mDomain;
    private final boolean mHidden;
    private final boolean mSelf;
    private final int mCommentCount;
    private final boolean mOver18;
    private final String mPermalink;
    private final boolean mSaved;
    private final int mScore;
    private final String mSelfText;
    private final String mSelfTextHtml;
    private final String mSubreddit;
    private final String mThumbnail;
    private final String mTitle;
    private final String mUrl;
    private final boolean mStickied;
    private final Creation mCreation;
    private final Vote mVote;

    protected Link(String id,
                   String author,
                   String authorFlairCssClass,
                   String authorFlair,
                   String domain,
                   boolean hidden,
                   boolean self,
                   int commentCount,
                   boolean over18,
                   String permalink,
                   boolean saved,
                   int score,
                   String selfText,
                   String selfTextHtml,
                   String subreddit,
                   String thumbnail,
                   String title,
                   String url,
                   boolean stickied,
                   Creation creation,
                   Vote vote) {
        mId = id;
        mAuthor = author;
        mAuthorFlairCssClass = authorFlairCssClass;
        mAuthorFlair = authorFlair;
        mDomain = domain;
        mHidden = hidden;
        mSelf = self;
        mCommentCount = commentCount;
        mOver18 = over18;
        mPermalink = permalink;
        mSaved = saved;
        mScore = score;
        mSelfText = selfText;
        mSelfTextHtml = selfTextHtml;
        mSubreddit = subreddit;
        mThumbnail = thumbnail;
        mTitle = title;
        mUrl = url;
        mStickied = stickied;
        mCreation = creation;
        mVote = vote;
    }

    public String getId() {
        return mId;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getAuthorFlairCssClass() {
        return mAuthorFlairCssClass;
    }

    public String getAuthorFlair() {
        return mAuthorFlair;
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

    public String getPermalink() {
        return mPermalink;
    }

    public boolean isSaved() {
        return mSaved;
    }

    public int getScore() {
        return mScore;
    }

    public String getSelfText() {
        return mSelfText;
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

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

    public boolean isStickied() {
        return mStickied;
    }

    public Creation getCreation() {
        return mCreation;
    }

    public Vote getVote() {
        return mVote;
    }

    public static class Builder {

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
        private boolean mSticked;
        private Creation mCreation;
        private Vote mVote;

        public void setId(String id) {
            mId = id;
        }

        public void setAuthor(String author) {
            mAuthor = author;
        }

        public void setAuthorFlairCssClass(String authorFlairCssClass) {
            mAuthorFlairCssClass = authorFlairCssClass;
        }

        public void setAuthorFlair(String authorFlair) {
            mAuthorFlair = authorFlair;
        }

        public void setDomain(String domain) {
            mDomain = domain;
        }

        public void setHidden(boolean hidden) {
            mHidden = hidden;
        }

        public void setSelf(boolean self) {
            mSelf = self;
        }

        public void setCommentCount(int commentCount) {
            mCommentCount = commentCount;
        }

        public void setOver18(boolean over18) {
            mOver18 = over18;
        }

        public void setPermalink(String permalink) {
            mPermalink = permalink;
        }

        public void setSaved(boolean saved) {
            mSaved = saved;
        }

        public void setScore(int score) {
            mScore = score;
        }

        public void setSelfText(String selfText) {
            mSelfText = selfText;
        }

        public void setSelfTextHtml(String selfTextHtml) {
            mSelfTextHtml = selfTextHtml;
        }

        public void setSubreddit(String subreddit) {
            mSubreddit = subreddit;
        }

        public void setThumbnail(String thumbnail) {
            mThumbnail = thumbnail;
        }

        public void setTitle(String title) {
            mTitle = title;
        }

        public void setUrl(String url) {
            mUrl = url;
        }

        public void setSticked(boolean sticked) {
            mSticked = sticked;
        }

        public void setCreation(Creation creation) {
            mCreation = creation;
        }

        public void setVote(Vote vote) {
            mVote = vote;
        }

        public Link create() {
            return new Link(mId,
                    mAuthor,
                    mAuthorFlairCssClass,
                    mAuthorFlair,
                    mDomain,
                    mHidden,
                    mSelf,
                    mCommentCount,
                    mOver18,
                    mPermalink,
                    mSaved,
                    mScore,
                    mSelfText,
                    mSelfTextHtml,
                    mSubreddit,
                    mThumbnail,
                    mTitle,
                    mUrl,
                    mSticked,
                    mCreation,
                    mVote);
        }
    }
}
