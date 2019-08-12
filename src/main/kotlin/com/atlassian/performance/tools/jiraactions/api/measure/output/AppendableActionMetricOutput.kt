package com.atlassian.performance.tools.jiraactions.api.measure.output

import com.atlassian.performance.tools.jiraactions.MetricVerboseJsonFormat
import com.atlassian.performance.tools.jiraactions.api.ActionMetric
import net.jcip.annotations.NotThreadSafe

@NotThreadSafe
class AppendableActionMetricOutput(
    private val target: Appendable
) : ActionMetricOutput {

    private val format = MetricVerboseJsonFormat()

    override fun write(metric: ActionMetric) {
        target.append(format.serialize(metric).toString())
        target.append('\n')
    }
}
