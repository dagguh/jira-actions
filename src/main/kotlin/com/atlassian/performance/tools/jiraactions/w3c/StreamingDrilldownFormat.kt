package com.atlassian.performance.tools.jiraactions.w3c

import com.atlassian.performance.tools.jiraactions.api.w3c.*
import java.time.Duration
import javax.json.stream.JsonParser
import javax.json.stream.JsonParser.Event.*

internal class StreamingDrilldownFormat {

    private val buffering = VerboseJsonFormat()

    fun deserializeRecordedEntries(
        json: JsonParser
    ): RecordedPerformanceEntries {
        var navigations: List<PerformanceNavigationTiming>? = null
        var resources: List<PerformanceResourceTiming>? = null
        while (json.hasNext()) {
            val event = json.next()
            when (event) {
                KEY_NAME -> {
                    val key = json.string
                    json.next()
                    when (key) {
                        "navigations" -> navigations = deserializeNavigations(json)
                        "resources" -> resources = deserializeResources(json)
                    }
                }
                else -> throw Exception("Unexpected $event")
            }
        }
        return RecordedPerformanceEntries(
            navigations = navigations!!,
            resources = resources!!
        )
    }

    private fun deserializeNavigations(
        json: JsonParser
    ): List<PerformanceNavigationTiming> {
        val navigations = mutableListOf<PerformanceNavigationTiming>()
        while (json.hasNext()) {
            val event = json.next()
            if (event == END_ARRAY) {
                break
            }
            when (event) {
                START_ARRAY -> {
                }
                START_OBJECT -> {
                    navigations += deserializeNavigationTiming(json)
                }
                END_OBJECT -> {
                }
                else -> throw Exception("Unexpected $event")
            }
        }
        return navigations
    }

    private fun deserializeNavigationTiming(
        json: JsonParser
    ): PerformanceNavigationTiming {
        var resource: PerformanceResourceTiming? = null
        var unloadEventStart: Duration? = null
        var unloadEventEnd: Duration? = null
        var domInteractive: Duration? = null
        var domContentLoadedEventStart: Duration? = null
        var domContentLoadedEventEnd: Duration? = null
        var domComplete: Duration? = null
        var loadEventStart: Duration? = null
        var loadEventEnd: Duration? = null
        var type: NavigationType? = null
        var redirectCount: Int? = null
        while (json.hasNext()) {
            val event = json.next()
            when (event) {
                KEY_NAME -> {
                    val key = json.string
                    json.next()
                    when (key) {
                        "resource" -> resource = deserializeResourceTiming(json)
                        "unloadEventStart" -> unloadEventStart = Duration.parse(json.string)
                        "unloadEventEnd" -> unloadEventEnd = Duration.parse(json.string)
                        "domInteractive" -> domInteractive = Duration.parse(json.string)
                        "domContentLoadedEventStart" -> domContentLoadedEventStart = Duration.parse(json.string)
                        "domContentLoadedEventEnd" -> domContentLoadedEventEnd = Duration.parse(json.string)
                        "domComplete" -> domComplete = Duration.parse(json.string)
                        "loadEventStart" -> loadEventStart = Duration.parse(json.string)
                        "loadEventEnd" -> loadEventEnd = Duration.parse(json.string)
                        "type" -> type = NavigationType.valueOf(json.string)
                        "redirectCount" -> redirectCount = json.bigDecimal.intValueExact()
                    }
                }
                else -> throw Exception("Unexpected $event")
            }
        }
        return PerformanceNavigationTiming(
            resource = resource!!,
            unloadEventStart = unloadEventStart!!,
            unloadEventEnd = unloadEventEnd!!,
            domInteractive = domInteractive!!,
            domContentLoadedEventStart = domContentLoadedEventStart!!,
            domContentLoadedEventEnd = domContentLoadedEventEnd!!,
            domComplete = domComplete!!,
            loadEventStart = loadEventStart!!,
            loadEventEnd = loadEventEnd!!,
            type = type!!,
            redirectCount = redirectCount!!
        )
    }

    private fun deserializeResources(
        json: JsonParser
    ): List<PerformanceResourceTiming> {
        val resources = mutableListOf<PerformanceResourceTiming>()
        while (json.hasNext()) {
            val event = json.next()
            if (event == END_ARRAY) {
                break
            }
            when (event) {
                START_ARRAY -> {
                }
                START_OBJECT -> {
                    resources += deserializeResourceTiming(json)
                }
                END_OBJECT -> {
                }
                else -> throw Exception("Unexpected $event")
            }
        }
        return resources
    }

    private fun deserializeResourceTiming(
        json: JsonParser
    ): PerformanceResourceTiming {
        var entry: PerformanceEntry? = null
        var initiatorType: String? = null
        var nextHopProtocol: String? = null
        var workerStart: Duration? = null
        var redirectStart: Duration? = null
        var redirectEnd: Duration? = null
        var fetchStart: Duration? = null
        var domainLookupStart: Duration? = null
        var domainLookupEnd: Duration? = null
        var connectStart: Duration? = null
        var connectEnd: Duration? = null
        var secureConnectionStart: Duration? = null
        var requestStart: Duration? = null
        var responseStart: Duration? = null
        var responseEnd: Duration? = null
        var transferSize: Long? = null
        var encodedBodySize: Long? = null
        var decodedBodySize: Long? = null
        while (json.hasNext()) {
            val event = json.next()
            when (event) {
                KEY_NAME -> {
                    val key = json.string
                    json.next()
                    when (key) {
                        "entry" -> entry = deserializeEntry(json)
                        "initiatorType" -> initiatorType = json.string
                        "nextHopProtocol" -> nextHopProtocol = json.string
                        "workerStart" -> workerStart = Duration.parse(json.string)
                        "redirectStart" -> redirectStart = Duration.parse(json.string)
                        "redirectEnd" -> redirectEnd = Duration.parse(json.string)
                        "fetchStart" -> fetchStart = Duration.parse(json.string)
                        "domainLookupStart" -> domainLookupStart = Duration.parse(json.string)
                        "domainLookupEnd" -> domainLookupEnd = Duration.parse(json.string)
                        "connectStart" -> connectStart = Duration.parse(json.string)
                        "connectEnd" -> connectEnd = Duration.parse(json.string)
                        "secureConnectionStart" -> secureConnectionStart = Duration.parse(json.string)
                        "requestStart" -> requestStart = Duration.parse(json.string)
                        "responseStart" -> responseStart = Duration.parse(json.string)
                        "responseEnd" -> responseEnd = Duration.parse(json.string)
                        "transferSize" -> transferSize = json.bigDecimal.longValueExact()
                        "encodedBodySize" -> encodedBodySize = json.bigDecimal.longValueExact()
                        "decodedBodySize" -> decodedBodySize = json.bigDecimal.longValueExact()
                    }
                }
                else -> throw Exception("Unexpected $event")
            }
        }
        return PerformanceResourceTiming(
            entry = entry!!,
            initiatorType = initiatorType!!,
            nextHopProtocol = nextHopProtocol!!,
            workerStart = workerStart!!,
            redirectStart = redirectStart!!,
            redirectEnd = redirectEnd!!,
            fetchStart = fetchStart!!,
            domainLookupStart = domainLookupStart!!,
            domainLookupEnd = domainLookupEnd!!,
            connectStart = connectStart!!,
            connectEnd = connectEnd!!,
            secureConnectionStart = secureConnectionStart!!,
            requestStart = requestStart!!,
            responseStart = responseStart!!,
            responseEnd = responseEnd!!,
            transferSize = transferSize!!,
            encodedBodySize = encodedBodySize!!,
            decodedBodySize = decodedBodySize!!
        )
    }

    private fun deserializeEntry(
        json: JsonParser
    ): PerformanceEntry {
        var name: String? = null
        var entryType: String? = null
        var startTime: Duration? = null
        var duration: Duration? = null
        while (json.hasNext()) {
            val event = json.next()
            when (event) {
                KEY_NAME -> {
                    val key = json.string
                    json.next()
                    when (key) {
                        "name" -> name = json.string
                        "entryType" -> entryType = json.string
                        "startTime" -> startTime = Duration.parse(json.string)
                        "duration" -> duration = Duration.parse(json.string)
                    }
                }
                else -> throw Exception("Unexpected $event")
            }
        }
        return PerformanceEntry(
            name = name!!,
            entryType = entryType!!,
            startTime = startTime!!,
            duration = duration!!
        )
    }
}
