package dev.datnt.taskmanagement.controller;

import dev.datnt.taskmanagement.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HealthController {

@GetMapping("/health")
public ApiResponse<Void> getHealth() {
	return ApiResponse.success("Application is running", null);
}
}
