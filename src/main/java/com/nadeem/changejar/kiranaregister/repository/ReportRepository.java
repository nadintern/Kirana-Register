package com.nadeem.changejar.kiranaregister.repository;

import com.nadeem.changejar.kiranaregister.entity.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReportRepository extends MongoRepository<Report, String> {

    List<Report> findByStoreId(String storeId);

    List<Report> findByStoreIdAndReportType(String storeId, String reportType);
}
