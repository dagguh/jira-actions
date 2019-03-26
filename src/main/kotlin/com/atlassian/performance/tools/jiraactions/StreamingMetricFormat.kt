package com.atlassian.performance.tools.jiraactions

import com.atlassian.performance.tools.jiraactions.api.ActionMetric
import com.atlassian.performance.tools.jiraactions.api.ActionResult
import com.atlassian.performance.tools.jiraactions.api.w3c.RecordedPerformanceEntries
import com.atlassian.performance.tools.jiraactions.w3c.StreamingDrilldownFormat
import com.atlassian.performance.tools.jiraactions.w3c.VerboseJsonFormat
import java.time.Duration
import java.time.Instant
import java.util.*
import javax.json.JsonObject
import javax.json.stream.JsonParser
import javax.json.stream.JsonParser.Event.*

internal class StreamingMetricFormat {

    private val streamingDrilldownFormat = StreamingDrilldownFormat()
    private val bufferingDrilldownFormat = VerboseJsonFormat()

    fun deserialize(
        json: JsonParser
    ): ActionMetric {
        var label: String? = null
        var result: ActionResult? = null
        var duration: Duration? = null
        var start: Instant? = null
        var virtualUser: UUID? = null
        var observation: JsonObject? = null
        var drilldown: RecordedPerformanceEntries? = null
        while (json.hasNext()) {
            val event = json.next()
            when (event) {
                START_OBJECT -> {
                }
                KEY_NAME -> {
                    val key = json.string
                    json.next()
                    when (key) {
                        "label" -> label = json.string
                        "result" -> result = ActionResult.valueOf(json.string)
                        "duration" -> duration = Duration.parse(json.string)
                        "start" -> start = Instant.parse(json.string)
                        "virtualUser" -> virtualUser = UUID.fromString(json.string)
                        "observation" -> observation = json.`object`
                        "drilldown" -> drilldown = streamingDrilldownFormat.deserializeRecordedEntries(json)
                    }
                }
                END_OBJECT -> {
                }
                else -> throw Exception("Unexpected $event")
            }
        }
        return ActionMetric.Builder(
            label = label!!,
            result = result!!,
            duration = duration!!,
            start = start!!
        )
            .virtualUser(virtualUser!!)
            .observation(observation)
            .drilldown(drilldown)
            .build()
    }
}
