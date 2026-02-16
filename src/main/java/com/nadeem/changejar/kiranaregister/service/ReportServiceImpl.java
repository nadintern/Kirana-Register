package com.nadeem.changejar.kiranaregister.service;

import com.nadeem.changejar.kiranaregister.entity.Report;
import com.nadeem.changejar.kiranaregister.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Override
    public List<Report> getReportsByStore(String storeId) {
        return reportRepository.findByStoreId(storeId);
    }

    @Override
    public List<Report> getReportsByStoreAndType(String storeId, String reportType) {
        return reportRepository.findByStoreIdAndReportType(storeId, reportType);
    }
}
