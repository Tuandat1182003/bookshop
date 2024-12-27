package vn.dazz.bookshop.services.chart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.dazz.bookshop.repositories.ProductRepository;
import vn.dazz.bookshop.services.product.ProductService;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RevenueServiceImpl implements RevenueService{

    @Autowired
    ProductRepository productRepository;


    @Override
    public Map<Integer, Double> findMonthlyRevenueByYear(int year) {
        List<Object[]> results = productRepository.findMonthlyRevenueByYear(year);
        Map<Integer, Double> monthlyRevenue = new HashMap<>();

        for (Object[] result : results) {
            Integer month = (Integer) result[0];
            Double revenue = (Double) result[1];
            DecimalFormat decimalFormat = new DecimalFormat("#.###");
            decimalFormat.format(revenue);
            monthlyRevenue.put(month, revenue);
        }

        return monthlyRevenue;
    }
}
