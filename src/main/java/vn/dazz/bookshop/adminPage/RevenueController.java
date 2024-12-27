package vn.dazz.bookshop.adminPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.dazz.bookshop.services.chart.RevenueService;

import java.util.Map;

@RestController
public class RevenueController {

    @Autowired
    private RevenueService revenueService;

    @GetMapping("/api/monthly-revenue")
    public Map<Integer, Double> getMonthlyRevenue(@RequestParam int year) {
        return revenueService.findMonthlyRevenueByYear(year);
    }

    @GetMapping("/adminPage/revenue-chart")
    public String showRevenueChart() {
        return "adminPage/chart";
    }
}