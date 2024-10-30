package SWP391.Fall24.controller.Manager;

import SWP391.Fall24.dto.Manager.AllRevenueOfMonthDTO;
import SWP391.Fall24.dto.Manager.DashBoardMonthYearDTO;
import SWP391.Fall24.dto.Manager.WeekSalesDTO;
import SWP391.Fall24.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashBoard")
@CrossOrigin(origins = "http://localhost:3000")
public class DashBoardController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orderAndRevenue")
    public AllRevenueOfMonthDTO getOrderAndRevenue(@RequestBody DashBoardMonthYearDTO dashBoardMonthYearDTO) {
        AllRevenueOfMonthDTO revenueList = orderService.getOrdersRevenueForDashBoard(dashBoardMonthYearDTO.getYear(), dashBoardMonthYearDTO.getMonth());
        return revenueList;
    }

    @PostMapping("/productAndQuantiy")
    public List<WeekSalesDTO> getProductAndQuantiy(@RequestBody DashBoardMonthYearDTO dashBoardMonthYearDTO) {
        List<WeekSalesDTO> salesDTOList = orderService.getWeeklySales(dashBoardMonthYearDTO.getYear(),dashBoardMonthYearDTO.getMonth());
        return salesDTOList;
    }
}
