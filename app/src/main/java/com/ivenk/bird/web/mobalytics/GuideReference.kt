package com.ivenk.bird.web.mobalytics

/*
 * Packs the guide url together with some meta information about the guide.
 * Useful for filtering the guides in advance.
 */
data class GuideReference(val url: String, val patch: String)

// TODO: Add more meta information
// TODO: Use class in MobalyticsCrawler
