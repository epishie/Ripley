/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface FeedScope {

}
