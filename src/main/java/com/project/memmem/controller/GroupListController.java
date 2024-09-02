package com.project.memmem.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.memmem.domain.dto.group.GroupDTO;
import com.project.memmem.domain.entity.Category;
import com.project.memmem.service.GroupListService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GroupListController {

	private final GroupListService service;
	
	@GetMapping("/group-list")
	public String groupList(@RequestParam(value = "category", required = false) Category category, Model model) {
	    service.groupsList(category, model);
	    return "views/group/list";
	}
	
	@GetMapping("/data")
	@ResponseBody
	public Page<GroupDTO> Scroll(@RequestParam(value = "category", required = false) Category category, @RequestParam int page,
			@RequestParam int size) {
		Pageable pageable = PageRequest.of(page, size);
		return service.Scroll(category, pageable);
	}

}
