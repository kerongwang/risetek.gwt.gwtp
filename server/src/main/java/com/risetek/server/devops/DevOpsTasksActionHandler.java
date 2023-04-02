package com.risetek.server.devops;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.risetek.share.devops.DevOpsTaskEntity;
import com.risetek.share.devops.DevOpsTasksAction;
import com.risetek.share.dispatch.GetResults;

public class DevOpsTasksActionHandler implements ActionHandler<DevOpsTasksAction, GetResults<DevOpsTaskEntity>> {
	@Override
	public GetResults<DevOpsTaskEntity> execute(DevOpsTasksAction action, ExecutionContext context) throws ActionException {
		return new GetResults<>(DevOpsTask.getTasks());
	}

	@Override
	public Class<DevOpsTasksAction> getActionType() {
		return DevOpsTasksAction.class;
	}

	@Override
	public void undo(DevOpsTasksAction action, GetResults<DevOpsTaskEntity> result, ExecutionContext context)
			throws ActionException {
		// do nothing.
	}}
