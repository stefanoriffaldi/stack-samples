package org.example.test.tools;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class LogExtractor {

    private final Logger logger;
    private final ListAppender<ILoggingEvent> appender;

    private LogExtractor(Logger logger) {
        this.logger = logger;
        appender = new ListAppender<>();
        appender.start();

        // add the appender to the logger
        logger.addAppender(appender);
    }

    public LogExtractor(Class<?> clazz) {
        this((Logger) LoggerFactory.getLogger(clazz));
    }

    public LogExtractor(String name) {
        this((Logger) LoggerFactory.getLogger(name));
    }

    public Stream<String> getFormattedMessages() {
        return appender.list.stream().map(ILoggingEvent::getFormattedMessage);
    }

    public void detach() {
        logger.detachAppender(appender);
        appender.stop();
    }
}
