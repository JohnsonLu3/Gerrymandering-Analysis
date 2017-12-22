package gerrymandering.measure;

import gerrymandering.common.TestType;

public abstract class MeasureResults {
	protected Boolean testResult;
	protected TestType testPerformed;

	public MeasureResults() {
	}

	public void addTestResult(Boolean result){
	    this.testResult = result;
	}

	public Boolean getTestResult(){
		return testResult;
	}

	public TestType getTestPerformed(){
		return testPerformed;
	}

	public void setTestPerformed(TestType testPerformed){
		this.testPerformed = testPerformed;
	}
}
