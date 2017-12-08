package gerrymandering.measure;

import gerrymandering.model.CompleteWork;

public interface StateMeasure {
	MeasureResults runStateMeasure(CompleteWork completeWork);
}
