package com.atlassian.performance.tools.jiraactions.api.parser

import com.atlassian.performance.tools.jiraactions.api.ActionMetric
import java.io.File
import java.io.InputStream

class MergingActionMetricsParser {

    private val parser = ActionMetricsParser()

    private var parsed = 0

    fun parse(
        metrics: List<File>
    ): List<ActionMetric> = metrics
        .asSequence()
        .filter { it.exists() }
        .map { it.inputStream() }
        .map { parse(it) }
        .onEach { println(++parsed) }
        .flatten()
        .toList()

    private fun parse(stream: InputStream): List<ActionMetric> = stream.use { parser.parse(it) }
}