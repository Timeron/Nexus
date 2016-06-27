package com.timeron.nexus.apps.wallet.service.dto;

import java.util.ArrayList;
import java.util.List;

public class HierarchyPieChartDTO extends PieChartDTO{

	private List<HierarchyPieChartDTO> children = new ArrayList<HierarchyPieChartDTO>();

	public List<HierarchyPieChartDTO> getChildren() {
		return children;
	}

	public void setChildren(List<HierarchyPieChartDTO> children) {
		this.children = children;
	}
	
	public void addChildren(HierarchyPieChartDTO children) {
		this.children.add(children);
	}
	
	
}
