package com.devsu.account.management.service;

import com.devsu.account.management.dto.ReportDTO;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

public interface ReportService
{
	ReportDTO getReport(Integer clienteId, LocalDate fechaInicial, LocalDate fechafinal) throws ExecutionException, InterruptedException;
}
