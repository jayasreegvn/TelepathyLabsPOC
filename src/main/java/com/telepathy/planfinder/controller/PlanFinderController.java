package com.telepathy.planfinder.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.telepathy.planfinder.model.Plan;
import com.telepathy.planfinder.model.PlanFinderResponse;
import com.telepathy.planfinder.service.PlanFinderService;

@RestController
@RequestMapping("/api")
public class PlanFinderController {
	
	@Autowired
	private PlanFinderService planFinderService;

	
	@GetMapping("/plans")
    public List<Plan> getAllPlans() {
		List<Plan> Plans=new ArrayList<Plan>();
		Set<String> features=new HashSet<String>();
		features.add("voice");
		features.add("data");
		Plan plan=new Plan("PLAN1",200,features);
		Plans.add(plan);
        return Plans;
    }

	@PostMapping(value="/plans", consumes = {"multipart/form-data"})
	public PlanFinderResponse getMinimumPlanCost(
			@RequestParam(value="file", required = true) MultipartFile planFile,@RequestParam("featuresRequired")String featuresRequired) throws IOException {
		PlanFinderResponse planFinderResponse=planFinderService.getMinimumPlanCost(planFile, featuresRequired);
		return planFinderResponse;
	}
}
