package com.telepathy.planfinder.model;

public class PlanFinderResponse {
  private int planCost;
  private String plansToBeSelected;

	public int getPlanCost() {
		return planCost;
	}

	public void setPlanCost(int planCost) {
		this.planCost = planCost;
	}

	public String getPlansToBeSelected() {
		return plansToBeSelected;
	}

	public void setPlansToBeSelected(String plansToBeSelected) {
		this.plansToBeSelected = plansToBeSelected;
	}

}
