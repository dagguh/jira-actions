package com.atlassian.performance.tools.jiraactions.api.page

import java.time.Duration
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions

class ProjectSummaryPage(
    private val driver: WebDriver
) {

    fun waitForMetadata(): ProjectSummaryPage {
        driver.wait(
            Duration.ofSeconds(6),
            ExpectedConditions.presenceOfElementLocated(By.className("project-meta-column"))
        )
        return this
    }
}
