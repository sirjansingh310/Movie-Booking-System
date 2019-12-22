package com.epam.mtbsclient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
	
	@GetMapping("pageNotFound")
	public String handlePageNotFound() {
		return "handleNoContent";
	}
	
	@GetMapping("error")
	public String handleError() {
		return "error";
	}
}
