/*
 * Copyright (C) 2014 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.contentstack.okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;

/**
 * A policy on how much time to spend on a task before giving up. When a task
 * times out, it is left in an unspecified state and should be abandoned. For
 * example, if reading from a source times out, that source should be closed and
 * the read should be retried later. If writing to a sink times out, the same
 * rules apply: close the sink and retry later.
 *
 * <h3>Timeouts and Deadlines</h3>
 * This class offers two complementary controls to define a timeout policy.
 *
 * <p><strong>Timeouts</strong> specify the maximum time to wait for a single
 * operation to complete. Timeouts are typically used to detect problems like
 * network partitions. For example, if a remote peer doesn't return <i>any</i>
 * data for ten seconds, we may assume that the peer is unavailable.
 *
 * <p><strong>Deadlines</strong> specify the maximum time to spend on a job,
 * composed of one or more operations. Use deadlines to set an upper bound on
 * the time invested on a job. For example, a battery-conscious app may limit
 * how much time it spends preloading content.
 */
public class Timeout {
    /**
     * An empty timeout that neither tracks nor detects timeouts. Use this when
     * timeouts aren't necessary, such as in implementations whose operations
     * do not block.
     */
    public static final Timeout NONE = new Timeout() {
        @Override
        public Timeout timeout(long timeout, TimeUnit unit) {
            return this;
        }

        @Override
        public Timeout deadlineNanoTime(long deadlineNanoTime) {
            return this;
        }

        @Override
        public void throwIfReached() throws IOException {
        }
    };

    /**
     * True if {@code deadlineNanoTime} is defined. There is no equivalent to null
     * or 0 for {@link System#nanoTime}.
     */
    private boolean hasDeadline;
    private long deadlineNanoTime;
    private long timeoutNanos;

    public Timeout() {
    }

    /**
     * Wait at most {@code timeout} time before aborting an operation. Using a
     * per-operation timeout means that as long as forward progress is being made,
     * no sequence of operations will fail.
     *
     * <p>If {@code timeout == 0}, operations will run indefinitely. (Operating
     * system timeouts may still apply.)
     */
    public Timeout timeout(long timeout, TimeUnit unit) {
        if (timeout < 0) throw new IllegalArgumentException("timeout < 0: " + timeout);
        if (unit == null) throw new IllegalArgumentException("unit == null");
        this.timeoutNanos = unit.toNanos(timeout);
        return this;
    }

    /**
     * Returns the timeout in nanoseconds, or {@code 0} for no timeout.
     */
    public long timeoutNanos() {
        return timeoutNanos;
    }

    /**
     * Returns true if a deadline is enabled.
     */
    public boolean hasDeadline() {
        return hasDeadline;
    }

    /**
     * Returns the {@linkplain System#nanoTime() nano time} when the deadline will
     * be reached.
     *
     * @throws IllegalStateException if no deadline is set.
     */
    public long deadlineNanoTime() {
        if (!hasDeadline) throw new IllegalStateException("No deadline");
        return deadlineNanoTime;
    }

    /**
     * Sets the {@linkplain System#nanoTime() nano time} when the deadline will be
     * reached. All operations must complete before this time. Use a deadline to
     * set a maximum bound on the time spent on a sequence of operations.
     */
    public Timeout deadlineNanoTime(long deadlineNanoTime) {
        this.hasDeadline = true;
        this.deadlineNanoTime = deadlineNanoTime;
        return this;
    }

    /**
     * Set a deadline of now plus {@code duration} time.
     */
    public final Timeout deadline(long duration, TimeUnit unit) {
        if (duration <= 0) throw new IllegalArgumentException("duration <= 0: " + duration);
        if (unit == null) throw new IllegalArgumentException("unit == null");
        return deadlineNanoTime(System.nanoTime() + unit.toNanos(duration));
    }

    /**
     * Clears the timeout. Operating system timeouts may still apply.
     */
    public Timeout clearTimeout() {
        this.timeoutNanos = 0;
        return this;
    }

    /**
     * Clears the deadline.
     */
    public Timeout clearDeadline() {
        this.hasDeadline = false;
        return this;
    }

    /**
     * Throws an {@link IOException} if the deadline has been reached or if the
     * current thread has been interrupted. This method doesn't detect timeouts;
     * that should be implemented to asynchronously abort an in-progress
     * operation.
     */
    public void throwIfReached() throws IOException {
        if (Thread.interrupted()) {
            throw new InterruptedIOException();
        }

        if (hasDeadline && System.nanoTime() > deadlineNanoTime) {
            throw new IOException("deadline reached");
        }
    }
}
