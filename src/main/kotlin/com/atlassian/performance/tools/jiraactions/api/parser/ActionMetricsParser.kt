package com.atlassian.performance.tools.jiraactions.api.parser

import com.atlassian.performance.tools.jiraactions.MetricVerboseJsonFormat
import com.atlassian.performance.tools.jiraactions.StreamingMetricFormat
import com.atlassian.performance.tools.jiraactions.api.ActionMetric
import org.apache.logging.log4j.LogManager
import java.io.InputStream
import java.io.StringReader
import javax.json.spi.JsonProvider

class ActionMetricsParser {

    private val logger = LogManager.getLogger(this::class.java)
    private val bufferingFormat = MetricVerboseJsonFormat()
    private val streamingFormat = StreamingMetricFormat()
    private val provider = JsonProvider.provider()

    fun parse(
        metricsStream: InputStream
    ): List<ActionMetric> = metricsStream
        .bufferedReader()
        .lineSequence()
        .mapNotNull { parseOrNull(it) }
        .toList()

    private fun parseOrNull(
        line: String
    ): ActionMetric? = try {
        val reader = StringReader(line)
        streamingFormat.deserialize(provider.createParser(reader))
//        bufferingFormat.deserialize(provider.createReader(reader).readObject())
    } catch (e: Exception) {
        logger.debug("Discarding '$line'", e)
        null
    }
}
