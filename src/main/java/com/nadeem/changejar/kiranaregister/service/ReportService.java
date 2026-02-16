package com.nadeem.changejar.kiranaregister.service;

import com.nadeem.changejar.kiranaregister.entity.Report;

import java.util.List;

public interface ReportService {

    List<Report> getReportsByStore(String storeId);

    List<Report> getReportsByStoreAndType(String storeId, String reportType);
}
