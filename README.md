<a href="https://opensource.newrelic.com/oss-category/#new-relic-experimental"><picture><source media="(prefers-color-scheme: dark)" srcset="https://github.com/newrelic/opensource-website/raw/main/src/images/categories/dark/Experimental.png"><source media="(prefers-color-scheme: light)" srcset="https://github.com/newrelic/opensource-website/raw/main/src/images/categories/Experimental.png"><img alt="New Relic Open Source experimental project banner." src="https://github.com/newrelic/opensource-website/raw/main/src/images/categories/Experimental.png"></picture></a>

![GitHub forks](https://img.shields.io/github/forks/newrelic-experimental/newrelic-java-resilience4?style=social)
![GitHub stars](https://img.shields.io/github/stars/newrelic-experimental/newrelic-java-resilience4?style=social)
![GitHub watchers](https://img.shields.io/github/watchers/newrelic-experimental/newrelic-java-resilience4?style=social)

![GitHub all releases](https://img.shields.io/github/downloads/newrelic-experimental/newrelic-java-resilience4/total)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/newrelic-experimental/newrelic-java-resilience4)
![GitHub last commit](https://img.shields.io/github/last-commit/newrelic-experimental/newrelic-java-resilience4)
![GitHub Release Date](https://img.shields.io/github/release-date/newrelic-experimental/newrelic-java-resilience4)


![GitHub issues](https://img.shields.io/github/issues/newrelic-experimental/newrelic-java-resilience4)
![GitHub issues closed](https://img.shields.io/github/issues-closed/newrelic-experimental/newrelic-java-resilience4)
![GitHub pull requests](https://img.shields.io/github/issues-pr/newrelic-experimental/newrelic-java-resilience4)
![GitHub pull requests closed](https://img.shields.io/github/issues-pr-closed/newrelic-experimental/newrelic-java-resilience4)

# New Relic Java Instrumentation for Resilience4

The following Resilience4j components are instrumented:

- CircuitBreaker
- Bulkhead
- RateLimiter
- Retry
- TimeLimiter



## Installation

To install:

1. Download the latest release jar files.   
2. In the New Relic Java directory (the one containing newrelic.jar), create a directory named extensions if it does not already exist.
3. Copy the downloaded jars into the extensions directory.
4. Restart the application.

## Resilience4j Metrics Collection 

This module also collects and reports metrics from Resilience4j components (Circuit Breaker, Bulkhead, ThreadPool Bulkhead, Rate Limiter, and Retry) using New Relic Agent APIs. The metrics are periodically collected and sent to New Relic Insights as custom events.




### Configuration

To enable (default: true) /disable specific metrics collection and set the polling interval ( default: 1 minute ) , update the `newrelic.yml` configuration file with the following parameters:



```yaml
# Circuit Breaker Metrics
Resilience4j.metrics.circuitbreaker.enabled: true
Resilience4j.metrics.circuitbreaker.intervalminutes: 5

# Bulkhead Metrics
Resilience4j.metrics.bulkhead.enabled: true
Resilience4j.metrics.bulkhead.intervalminutes: 5

# ThreadPool Bulkhead Metrics
Resilience4j.metrics.threadpoolbulkhead.enabled: true
Resilience4j.metrics.threadpoolbulkhead.intervalminutes: 5

# Rate Limiter Metrics
Resilience4j.metrics.ratelimiter.enabled: true
Resilience4j.metrics.ratelimiter.intervalminutes: 5

# Retry Metrics
Resilience4j.metrics.retry.enabled: true
Resilience4j.metrics.retry.intervalminutes: 5
```
### Usage
**Enable Metrics Collection:** Set the appropriate flags in the newrelic.yml file to enable metrics collection for the desired Resilience4j components.

**Set Polling Interval:** Configure the polling interval (in minutes) for each component's metrics collection.

**Run the Application:** Start your application. The metrics will be collected at the specified intervals and sent to New Relic Insights as custom events.

**Monitor Metrics in New Relic:** Log in to your New Relic account and navigate to the Insights section to view the custom events for the metrics collected.

This setup helps you monitor the performance and reliability of your Resilience4j components in real-time using New Relic's powerful monitoring and alerting capabilities.

## Custom Event Tables in New Relic Platform

The metrics collected from each Resilience4j pattern are reported as custom events to New Relic Insights. Below are the custom event tables where you can find the reported metrics:


### CircuitBreakerMetrics

- **Custom Event Table Name**: `CircuitBreakerMetrics`
- **Metrics**:
  - `Name`
  - `FailureRate`
  - `BufferedCalls`
  - `Failed`
  - `NotPermittedCalls`
  - `SlowCalls`
  - `SuccessfulCalls`
  - `SlowCallRate`
  - `SlowFailedCalls`
  - `SlowSuccessfulCalls`
    
### BulkheadMetrics

- **Custom Event Table Name**: `BulkheadMetrics`
- **Metrics**:
  - `Name`
  - `AvailableConcurrentCalls`
  - `MaxAllowedConcurrentCalls`
 
### ThreadPoolBulkheadMetrics

- **Custom Event Table Name**: `ThreadPoolBulkheadMetrics`
- **Metrics**:
  - `Name`
  - `CoreThreadPoolSize`
  - `ThreadPoolSize`
  - `MaximumThreadPoolSize`
  - `QueueDepth`
  - `RemainingQueueCapacity`
  - `QueueCapacity`
### RetryMetrics

- **Custom Event Table Name**: `RetryMetrics`
- **Metrics**:
  - `Name`
  - `NumberOfSuccessfulCallsWithoutRetryAttempt`
  - `NumberOfFailedCallsWithoutRetryAttempt`
  - `NumberOfSuccessfulCallsWithRetryAttempt`
  - `NumberOfFailedCallsWithRetryAttempt`



### RateLimiterMetrics

- **Custom Event Table Name**: `RateLimiterMetrics`
- **Metrics**:
  - `Name`
  - `AvailablePermissions`
  - `NumberOfWaitingThreads`



## Accessing Custom Events in New Relic Insights

1. **Log in to New Relic**: Access your New Relic account.
2. **Query Custom Events**: Use the following NRQL (New Relic Query Language) queries to access the custom events:

   - **ThreadPoolBulkheadMetrics**:
     ```sql
     SELECT * FROM ThreadPoolBulkheadMetrics
     ```

   - **RetryMetrics**:
     ```sql
     SELECT * FROM RetryMetrics
     ```

   - **CircuitBreakerMetrics**:
     ```sql
     SELECT * FROM CircuitBreakerMetrics
     ```

   - **RateLimiterMetrics**:
     ```sql
     SELECT * FROM RateLimiterMetrics
     ```

   - **BulkheadMetrics**:
     ```sql
     SELECT * FROM BulkheadMetrics
     ```

These queries will display the custom events along with the collected metrics, allowing you to monitor and analyze the performance and reliability of your Resilience4j components in real-time.
## Getting Started

Once installed, the instrumentation will track transactions through the Resilience4j components.

## Building

Building the extension requires that Gradle is installed.
To build the extension jars from source, follow these steps:
### Build single extension
To build a single extension with name *extension*, do the following:
1. Set an environment variable *NEW_RELIC_EXTENSIONS_DIR* and set its value to the directory where you want the jar file built.
2. Run the command: ./gradlew *extension*:clean *extension*:install
### Build all extensions
To build all extensions, do the following:
1. Set an environment variable *NEW_RELIC_EXTENSIONS_DIR* and set its value to the directory where you want the jar file built.
2. Run the command: ./gradlew clean install

## Support

New Relic has open-sourced this project. This project is provided AS-IS WITHOUT WARRANTY OR DEDICATED SUPPORT. Issues and contributions should be reported to the project here on GitHub.

>We encourage you to bring your experiences and questions to the [Explorers Hub](https://discuss.newrelic.com) where our community members collaborate on solutions and new ideas.

## Contributing

We encourage your contributions to improve [Project Name]! Keep in mind when you submit your pull request, you'll need to sign the CLA via the click-through using CLA-Assistant. You only have to sign the CLA one time per project. If you have any questions, or to execute our corporate CLA, required if your contribution is on behalf of a company, please drop us an email at opensource@newrelic.com.

**A note about vulnerabilities**

As noted in our [security policy](../../security/policy), New Relic is committed to the privacy and security of our customers and their data. We believe that providing coordinated disclosure by security researchers and engaging with the security community are important means to achieve our security goals.

If you believe you have found a security vulnerability in this project or any of New Relic's products or websites, we welcome and greatly appreciate you reporting it to New Relic through [HackerOne](https://hackerone.com/newrelic).

## License

New Relic Java Instrumentation for resilience4 is licensed under the [Apache 2.0](http://apache.org/licenses/LICENSE-2.0.txt) License.

>[If applicable: [Project Name] also uses source code from third-party libraries. You can find full details on which libraries are used and the terms under which they are licensed in the third-party notices document.]
