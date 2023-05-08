package com.telepathy.planfinder.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.telepathy.planfinder.model.Plan;
import com.telepathy.planfinder.model.PlanFinderResponse;
import com.telepathy.planfinder.model.PlanSet;



@Service
public class PlanFinderService {

	public PlanFinderResponse getMinimumPlanCost(MultipartFile planFile,String requiredFeatures) throws IOException{
		 List<Plan> plans = readPlans(planFile);
		 PlanFinderResponse response =new PlanFinderResponse();
	        PlanSet planSet = findCheapestPlanSet(plans, requiredFeatures.split(","));
	        if (planSet != null) {
	        	response.setPlanCost(planSet.getTotalPrice());
	        	response.setPlansToBeSelected(planSet.toString());
	            System.out.println(planSet.getTotalPrice() + "," + planSet.toString());
	        } else {
	            System.out.println("0");
	            response.setPlanCost(0);
	        	response.setPlansToBeSelected("");
	        }
			return response;
	}
	
	
	private  List<Plan> readPlans(MultipartFile file) throws IOException {
        List<Plan> plans = new ArrayList<Plan>();
        //try (BufferedReader br = new BufferedReader(new FileReader( "D://CodingworkArea//SamplePlanSelection.txt"))) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))){
        	String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split(",");
                String name = parts[0];
                int price = Integer.parseInt(parts[1]);
                Set<String> features = new HashSet<>(Arrays.asList(parts).subList(2, parts.length));
                plans.add(new Plan(name, price, features));
            }
        }
        return plans;
    }

    private PlanSet findCheapestPlanSet(List<Plan> plans, String[] requiredFeatures) {
        Set<String> features = new HashSet<String>(Arrays.asList(requiredFeatures));
        List<PlanSet> allPlanSets = new ArrayList<PlanSet>();
        allPlanSets.addAll(getAllPlanSets(plans, features));
        if (allPlanSets.isEmpty()) {
            return null;
        }
       Collections.sort(allPlanSets, (x1,x2)->((Integer)x1.getTotalPrice()).compareTo((Integer)x2.getTotalPrice()));
       return allPlanSets.get(0);
    }

    private List<PlanSet> getAllPlanSets(List<Plan> plans, Set<String> requiredFeatures) {
        List<PlanSet> allPlanSets = new ArrayList<PlanSet>();
        for (int i = 1; i <= plans.size(); i++) {
            List<PlanSet> planSets = getPlanSets(plans, i, requiredFeatures);
            allPlanSets.addAll(planSets);
        }
        return allPlanSets;
    }

    private List<PlanSet> getPlanSets(List<Plan> plans, int size, Set<String> requiredFeatures) {
        List<List<Plan>> planLists = getCombinations(plans, size);
        List<PlanSet> planSets = new ArrayList<PlanSet>();
        for (List<Plan> planList : planLists) {
            PlanSet planSet = new PlanSet(planList);
            if (planSet.containsAllFeatures(requiredFeatures)) {
                planSets.add(planSet);
            }
        }
        return planSets;
    }

    private <T> List<List<T>> getCombinations(List<T> items, int size) {
        if (size == 0) {
            return Collections.singletonList(Collections.emptyList());
        }
        if (items.isEmpty()) {
            return Collections.emptyList();
        }
        List<List<T>> combinations = new ArrayList<>();
        T head = items.get(0);
        List<T> tail = items.subList(1, items.size());
        for (List<T> smallerCombination : getCombinations(tail, size - 1)) {
            List<T> newCombination = new ArrayList<>();
            newCombination.add(head);
            newCombination.addAll(smallerCombination);
            combinations.add(newCombination);
        }
        combinations.addAll(getCombinations(tail, size));
        return combinations;
    }
}
