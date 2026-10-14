package com.example.podly.data

import com.example.podly.models.Episode
import com.example.podly.models.PodcastChannel
import com.example.podly.utils.parseDuration
import com.example.podly.utils.parsePubDate
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

object FeedParser {
    fun parse(feed: String): PodcastChannel {
        val xmlParser = XmlPullParserFactory.newInstance().run {
            isNamespaceAware = true
            newPullParser()
        }
        xmlParser.setInput(StringReader(feed))

        val channelMap = HashMap<String, String?>()
        val episodes = mutableListOf<Episode>()
        var episodeMap = HashMap<String, String?>()

        var currentScope = "channel"
        var currentText: String? = ""
        var eventType = xmlParser.eventType

        while (eventType != XmlPullParser.END_DOCUMENT) {
            val tag = xmlParser.name
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (tag) {
                        "channel" -> {
                            currentScope = "channel"
                        }

                        "item" -> {
                            currentScope = "episode"
                            episodeMap = HashMap()
                        }

                        "enclosure" -> {
                            episodeMap["url"] = xmlParser.getAttributeValue(0)
                        }
                        "category" -> {
                            channelMap["category"] = xmlParser.getAttributeValue(0)
                        }
                    }
                }

                XmlPullParser.TEXT -> currentText = xmlParser.text
                XmlPullParser.END_TAG ->
                    if (currentScope == "channel") {
                        when (tag) {
                            "title" -> channelMap["title"] = currentText
                            "author" -> channelMap["author"] = currentText
                            "description" -> channelMap["description"] = currentText
                            "url" -> channelMap["artworkUrl"] = currentText
                        }
                    } else {
                        when (tag) {
                            "title" -> episodeMap["title"] = currentText
                            "description" -> episodeMap["description"] = currentText
                            "pubDate" -> episodeMap["pubDate"] = currentText
                            "duration" -> episodeMap["duration"] = currentText
                            "guid" -> episodeMap["guid"] = currentText
                            "item" -> episodes.add(
                                Episode(
                                    title = episodeMap["title"],
                                    description = episodeMap["description"],
                                    pubDate = parsePubDate(episodeMap["pubDate"]),
                                    duration = parseDuration(episodeMap["duration"]),
                                    guid = episodeMap["guid"] ?: episodeMap["url"],
                                    audioUrl = episodeMap["url"]
                                )
                            )
                        }
                    }

            }
            eventType = xmlParser.next()
        }

        return PodcastChannel(
            title = channelMap["title"],
            description = channelMap["description"],
            author = channelMap["author"],
            artworkUrl = channelMap["artworkUrl"],
            category = channelMap["category"],
            episodes = episodes
        )
    }
}