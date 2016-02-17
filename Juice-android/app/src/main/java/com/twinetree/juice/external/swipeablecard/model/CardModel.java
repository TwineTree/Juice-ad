/**
 * AndTinder v0.1 for Android
 *
 * @Author: Enrique López Mañas <eenriquelopez@gmail.com>
 * http://www.lopez-manas.com
 *
 * TAndTinder is a native library for Android that provide a
 * Tinder card like effect. A card can be constructed using an
 * image and displayed with animation effects, dismiss-to-like
 * and dismiss-to-unlike, and use different sorting mechanisms.
 *
 * AndTinder is compatible with API Level 13 and upwards
 *
 * @copyright: Enrique López Mañas
 * @license: Apache License 2.0
 */

package com.twinetree.juice.external.swipeablecard.model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class CardModel {

	private String title;
	private String imageUrl;
	private String videoUrl;

    private OnCardDismissedListener mOnCardDismissedListener = null;

    private OnClickListener mOnClickListener = null;

    public interface OnCardDismissedListener {
        void onLike();
        void onDislike();
    }

    public interface OnClickListener {
        void OnClickListener();
    }

	public CardModel() {
		this(null, null, (String)null);
	}

	public CardModel(String title, String imageUrl, String videoUrl) {
		this.title = title;
		this.imageUrl = imageUrl;
		this.videoUrl = videoUrl;
	}

	public String getTitle() {
		return title;
	}

    public String getImageUrl() {
        return imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

	public void setTitle(String title) {
		this.title = title;
	}

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setOnCardDismissedListener( OnCardDismissedListener listener ) {
        this.mOnCardDismissedListener = listener;
    }

    public OnCardDismissedListener getOnCardDismissedListener() {
       return this.mOnCardDismissedListener;
    }


    public void setOnClickListener( OnClickListener listener ) {
        this.mOnClickListener = listener;
    }

    public OnClickListener getOnClickListener() {
        return this.mOnClickListener;
    }
}
