package com.devsu.account.management.controller;

import com.devsu.account.management.dto.ReportDTO;
import com.devsu.account.management.service.ReportService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/reportes")
public class ReportController
{
	@Autowired
	private ReportService reportService;

	@GetMapping
	public ResponseEntity<ReportDTO> getReport(@Valid @RequestParam("customer_id") Integer customerId,
											   @RequestParam("starting_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startingDate,
											   @RequestParam("end_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) throws ExecutionException, InterruptedException {
		return ResponseEntity.status(HttpStatus.OK).body(reportService.getReport(customerId, startingDate, endDate));
	}
}
