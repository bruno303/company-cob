package com.companycob.tests.runner;

import java.lang.annotation.Annotation;

import org.junit.internal.runners.statements.Fail;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.companycob.tests.annotations.MaxExecutionTimes;

public class RunManyTimesUntilFailureRunner extends SpringJUnit4ClassRunner {

	private int maxRunCount = 50;

	public RunManyTimesUntilFailureRunner(Class<?> testClass) throws InitializationError {
		super(testClass);
	}

	@Override
    protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
		final Description description = describeChild(method);
        if (isIgnored(method)) {
            notifier.fireTestIgnored(description);
        } else {
        	final MaxExecutionTimes annotation = getAnnotation(description, MaxExecutionTimes.class);
        	setMaxRunCount(annotation);
	        runManyTimesUntilFailure(method, notifier, description);
        }
    }

	private void runManyTimesUntilFailure(final FrameworkMethod method, RunNotifier notifier, Description description) {
		final RunManyTimesUntilFailureListener listener = new RunManyTimesUntilFailureListener(this.maxRunCount);
        notifier.addListener(listener);
        while (listener.shouldContinue) {
        	final Statement statement = getStatement(method);
        	runLeaf(statement, description, notifier);
        }
	}

	private Statement getStatement(final FrameworkMethod method) {
		Statement statement;
		try {
			statement = methodBlock(method);
		}
		catch (final Exception ex) {
			statement = new Fail(ex);
		}

		return statement;
	}

	private void setMaxRunCount(MaxExecutionTimes annotation) {
		if (annotation != null) {

        	final int maxExecutionCount = annotation.value();
            if(maxExecutionCount <= 0) {
            	throw new IllegalArgumentException("MaxExecutionTimes must be greater than zero.");
            }

            this.maxRunCount = maxExecutionCount;
        }
	}

	private <T extends Annotation> T getAnnotation(Description description, Class<T> clazz) {
		var annotation = description.getAnnotation(clazz);
    	if (annotation == null) {
    		annotation = description.getTestClass().getAnnotation(clazz);
    	}

    	return annotation;
	}

	private static class RunManyTimesUntilFailureListener extends RunListener {

		private final int maxRunCount;

		public RunManyTimesUntilFailureListener(int maxRunCount) {
			this.maxRunCount = maxRunCount;
		}

        boolean shouldContinue = true;
        int runCount = 0;

        @Override
        public void testFailure(final Failure failure) throws Exception {
            shouldContinue = false;
        }

        @Override
        public void testFinished(Description description) throws Exception {
            runCount++;
            shouldContinue = (shouldContinue && runCount < this.maxRunCount);
        }
    }
}
