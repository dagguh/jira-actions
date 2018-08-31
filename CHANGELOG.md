# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## API
The API consists of all public Java types from `com.atlassian.performance.tools.jiraactions.api` and its subpackages:

  * [source compatibility]
  * [binary compatibility]
  * [behavioral compatibility] with behavioral contracts expressed via Javadoc

[source compatibility]: http://cr.openjdk.java.net/~darcy/OpenJdkDevGuide/OpenJdkDevelopersGuide.v0.777.html#source_compatibility
[binary compatibility]: http://cr.openjdk.java.net/~darcy/OpenJdkDevGuide/OpenJdkDevelopersGuide.v0.777.html#binary_compatibility
[behavioral compatibility]: http://cr.openjdk.java.net/~darcy/OpenJdkDevGuide/OpenJdkDevelopersGuide.v0.777.html#behavioral_compatibility

## [Unreleased]
[Unreleased]: https://bitbucket.org/atlassian/jira-actions/branches/compare/master%0Drelease-0.0.1

### Changed
- Define the public API.
- Add this change log.

## [0.0.1] - 2018-08-01
[0.0.1]: https://bitbucket.org/atlassian/jira-actions/branches/compare/release-0.0.1%0Dinitial-commit

### Added
- Extract performance reporting from [JPT submodule].
- Add [README.md](README.md).
- Configure Bitbucket Pipelines.

[JPT submodule]: https://stash.atlassian.com/projects/JIRASERVER/repos/jira-performance-tests/browse/actions?at=3dfb21b8b65cc0c1c26ad9aeff58f5d23fdabf5b
