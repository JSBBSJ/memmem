package com.project.memmem.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.memmem.domain.dto.user.UserUpdateDTO;
import com.project.memmem.domain.entity.UserEntity;
import com.project.memmem.security.MemmemUserDetails;
import com.project.memmem.service.MypageService;
import com.project.memmem.service.impl.UserService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class MypageController {
	
	private final MypageService mypageService; // 사용자 추가 정보를 가져오는 서비스

	@GetMapping("/mypage")
    public String myPage(@AuthenticationPrincipal MemmemUserDetails userDetails, Model model) {
        if (userDetails != null) {
        	addUserDetailsToModel(userDetails.getUserId(), model);
        }
        model.addAttribute("activeSection", "profile");
        return "views/mypage/mypage";
    }

    @GetMapping("/mypage/{section}")
    public String loadSection(@AuthenticationPrincipal MemmemUserDetails userDetails,
                              @PathVariable("section") String section, 
                              Model model) {
        if (userDetails != null) {
        	addUserDetailsToModel(userDetails.getUserId(), model);
        }
        model.addAttribute("activeSection", section);
        return "views/mypage/" + section + " :: content";
    }

    private void addUserDetailsToModel(long userId, Model model) {
        UserEntity userEntity = mypageService.getUserById(userId);
        model.addAttribute("email", userEntity.getEmail());
        model.addAttribute("name", userEntity.getName());
        model.addAttribute("nickName", userEntity.getNickName());
        model.addAttribute("userId", userEntity.getUserId());
        model.addAttribute("number", userEntity.getNumber());
        model.addAttribute("address", userEntity.getAddress());
        model.addAttribute("birthDate", userEntity.getBirthDate());
    }

	
	@GetMapping("/mypage/edit")
    public String showEditForm(@AuthenticationPrincipal MemmemUserDetails userDetails, Model model) {
        if (userDetails != null) {
            UserEntity userEntity = mypageService.getUserById(userDetails.getUserId());
            model.addAttribute("user", userEntity);
        }
        return "views/mypage/editProfile";
    }

	@PostMapping("/mypage/update")
	public String updateProfile(@AuthenticationPrincipal MemmemUserDetails userDetails,
	                            @ModelAttribute("user") UserUpdateDTO userUpdateDTO) {
	    if (userDetails != null) {
	        mypageService.updateUser(userDetails.getUserId(), userUpdateDTO);
	    }
	    return "redirect:/mypage";
	}
	@GetMapping("hidden")
	public String heddin() {
		return "/views/mypage/hidden";
	}

}
