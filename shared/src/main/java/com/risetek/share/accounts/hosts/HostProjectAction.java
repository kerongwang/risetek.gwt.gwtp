package com.risetek.share.accounts.hosts;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;
import com.risetek.share.dispatch.GetResult;

public class HostProjectAction extends UnsecuredActionImpl<GetResult<HostProjectEntity>> {

	protected HostProjectAction(){}

	// List User Entity
	public HostProjectAction(HostProjectEntity entity) {
		sequence = sequence_inc++;
	}

	
	private static long sequence_inc = 0;
	public long sequence;
	public HostProjectEntity entity;
}
