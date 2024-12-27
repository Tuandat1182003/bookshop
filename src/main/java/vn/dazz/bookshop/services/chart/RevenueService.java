package vn.dazz.bookshop.services.chart;

import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface RevenueService {
     Map<Integer, Double> findMonthlyRevenueByYear(@Param("year") int year);
}
